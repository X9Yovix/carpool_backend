package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.model.user.UserRole;
import com.tekup.carpool_backend.payload.request.ChangePasswordRequest;
import com.tekup.carpool_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean seedInitialUsers() {
        User admin1 = new User(1L, "Ali", "Ben Ali", "admin@gmail.com", BCrypt.hashpw("adminpassword", BCrypt.gensalt()), UserRole.ADMIN);
        User driver1 = new User(2L, "Saleh", "Ben Saleh", "driver@gmail.com", BCrypt.hashpw("driverpassword", BCrypt.gensalt()), UserRole.DRIVER);
        User passenger1 = new User(3L, "Mohamed", "Ben Mohamed", "passenger@gmail.com", BCrypt.hashpw("passengerpassword", BCrypt.gensalt()), UserRole.PASSENGER);

        List<User> savedUsers = userRepository.saveAll(List.of(admin1, driver1, passenger1));
        return !savedUsers.isEmpty();
    }

    @Override
    public String changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return "Wrong password";
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return "NewPassword & ConfirmationPassword are not the same";
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return "Password updated";
    }
}
