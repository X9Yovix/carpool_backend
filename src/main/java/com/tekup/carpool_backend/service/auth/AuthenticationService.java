package com.tekup.carpool_backend.service.auth;


import com.tekup.carpool_backend.payload.request.*;
import com.tekup.carpool_backend.payload.response.LoginResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;

public interface AuthenticationService {
    MessageResponse register(RegisterRequest request);

    Object login(LoginRequest request);

    MessageResponse verifyAccount(VerifyAccountRequest request);

    MessageResponse regenerateOtp(RegenerateOtpRequest request);

    MessageResponse forgotPassword(ForgotPasswordRequest request);

    MessageResponse resetPassword(String token, ResetPasswordRequest request);
}
