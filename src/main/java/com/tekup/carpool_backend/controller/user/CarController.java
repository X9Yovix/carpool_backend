package com.tekup.carpool_backend.controller.user;

import com.tekup.carpool_backend.payload.request.AddCarRequest;
import com.tekup.carpool_backend.service.user.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    @PostMapping("/")
    public ResponseEntity<Object> createCar(@RequestBody AddCarRequest request, Principal connectedUser) {
        return ResponseEntity.ok().body(carService.createCar(request,connectedUser));
    }
}
