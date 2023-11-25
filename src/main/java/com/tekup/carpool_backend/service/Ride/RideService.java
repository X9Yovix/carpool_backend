package com.tekup.carpool_backend.service.Ride;
import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.payload.request.SaveRideRequest;
import com.tekup.carpool_backend.payload.request.UpdateRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

public interface RideService {
    //,Principal connectedUser
    MessageResponse save(SaveRideRequest request);
    List<Ride> findAllRides() ;

    List<Ride> findWithLimit();

    List<Ride> findByDeparture(String departure);
    List<Ride> findByArrival( String arrival);
    List<Ride> findByTime( LocalDateTime time);
    MessageResponse update(UpdateRequest request);
    MessageResponse delete (int id);


}
