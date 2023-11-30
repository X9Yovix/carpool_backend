package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.model.ride.RideRequestStatus;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;

import java.security.Principal;

public interface RideRequestService {
    Object createRideRequest(ApplyForRideRequest request, Principal connectedUser);

    Object getAppliedRides(User passenger, RideRequestStatus status, int page, int size);

    Object getRequestedRidesForDriver(Principal connectedUser, int page, int size);

    Object acceptRideRequest(Long rideRequestId);
    Object declineRideRequest(Long rideRequestId);

}
