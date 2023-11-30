package com.tekup.carpool_backend.repository.user;

import com.tekup.carpool_backend.model.user.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
