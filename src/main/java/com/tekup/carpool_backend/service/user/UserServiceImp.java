package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.model.user.UserRole;
import com.tekup.carpool_backend.repository.user.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean seedInitialUsers() {
        User admin1 = new User(Long.parseLong("1"), "Ali", "Ben Ali", "admin@gmail.com", BCrypt.hashpw("adminpassword", BCrypt.gensalt()), UserRole.ADMIN);
        User driver1 = new User(Long.parseLong("2"), "Saleh", "Ben Saleh", "driver@gmail.com", BCrypt.hashpw("driverpassword", BCrypt.gensalt()), UserRole.DRIVER);
        User passenger1 = new User(Long.parseLong("3"), "Mohamed", "Ben Mohamed", "passenger@gmail.com", BCrypt.hashpw("passengerpassword", BCrypt.gensalt()), UserRole.PASSENGER);

        List<User> savedUsers = this.userRepository.saveAll(List.of(admin1, driver1, passenger1));
        return !savedUsers.isEmpty();
    }
}
