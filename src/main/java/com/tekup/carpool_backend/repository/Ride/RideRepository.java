package com.tekup.carpool_backend.repository.Ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface RideRepository extends JpaRepository<Ride,Integer> {

    List<Ride> findAll();

    @Query("SELECT R FROM Ride R ORDER BY R.id DESC limit 2")
    List<Ride> findWithLimit();


    List<Ride> findByDeparture (String departure);
    List<Ride> findByArrival(String arrival);
    List<Ride> findByTime(LocalDateTime time);
    MessageResponse deleteById(int id);
}
