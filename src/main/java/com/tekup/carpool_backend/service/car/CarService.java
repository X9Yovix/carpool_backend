package com.tekup.carpool_backend.service.car;

import com.tekup.carpool_backend.model.car.Car;
import com.tekup.carpool_backend.payload.request.SaveCarRequest;
import com.tekup.carpool_backend.payload.request.UpdateCarRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;

import java.util.List;

public interface CarService {
    MessageResponse save(SaveCarRequest request);
    List<Car> findAllCars() ;
    MessageResponse update(UpdateCarRequest request);
    MessageResponse delete (int id);
}
