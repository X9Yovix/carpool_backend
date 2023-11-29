package com.tekup.carpool_backend.service.user;

import com.tekup.carpool_backend.model.car.Car;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.AddCarRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.car.CarRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class CarServiceImp implements CarService{
    private final CarRepository carRepository;
    private final EntityManager entityManager;

    @Override
    public Object createCar(AddCarRequest request, Principal connectedUser) {
        User authUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        entityManager.detach(authUser);
        Car car = Car.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .color(request.getColor())
                .seats(request.getSeats())
                .user(authUser)
                .build();
        carRepository.save(car);

        return MessageResponse.builder()
                .message("Car created successfully")
                .http_code(HttpStatus.OK.value())
                .build();
    }
}
