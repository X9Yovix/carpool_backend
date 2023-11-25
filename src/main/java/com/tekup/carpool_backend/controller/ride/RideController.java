package com.tekup.carpool_backend.controller.ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.payload.request.SaveRideRequest;
import com.tekup.carpool_backend.payload.request.UpdateRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.service.Ride.RideService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/rides")
public class RideController {
    private final RideService service ;

    public RideController(  RideService service ){
        this.service=service;
    }
    @PostMapping
    //,Principal connectedUser
    public ResponseEntity<MessageResponse> save(@RequestBody SaveRideRequest request) {
        return ResponseEntity.ok(service.save(request));

    }
    @GetMapping
    public List<Ride>  findAllRides() {

        return service.findAllRides();
    }
    @GetMapping("filter/limit")
    public List<Ride>  findWithLimit() {

        return service.findWithLimit();
    }

    @GetMapping("filter/departure/{departure}")
    public List<Ride> findByDeparture (@PathVariable String departure){
        return service.findByDeparture(departure);
    }
    @GetMapping("filter/arrival/{arrival}")
    public List<Ride> findByArrival( @PathVariable String arrival){
        return service.findByArrival(arrival);
    }
    @GetMapping("filter/time/{time}")
    public List<Ride> findByTime( @PathVariable LocalDateTime time){
        return service.findByTime(LocalDateTime.parse(String.valueOf(time)));
    }


    @PatchMapping
    public ResponseEntity<MessageResponse> updateRide(@RequestBody UpdateRequest request){
        return ResponseEntity.ok(service.update(request));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable int id){
        return ResponseEntity.ok(service.delete(id));

    }
}
