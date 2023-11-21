package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.exception.ResourceNotFoundException;
import com.tekup.carpool_backend.model.user.Role;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ChangePasswordRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.user.RoleRepository;
import com.tekup.carpool_backend.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private void seedRoles() {
        roleRepository.save(new Role(1L, "ADMIN"));
        roleRepository.save(new Role(2L, "DRIVER"));
        roleRepository.save(new Role(3L, "PASSENGER"));
    }

    public boolean seedInitialUsers() {
        seedRoles();
        Role adminRole = roleRepository.findByName("ADMIN").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: ADMIN")
        );
        Role driverRole = roleRepository.findByName("DRIVER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: DRIVER")
        );
        Role passengerRole = roleRepository.findByName("PASSENGER").orElseThrow(
                () -> new ResourceNotFoundException("Role not found for name: PASSENGER")
        );
        User admin1 = new User(1L, "Ali", "Ben Ali", "ali@gmail.com", BCrypt.hashpw("alipassword", BCrypt.gensalt()), Collections.singleton(adminRole), true);
        User driver1 = new User(2L, "Saleh", "Ben Saleh", "saleh@gmail.com", BCrypt.hashpw("salehpassword", BCrypt.gensalt()), Collections.singleton(driverRole), true);
        User passenger1 = new User(3L, "Mohamed", "Ben Mohamed", "mohamed@gmail.com", BCrypt.hashpw("mohamedpassword", BCrypt.gensalt()), new HashSet<>(Arrays.asList(driverRole, passengerRole)), true);

        List<User> savedUsers = userRepository.saveAll(List.of(admin1, driver1, passenger1));
        return !savedUsers.isEmpty();
    }

    @Override
    public MessageResponse changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return MessageResponse.builder()
                    .message("Current password is wrong")
                    .http_code(401)
                    .build();
        }
        if (!request.getNewPassword().equals(request.getConfirmationPassword())) {
            return MessageResponse.builder()
                    .message("New Password & Password Confirmation are not the same")
                    .http_code(401)
                    .build();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return MessageResponse.builder()
                .message("Password updated successfully")
                .http_code(200)
                .build();
    }
}
