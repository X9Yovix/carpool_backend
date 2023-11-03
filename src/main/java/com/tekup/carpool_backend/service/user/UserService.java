package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.payload.request.ChangePasswordRequest;

import java.security.Principal;

public interface UserService {
    boolean seedInitialUsers();
    String changePassword(ChangePasswordRequest email, Principal connectedUser);
}
