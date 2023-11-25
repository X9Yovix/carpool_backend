package com.tekup.carpool_backend.controller.car;

import com.tekup.carpool_backend.model.car.Car;
import com.tekup.carpool_backend.payload.request.SaveCarRequest;
import com.tekup.carpool_backend.payload.request.UpdateCarRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.service.car.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private  CarService service ;
    public CarController(  CarService service ){
        this.service=service;
    }
    @PostMapping
    //,Principal connectedUser
    public ResponseEntity<MessageResponse> save(@RequestBody SaveCarRequest request) {
        return ResponseEntity.ok(service.save(request));

    }
    @GetMapping
    public List<Car> findAllCars() {

        return service.findAllCars();
    }
    @PatchMapping
    public ResponseEntity<MessageResponse> updateCar(@RequestBody UpdateCarRequest request){

        return ResponseEntity.ok(service.update(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable int id){
       return ResponseEntity.ok( service.delete(id));

    }
}
