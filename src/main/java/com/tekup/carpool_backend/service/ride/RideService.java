package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.payload.request.AddRideRequest;

import java.security.Principal;

public interface RideService {
    Object createRide(AddRideRequest request, Principal connectedUser);
}
