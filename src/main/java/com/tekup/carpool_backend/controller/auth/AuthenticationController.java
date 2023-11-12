package com.tekup.carpool_backend.controller.auth;

import com.tekup.carpool_backend.payload.request.*;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(service.login(request));
    }

    @PutMapping("/verify-account")
    public ResponseEntity<MessageResponse> verifyAccount(@RequestBody VerifyAccountRequest request) {
        return ResponseEntity.ok(service.verifyAccount(request));
    }

    @PostMapping("/regenerate-otp")
    public ResponseEntity<MessageResponse> regenerateOtp(@RequestBody RegenerateOtpRequest request) {
        return ResponseEntity.ok(service.regenerateOtp(request));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<MessageResponse> forgotPassword(@RequestBody ForgotPasswordRequest request) {
        return ResponseEntity.ok(service.forgotPassword(request));
    }

    @PatchMapping("/reset-password/{token}")
    public ResponseEntity<MessageResponse> resetPassword(@PathVariable String token, @RequestBody ResetPasswordRequest request) {
        return ResponseEntity.ok(service.resetPassword(token, request));
    }
}
