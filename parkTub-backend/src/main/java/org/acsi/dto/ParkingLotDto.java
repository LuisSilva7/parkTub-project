package org.acsi.dto;

public class ParkingLotDto {
    public String name;
    public int availableSpots;

    public ParkingLotDto(String name, int availableSpots) {
        this.name = name;
        this.availableSpots = availableSpots;
    }
}
