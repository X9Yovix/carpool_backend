package com.tekup.carpool_backend.payload.response;

import com.tekup.carpool_backend.model.ride.Ride;
import com.tekup.carpool_backend.model.user.Car;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RideResponse {
    private List<RideInfo> rides;
    private int http_code;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class RideInfo {
        private Long id;
        private String departureLocation;
        private String destinationLocation;
        private LocalDateTime departureDate;
        private String rideStatus;
        private Long carId;
        private String carBrand;
        private String carModel;
        private String carColor;
        private int carSeats;
    }
}
