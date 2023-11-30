package com.tekup.carpool_backend.controller.ride;

import com.tekup.carpool_backend.model.ride.RideRequestStatus;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;
import com.tekup.carpool_backend.service.ride.RideRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/ride-requests")
@RequiredArgsConstructor
public class RideRequestController {
    private final RideRequestService rideRequestService;

    @PostMapping("/passenger/apply")
    public ResponseEntity<Object> applyForRide(@RequestBody ApplyForRideRequest request, Principal connectedUser) {
        return ResponseEntity.ok(rideRequestService.createRideRequest(request, connectedUser));
    }

    @GetMapping("/passenger/applied")
    public ResponseEntity<Object> getAppliedRides(
            Principal connectedUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) RideRequestStatus status
    ) {
        User passenger = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        return ResponseEntity.ok(rideRequestService.getAppliedRides(passenger, status, page, size));
    }
}
