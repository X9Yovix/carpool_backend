package com.tekup.carpool_backend.service.auth;

import com.tekup.carpool_backend.config.jwt.JwtService;
import com.tekup.carpool_backend.exception.ResourceNotFoundException;
import com.tekup.carpool_backend.mail.EmailSender;
import com.tekup.carpool_backend.mail.Otp;
import com.tekup.carpool_backend.model.password.ResetPassword;
import com.tekup.carpool_backend.model.token.Token;
import com.tekup.carpool_backend.model.token.TokenType;
import com.tekup.carpool_backend.model.user.Role;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.*;
import com.tekup.carpool_backend.payload.response.LoginResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.password.ResetPasswordRepository;
import com.tekup.carpool_backend.repository.token.TokenRepository;
import com.tekup.carpool_backend.repository.user.RoleRepository;
import com.tekup.carpool_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImp implements AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final Otp otpCmp;
    private final EmailSender emailSenderCmp;
    private final ResetPasswordRepository resetPasswordRepository;
    private final RoleRepository roleRepository;
    @Value("${carpool_app.frontend.url}")
    private String frontUrl;

    public MessageResponse register(RegisterRequest request) {

        Set<Role> roles = request.getRoles().stream()
                .map(roleName -> roleRepository.findByName(roleName).orElseThrow())
                .collect(Collectors.toSet());

        if (roles.isEmpty()) {
            return MessageResponse.builder()
                    .message("Invalid roles provided")
                    .build();
        }

        String otpCode = otpCmp.generateOtp();

        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roles)
                .otp(otpCode)
                .otpGeneratedTime(LocalDateTime.now())
                .build();
        User savedUser = userRepository.save(user);

        emailSenderCmp.sendOtpVerification(savedUser.getEmail(), otpCode);
        return MessageResponse.builder()
                .message("Registration done, check your email to verify your account with the OTP code")
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user = userRepository.findByEmailWithRoles(request.getEmail()).orElseThrow((ResourceNotFoundException::new));
        if (user.isVerified()) {
            String jwtToken = jwtService.generateToken(user);

            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return LoginResponse.builder()
                    .token(jwtToken)
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .message("Welcome to TEKUP-Carpool project")
                    .build();
        }
        return LoginResponse.builder()
                .message("Your account is not verified")
                .build();
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(t -> {
            t.setRevoked(true);
            t.setExpired(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    public MessageResponse verifyAccount(VerifyAccountRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(ResourceNotFoundException::new);
        LocalDateTime otpGeneratedTime = user.getOtpGeneratedTime();
        LocalDateTime currentTime = LocalDateTime.now();
        long secondsDifference = Duration.between(otpGeneratedTime, currentTime).getSeconds();
        boolean isNotExpired = secondsDifference < 60;

        if (user.getOtp().equals(request.getOtp())) {
            if (isNotExpired) {
                if (!user.isVerified()) {
                    user.setVerified(true);
                    userRepository.save(user);
                    return MessageResponse.builder()
                            .message("Your OTP has been successfully verified. You now have access to the platform")
                            .build();
                } else {
                    return MessageResponse.builder()
                            .message("Your account is already verified. You have access to the platform")
                            .build();
                }
            } else {
                return MessageResponse.builder()
                        .message("OTP Code expired. Please regenerate another OTP code")
                        .build();
            }
        } else {
            return MessageResponse.builder()
                    .message("Invalid OTP code")
                    .build();
        }
    }

    public MessageResponse regenerateOtp(RegenerateOtpRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(ResourceNotFoundException::new);
        if (!user.isVerified()) {
            String otpCode = otpCmp.generateOtp();
            user.setOtp(otpCode);
            user.setOtpGeneratedTime(LocalDateTime.now());
            userRepository.save(user);

            emailSenderCmp.sendOtpVerification(request.getEmail(), otpCode);

            return MessageResponse.builder()
                    .message("A new OTP code has been generated and sent to your email")
                    .build();
        } else {
            return MessageResponse.builder()
                    .message("Your account is already verified. You have access to the platform")
                    .build();
        }
    }

    public MessageResponse forgotPassword(ForgotPasswordRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String url = generateResetToken(user);
        emailSenderCmp.sendResetPassword(request.getEmail(), url);
        return MessageResponse.builder()
                .message("Password reset instructions have been sent to your email")
                .build();
    }

    //Token reset password is set to be valid for 30mns
    public String generateResetToken(User user) {
        UUID uuid = UUID.randomUUID();
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime expirationDateTime = currentDateTime.plusMinutes(30);
        ResetPassword resetToken = ResetPassword.builder()
                .token(uuid.toString())
                .expirationDate(expirationDateTime)
                .user(user)
                .build();

        ResetPassword token = resetPasswordRepository.save(resetToken);
        return frontUrl + "/reset-password/" + token.getToken();
    }

    public MessageResponse resetPassword(String token, ResetPasswordRequest request) {
        if (request.getNewPassword().equals(request.getConfirmationPassword())) {
            ResetPassword resetPassword = resetPasswordRepository.findByToken(token).orElseThrow();
            if (isResetPasswordTokenValid(resetPassword.getExpirationDate())) {
                User user = resetPassword.getUser();
                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                userRepository.save(user);
                return MessageResponse.builder()
                        .message("Password has been changed")
                        .build();
            } else {
                return MessageResponse.builder()
                        .message("Something went wrong")
                        .build();
            }
        } else {
            return MessageResponse.builder()
                    .message("New Password and Password Confirmation do not match")
                    .build();
        }
    }

    public boolean isResetPasswordTokenValid(LocalDateTime expirationDate) {
        LocalDateTime currentDate = LocalDateTime.now();
        return expirationDate.isAfter(currentDate);
    }
}
