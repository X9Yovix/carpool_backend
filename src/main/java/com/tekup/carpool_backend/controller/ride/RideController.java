package com.tekup.carpool_backend.controller.ride;

import com.tekup.carpool_backend.payload.request.AddRideRequest;
import com.tekup.carpool_backend.payload.request.FilterRideRequest;
import com.tekup.carpool_backend.service.ride.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/rides")
@RequiredArgsConstructor
public class RideController {
    private final RideService rideService;

    @PostMapping("/driver")
    public ResponseEntity<Object> createRide(@RequestBody AddRideRequest request, Principal connectedUser) {
        return ResponseEntity.ok(rideService.createRide(request, connectedUser));
    }

    @GetMapping("/driver")
    public ResponseEntity<Object> getRidesCreatedByAuthenticatedDriver(Principal connectedUser) {
        return ResponseEntity.ok(rideService.getRidesCreatedByAuthenticatedDriver(connectedUser));
    }

    @GetMapping("/filter")
    public ResponseEntity<Object> filterRides(@RequestBody FilterRideRequest request) {
        return ResponseEntity.ok(rideService.filterRides(request));
    }
}
