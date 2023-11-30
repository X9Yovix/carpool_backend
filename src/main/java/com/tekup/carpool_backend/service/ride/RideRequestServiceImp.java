package com.tekup.carpool_backend.service.ride;

import com.tekup.carpool_backend.exception.ResourceNotFoundException;
import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.ride.RideRequest;
import com.tekup.carpool_backend.model.ride.RideRequestStatus;
import com.tekup.carpool_backend.model.user.User;
import com.tekup.carpool_backend.payload.request.ApplyForRideRequest;
import com.tekup.carpool_backend.payload.response.ErrorResponse;
import com.tekup.carpool_backend.payload.response.MessageResponse;
import com.tekup.carpool_backend.payload.response.RideRequestResponse;
import com.tekup.carpool_backend.repository.ride.RideRepository;
import com.tekup.carpool_backend.repository.ride.RideRequestRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public RideRequestResponse getAppliedRides(User passenger, RideRequestStatus status, int page, int size) {
        Page<RideRequest> rideRequests;
        Pageable pageable = PageRequest.of(page, size);

        if (status != null) {
            rideRequests = rideRequestRepository.findByPassengerAndStatus(passenger, status, pageable);
        } else {
            rideRequests = rideRequestRepository.findByPassenger(passenger, pageable);
        }

        List<RideRequestResponse.RideRequestInfo> rideRequestInfo = rideRequests.stream()
                .map(rideRequest -> {
                    Ride ride = rideRequest.getRide();
                    return new RideRequestResponse.RideRequestInfo(
                            rideRequest.getId(),
                            rideRequest.getStatus().toString(),
                            rideRequest.getRequestDate(),

                            ride.getStatus().toString(),
                            ride.getDepartureDate(),
                            ride.getDepartureLocation(),
                            ride.getDestinationLocation()
                    );
                })
                .collect(Collectors.toList());

        return RideRequestResponse.builder()
                .ridesRequest(rideRequestInfo)
                .http_code(HttpStatus.OK.value())
                .build();
    }
}
