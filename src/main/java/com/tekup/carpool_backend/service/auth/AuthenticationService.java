package com.tekup.carpool_backend.service.auth;


import com.tekup.carpool_backend.payload.request.LoginRequest;
import com.tekup.carpool_backend.payload.request.RegenerateOtpRequest;
import com.tekup.carpool_backend.payload.request.RegisterRequest;
import com.tekup.carpool_backend.payload.request.VerifyAccountRequest;
import com.tekup.carpool_backend.payload.response.LoginResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;

public interface AuthenticationService {
    MessageResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    MessageResponse verifyAccount(VerifyAccountRequest request);
    MessageResponse regenerateOtp(RegenerateOtpRequest request);
}
