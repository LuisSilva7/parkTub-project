package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "parking_session")
public class ParkingSession extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String licensePlate;
    public Double latitude;
    public Double longitude;

    public LocalDateTime checkInTime;
    public LocalDateTime checkOutTime;
    public LocalDateTime totalTime;

    public Boolean isActive;

    @ManyToOne
    @JoinColumn(name = "parking_lot_id", nullable = false)
    public ParkingLot parkingLot;

    @OneToOne(mappedBy = "parkingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    public Payment payment;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    public User user;

    public static ParkingSession getActiveParkingSession() {
        return ParkingSession.find("isActive", true).firstResult();
    }
}
