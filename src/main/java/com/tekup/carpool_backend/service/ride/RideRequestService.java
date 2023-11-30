package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;

import java.security.Principal;

public interface RideRequestService {
    Object createRideRequest(ApplyForRideRequest request, Principal connectedUser);
}
