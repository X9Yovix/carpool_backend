package com.tekup.carpool_backend.repository.ride;

import com.tekup.carpool_backend.model.ride.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Long> {
    List<Ride> findByDriverId(Long driver_id);
}
