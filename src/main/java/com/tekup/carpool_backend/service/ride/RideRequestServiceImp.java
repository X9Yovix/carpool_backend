package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.exception.ResourceNotFoundException;
import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideRequest;
import com.tekup.carpool_backend.model.ride.RideRequestStatus;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;
import com.tekup.carpool_backend.payload.response.ErrorResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.repository.ride.RideRepository;
import com.tekup.carpool_backend.repository.ride.RideRequestRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RideRequestServiceImp implements RideRequestService {
    private final RideRequestRepository rideRequestRepository;
    private final EntityManager entityManager;
    private final RideRepository rideRepository;

    @Override
    public Object createRideRequest(ApplyForRideRequest request, Principal connectedUser) {

        User passenger = (User) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal();
        entityManager.detach(passenger);

        Ride ride = rideRepository.findById(request.getRideId())
                .orElseThrow(() -> new ResourceNotFoundException("Ride not found with ID: " + request.getRideId()));

        if (rideRequestRepository.existsByPassengerAndRide(passenger, ride)) {
            return ErrorResponse.builder()
                    .errors(List.of("You have already requested this ride"))
                    .http_code(HttpStatus.UNAUTHORIZED.value())
                    .build();
        }

        RideRequest rideRequest = RideRequest.builder()
                .passenger(passenger)
                .ride(ride)
                .requestDate(LocalDateTime.now())
                .status(RideRequestStatus.PENDING)
                .build();

        rideRequestRepository.save(rideRequest);

        return MessageResponse.builder()
                .message("Ride request created successfully")
                .http_code(HttpStatus.OK.value())
                .build();
    }
}
