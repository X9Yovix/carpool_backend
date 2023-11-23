package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.payload.request.ChangePasswordRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;

import java.security.Principal;

public interface UserService {
    boolean seedInitialUsers();

    Object changePassword(ChangePasswordRequest email, Principal connectedUser);
}
