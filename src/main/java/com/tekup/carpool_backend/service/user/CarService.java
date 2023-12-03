package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.payload.request.AddCarRequest;
import java.security.Principal;

public interface CarService {
    Object createCar(AddCarRequest request, Principal connectedUser);

    Object  getDriverCars(Principal connectedUser);

    Object deleteCar(Long id, Principal connectedUser);
}
