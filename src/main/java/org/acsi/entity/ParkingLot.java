package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "parking_lot")
public class ParkingLot extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String address;
    private Double latitude;
    private Double longitude;

    private int totalSpots;
    private int availableSpots;

    //relacao com parkingSession
}
