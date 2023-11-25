package com.tekup.carpool_backend.repository.car;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.model.car.Car;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarRepository extends JpaRepository<Car,Integer> {
    List<Car> findAll();
    MessageResponse deleteById(int id);

}
