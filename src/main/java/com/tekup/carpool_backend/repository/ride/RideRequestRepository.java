package com.tekup.carpool_backend.repository.ride;

import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideRequest;
import com.tekup.carpool_backend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest,Long> {
    boolean existsByPassengerAndRide(User passenger, Ride ride);
}
