package com.tekup.carpool_backend.service.Ride;

import com.tekup.carpool_backend.model.Ride.Ride;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.SaveRideRequest;
import com.tekup.carpool_backend.payload.request.UpdateRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.Ride.RideRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class RideServiceImp implements RideService {

    private final RideRepository repository;

    public RideServiceImp(RideRepository repository) {
        this.repository=repository;
    }
    @Override
//, Principal connectedUser
    public MessageResponse save(SaveRideRequest request) {
       // User user = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();


            Ride r = Ride.builder()
                    .departure(request.getDeparture())
                    .arrival(request.getArrival())
                    .nbPlacesAv(request.getNbPlacesAv())
                    .price(request.getPrice())
                    .time(LocalDateTime.parse(request.getTime()))
                    .build();
            Ride savedRide = repository.save(r);
            return MessageResponse.builder()
                    .message("Ride has been added successffly")
                    .http_code(HttpStatus.OK.value())
                    .build();

    }

    @Override
    public List<Ride> findAllRides() {


       return repository.findAll();


    }
    @Override
    public List<Ride> findWithLimit() {

        return repository.findWithLimit();


    }

    @Override
    public List<Ride> findByDeparture(String departure) {
        return repository.findByDeparture(departure);


    }

    @Override
    public List<Ride> findByArrival(String arrival) {
        return repository.findByArrival(arrival);
    }

    @Override
    public List<Ride> findByTime(LocalDateTime time) {
        return repository.findByTime(LocalDateTime.parse(String.valueOf(time)));
    }

    @Override
    public MessageResponse update(UpdateRequest request) {
        Ride r = Ride.builder()
                .id(request.getId())
                .departure(request.getDeparture())
                .arrival(request.getArrival())
                .nbPlacesAv(request.getNbPlacesAv())
                .price(request.getPrice())
                .time(LocalDateTime.parse(request.getTime()))
                .build();
         Ride updatedRide = repository.save(r);
        return MessageResponse.builder()
                .message("Ride has been updated successffly")
                .http_code(HttpStatus.OK.value())
                .build();
        }

        @Override
        public MessageResponse delete ( int id){


        MessageResponse deletedRide = repository.deleteById(id);
            return MessageResponse.builder()
                    .message("Ride has been deleted successffly")
                    .http_code(HttpStatus.OK.value())
                    .build();
        }



}