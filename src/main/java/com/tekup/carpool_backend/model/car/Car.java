package com.tekup.carpool_backend.model.car;

import com.tekup.carpool_backend.model.Ride.Ride;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name="cars")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String matricule;
    @Column
    private String brand;
    @Column
    private String color;
    @Column
    private int nbPlaces;
    @OneToMany(mappedBy="car")
    private List<Ride> rides;

}
