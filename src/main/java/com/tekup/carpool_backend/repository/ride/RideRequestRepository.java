package com.tekup.carpool_backend.repository.ride;

import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideRequest;
import com.tekup.carpool_backend.model.ride.RideRequestStatus;
import com.tekup.carpool_backend.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRequestRepository extends JpaRepository<RideRequest, Long> {
    boolean existsByPassengerAndRide(User passenger, Ride ride);

    Page<RideRequest> findByPassengerAndStatus(User passenger, RideRequestStatus status, Pageable pageable);

    Page<RideRequest> findByPassenger(User passenger, Pageable pageable);
}
