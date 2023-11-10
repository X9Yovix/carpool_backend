package com.tekup.carpool_backend.controller.ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.service.Ride.RideService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Rides")
public class RideController {
    private RideService service ;

    public RideController(  RideService service ){
        this.service=service;
    }
    @PostMapping
    public Ride save(@RequestBody Ride r) { return service.save(r);}
    @GetMapping("/depart/{dep}")
    public List<Ride> findByDep (@PathVariable String dep){
        return service.findByDep(dep);
    }
    @GetMapping("/arrivee/{arr}")
    public List<Ride> findByArr( @PathVariable String arr){
        return service.findByArr(arr);
    }
    @GetMapping("/date/{time}")
    public List<Ride> findByTime( @PathVariable String time){
        return service.findByTime(time);
    }

    @GetMapping
    public List<Ride> findAllRides() {
        return service.findAllRides();
    }
    @PutMapping
    public Ride updateRide(@RequestBody  Ride r){
        return service.update(r);
    }
    @DeleteMapping("/{idR}")
    public void delete(@PathVariable int idR){
        service.delete(idR);}
}
