package com.tekup.carpool_backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRequest {
    private int id;
    private String departure;

    private String arrival;

    private int nbPlacesAv;

    private float price ;

    private String time;


}