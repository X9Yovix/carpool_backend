package com.tekup.carpool_backend.controller.ride;

import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;
import com.tekup.carpool_backend.service.ride.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/ride-requests")
@RequiredArgsConstructor
public class RideRequestController {
    private final RideRequestService rideRequestService;

    @PostMapping("/apply")
    public ResponseEntity<Object> applyForRide(@RequestBody ApplyForRideRequest request, Principal connectedUser) {
        return ResponseEntity.ok(rideRequestService.createRideRequest(request,connectedUser));
    }
}
