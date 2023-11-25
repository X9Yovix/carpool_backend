package com.tekup.carpool_backend.model.Ride;

import com.tekup.carpool_backend.model.car.Car;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name="rides")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ride {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    @Column
    private String departure;
    @Column
    private String arrival;
    @Column
    private int nbPlacesAv;
    @Column
    private float price ;
    @Column
    private LocalDateTime time;
    @ManyToOne
    @JoinColumn(name="matricule")
    private Car car;
}
