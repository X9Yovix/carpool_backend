package com.tekup.carpool_backend.controller;

import com.tekup.carpool_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/seed")
    public ResponseEntity<String> seedUsers() {
        userService.seedInitialUsers();
        return ResponseEntity.ok().body("done");
    }
}
