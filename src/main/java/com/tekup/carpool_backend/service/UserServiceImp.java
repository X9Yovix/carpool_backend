package com.tekup.carpool_backend.service;

import com.tekup.carpool_backend.model.User;
import com.tekup.carpool_backend.model.UserRole;
import com.tekup.carpool_backend.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User register(User user) {
        return null;
    }

    @Override
    public User login(String username, String password) {
        return null;
    }

    public void seedInitialUsers() {
        User admin1 = new User("Ali","Ben Ali", "admin@gmail.com", BCrypt.hashpw("alipassword", BCrypt.gensalt()), UserRole.ADMIN);
        User driver1 = new User("Saleh","Ben Saleh", "driver@gmail.com", BCrypt.hashpw("driverpassword", BCrypt.gensalt()), UserRole.DRIVER);
        User passenger1 = new User("Mohamed","Ben Mohamed", "passenger@gmail.com", BCrypt.hashpw("passengerpassword", BCrypt.gensalt()), UserRole.PASSENGER);
        this.userRepository.saveAll(List.of(admin1, driver1, passenger1));
    }
}
