package com.tekup.carpool_backend.service.car;

import com.tekup.carpool_backend.model.car.Car;
import com.tekup.carpool_backend.payload.request.SaveCarRequest;
import com.tekup.carpool_backend.payload.request.UpdateCarRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.car.CarRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarServiceImp implements CarService {
    private final CarRepository repository;

    public CarServiceImp(CarRepository repository) {
        this.repository=repository;
    }
    @Override
    //,Principal connectedUser
    public MessageResponse save(SaveCarRequest request) {
        //User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        Car c = Car.builder()
                .matricule(request.getMatricule())
                .brand(request.getBrand())
                .color(request.getColor())
                .nbPlaces(request.getNbPlaces())

                .build();
        Car savedCar = repository.save(c);
        return MessageResponse.builder()
                .message("car has been added successffly")
                .http_code(HttpStatus.OK.value())
                .build();
    }
    @Override
    public List<Car> findAllCars() {

        return repository.findAll();

    }
    @Override
    public MessageResponse update(UpdateCarRequest request) {
        Car c = Car.builder()
                .id(request.getId())
                .matricule(request.getMatricule())
                .brand(request.getBrand())
                .color(request.getColor())
                .nbPlaces(request.getNbPlaces())
                .build();

        Car updatedCar = repository.save(c);
        return MessageResponse.builder()
                .message("Car has been updated successffly")
                .http_code(HttpStatus.OK.value())
                .build();

    }

    @Override
    public  MessageResponse delete ( int id){
        MessageResponse deletedCar = repository.deleteById(id);
        return MessageResponse.builder()
                .message("Car has been deleted successffly")
                .http_code(HttpStatus.OK.value())
                .build();



    }

}
