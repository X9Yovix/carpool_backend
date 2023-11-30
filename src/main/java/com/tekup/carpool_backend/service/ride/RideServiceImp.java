package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideStatus;
import com.tekup.carpool_backend.model.user.Car;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.AddRideRequest;
import com.tekup.carpool_backend.payload.request.FilterRideRequest;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.payload.response.RideResponse;
import com.tekup.carpool_backend.repository.ride.RideRepository;
import com.tekup.carpool_backend.repository.user.CarRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RideServiceImp implements RideService {
    private final RideRepository rideRepository;
    private final EntityManager entityManager;
    private final CarRepository carRepository;

    @Override
    public Object createRide(AddRideRequest request, Principal connectedUser) {
        User authUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        entityManager.detach(authUser);

        Car car = carRepository.findById(request.getCard_id()).orElseThrow();

        Ride ride = Ride.builder()
                .departureLocation(request.getDepartureLocation())
                .destinationLocation(request.getDestinationLocation())
                .departureDate(request.getDepartureDate())
                .price(request.getPrice())
                .status(RideStatus.ACTIVE)
                .car(car)
                .driver(authUser)
                .build();

        rideRepository.save(ride);

        return MessageResponse.builder()
                .message("Ride created successfully")
                .http_code(HttpStatus.OK.value())
                .build();
    }

    @Override
    public Object getRidesCreatedByAuthenticatedDriver(Principal connectedUser) {
        User authUser = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();

        List<Ride> rides = rideRepository.findByDriverId(authUser.getId());

        List<RideResponse.RideInfo> rideInfo = rides.stream()
                .map(ride -> new RideResponse.RideInfo(
                        ride.getId(),
                        ride.getDepartureLocation(),
                        ride.getDestinationLocation(),
                        ride.getDepartureDate(),
                        ride.getStatus().toString(),
                        ride.getPrice(),
                        ride.getCar().getId(),
                        ride.getCar().getBrand(),
                        ride.getCar().getModel(),
                        ride.getCar().getColor(),
                        ride.getCar().getSeats()
                ))
                .collect(Collectors.toList());

        return RideResponse.builder()
                .rides(rideInfo)
                .http_code(HttpStatus.OK.value())
                .build();
    }
    @Override
    public Object filterRides(FilterRideRequest request) {
        List<Ride> filteredRides = rideRepository.findByFilters(
                request.getDeparture(),
                request.getDestination(),
                request.getStatus(),
                request.getMinPrice(),
                request.getMaxPrice()
        );
        return formatFilteredResults(filteredRides);
    }
    private Object formatFilteredResults(List<Ride> rides) {
        List<RideResponse.RideInfo> rideInfo = rides.stream()
                .map(ride -> new RideResponse.RideInfo(
                        ride.getId(),
                        ride.getDepartureLocation(),
                        ride.getDestinationLocation(),
                        ride.getDepartureDate(),
                        ride.getStatus().toString(),
                        ride.getPrice(),
                        ride.getCar().getId(),
                        ride.getCar().getBrand(),
                        ride.getCar().getModel(),
                        ride.getCar().getColor(),
                        ride.getCar().getSeats()
                ))
                .collect(Collectors.toList());
        return RideResponse.builder()
                .rides(rideInfo)
                .http_code(HttpStatus.OK.value())
                .build();
    }
}
