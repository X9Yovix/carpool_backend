package com.tekup.carpool_backend.service.auth;


import com.tekup.carpool_backend.payload.request.LoginRequest;
import com.tekup.carpool_backend.payload.request.RegisterRequest;
import com.tekup.carpool_backend.payload.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse login(LoginRequest request);
}
