package com.tekup.carpool_backend.repository.Ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {
    List<Ride> findByDep (String dep);
    List<Ride> findByArr(String arr);
    List<Ride> findByTime(String time);
    void deleteById( int idR);
}
