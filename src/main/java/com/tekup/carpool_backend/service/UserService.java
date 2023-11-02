package com.tekup.carpool_backend.service;

import com.tekup.carpool_backend.model.User;

public interface UserService {
    void seedInitialUsers();
    User register(User user);
    User login(String username, String password);
}
