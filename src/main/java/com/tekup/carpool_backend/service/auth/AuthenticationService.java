package com.tekup.carpool_backend.service.auth;


import com.tekup.carpool_backend.payload.request.*;
import com.tekup.carpool_backend.payload.response.LoginResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;

public interface AuthenticationService {
    Object register(RegisterRequest request);

    Object login(LoginRequest request);

    Object verifyAccount(VerifyAccountRequest request);

    Object regenerateOtp(RegenerateOtpRequest request);

    Object forgotPassword(ForgotPasswordRequest request);

    Object resetPassword(String token, ResetPasswordRequest request);
}
