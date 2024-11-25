package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "parking_session")
public class ParkingSession extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String licensePlate;
    private Double latitude;
    private Double longitude;

    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private LocalDateTime totalTime;

    private Boolean isActive;
    private Double totalAmount;

    //relacao com parkingLot
    @ManyToOne
    private ParkingLot parkingLot; // Estacionamento onde ocorreu o check-in

    //relacao com payment

    //relacao com user
}
