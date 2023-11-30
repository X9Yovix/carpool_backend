package com.tekup.carpool_backend.payload.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ApplyForRideRequest {
    private Long rideId;
}
