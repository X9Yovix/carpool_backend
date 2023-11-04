package com.tekup.carpool_backend.controller.auth;

import com.tekup.carpool_backend.payload.request.LoginRequest;
import com.tekup.carpool_backend.payload.request.RegenerateOtpRequest;
import com.tekup.carpool_backend.payload.request.RegisterRequest;
import com.tekup.carpool_backend.payload.request.VerifyAccountRequest;
import com.tekup.carpool_backend.payload.response.LoginResponse;
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
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
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
}
