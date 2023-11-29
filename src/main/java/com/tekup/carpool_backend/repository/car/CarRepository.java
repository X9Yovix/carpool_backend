package com.tekup.carpool_backend.repository.car;

import com.tekup.carpool_backend.model.car.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
