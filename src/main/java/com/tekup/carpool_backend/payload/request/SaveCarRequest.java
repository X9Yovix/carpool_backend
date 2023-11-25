package com.tekup.carpool_backend.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaveCarRequest {
    private String matricule;
    private String brand;
    private String color;

    private int nbPlaces;


}
