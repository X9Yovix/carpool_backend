package com.tekup.carpool_backend.controller.user;

import com.tekup.carpool_backend.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/seed")
    public ResponseEntity<Boolean> seedUsers() {
        return ResponseEntity.ok().body(this.service.seedInitialUsers());
    }

    @GetMapping("/protected")
    public ResponseEntity<String> needsToken() {
        return ResponseEntity.ok().body("entered a protected route with a bearer token");
    }

    @GetMapping("/admin")
    public ResponseEntity<String> onlyAdmin() {
        return ResponseEntity.ok().body("protected route and for admin only");
    }
}
