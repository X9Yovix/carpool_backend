package com.tekup.carpool_backend.model.Ride;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="rides")
public class Ride {
    @Id
    @GeneratedValue
    private int idR;
    @Column
    private String dep;
    @Column
    private String arr;
    @Column
    private int nbPlacesAv;
    @Column
    private float price ;
    @Column
    private String time;


    public Ride( String dep, String arr, int nbPlacesAv, float price, String time) {
        this.dep = dep;
        this.arr = arr;
        this.nbPlacesAv = nbPlacesAv;
        this.price = price;
        this.time=time;
    }

    public Ride() {}


}
