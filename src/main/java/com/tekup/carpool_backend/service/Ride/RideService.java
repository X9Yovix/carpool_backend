package com.tekup.carpool_backend.service.Ride;
import com.tekup.carpool_backend.model.Ride.Ride;

import java.util.List;

public interface RideService {
    Ride save(Ride r);
    List<Ride> findAllRides() ;
    List<Ride> findByDep( String dep);
    List<Ride> findByArr( String arr);
    List<Ride> findByTime( String time);
    Ride update (Ride r);
    void delete (int idR);
}
