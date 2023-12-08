package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.exception.ResourceNotFoundException;
import com.tekup.carpool_backend.model.user.Role;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ChangePasswordRequest;
import com.tekup.carpool_backend.payload.request.UpdateUserDataRequest;
import com.tekup.carpool_backend.payload.response.ErrorResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.payload.response.UpdateDataResponse;
import com.tekup.carpool_backend.repository.user.RoleRepository;
import com.tekup.carpool_backend.repository.user.UserRepository;
import com.tekup.carpool_backend.service.utils.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.security.Principal;
import java.util.*;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final FileStorageService fileStorageService;

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
        User admin1 = new User(1L, "Ali", "Ben Ali", "ali@gmail.com", BCrypt.hashpw("alipassword", BCrypt.gensalt()), "Rades Meliane", "123456789", Collections.singleton(adminRole), true);
        User driver1 = new User(2L, "Saleh", "Driver Only", "saleh@gmail.com", BCrypt.hashpw("salehpassword", BCrypt.gensalt()), "Centre ville, Tunis", "123456789", Collections.singleton(driverRole), true);
        User passenger1 = new User(3L, "Mohamed", "Driver Passenger", "mohamed@gmail.com", BCrypt.hashpw("mohamedpassword", BCrypt.gensalt()), "Technopole, Ariana", "123456789", new HashSet<>(Arrays.asList(driverRole, passengerRole)), true);

        List<User> savedUsers = userRepository.saveAll(List.of(admin1, driver1, passenger1));
        return !savedUsers.isEmpty();
    }

    @Override
    public Object changePassword(ChangePasswordRequest request, Principal connectedUser) {
        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            return ErrorResponse.builder()
                    .errors(List.of("Current password is wrong"))
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        return MessageResponse.builder()
                .message("Password updated successfully")
                .http_code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Object updateUserData(UpdateUserDataRequest request, Principal connectedUser) {

        User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setAddress(request.getAddress());
        user.setPhoneNumber(request.getPhoneNumber());

        if (request.getImage() != null && !request.getImage().isEmpty()) {
            try {
                String fileName = fileStorageService.storeImg(request.getImage());
                user.setImageUrl("/uploads/img/user/" + fileName);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        userRepository.save(user);

        return UpdateDataResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .address(user.getAddress())
                .phoneNumber(user.getPhoneNumber())
                .imgUrl(user.getImageUrl())
                .message("User data updated successfully")
                .http_code(HttpStatus.OK.value())
                .build();
    }
}
