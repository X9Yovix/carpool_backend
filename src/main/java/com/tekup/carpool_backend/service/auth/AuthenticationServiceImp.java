package com.tekup.carpool_backend.service.auth;

import com.tekup.carpool_backend.config.jwt.JwtService;
import com.tekup.carpool_backend.mail.EmailSender;
import com.tekup.carpool_backend.mail.Otp;
import com.tekup.carpool_backend.model.token.Token;
import com.tekup.carpool_backend.model.token.TokenType;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.model.user.UserRole;
import com.tekup.carpool_backend.payload.request.LoginRequest;
import com.tekup.carpool_backend.payload.request.RegisterRequest;
import com.tekup.carpool_backend.payload.request.VerifyAccountRequest;
import com.tekup.carpool_backend.payload.response.LoginResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.token.TokenRepository;
import com.tekup.carpool_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

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

    public MessageResponse register(RegisterRequest request) {
        String otpCode = otpCmp.generateOtp();
        User user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UserRole.valueOf(request.getRole()))
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
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String jwtToken = jwtService.generateToken(user);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return LoginResponse.builder()
                .token(jwtToken)
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
                .orElseThrow();
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
}
