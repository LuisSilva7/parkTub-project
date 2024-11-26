package org.acsi.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parking_lot")
public class ParkingLot extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String address;
    public Double latitude;
    public Double longitude;

    public int totalSpots;
    public int availableSpots;

    @OneToMany(mappedBy = "parkingLot", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<ParkingSession> parkingSessions = new ArrayList<>();

    public static ParkingLot findByAddress(String address) {
        return find("address", address).firstResult();
    }
}
