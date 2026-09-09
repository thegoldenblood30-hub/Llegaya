package com.llegaya.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "trips")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "driver_id")
    private User driver;

    private double originLat;
    private double originLng;
    private double destLat;
    private double destLng;

    private LocalDateTime departureTime;
    private double maxCapacityKg;
    private boolean active = true;
}
