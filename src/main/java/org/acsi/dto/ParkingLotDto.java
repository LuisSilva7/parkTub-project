package org.acsi.dto;

public class ParkingLotDto {
    public String address;
    public int availableSpots;

    public ParkingLotDto(String address, int availableSpots) {
        this.address = address;
        this.availableSpots = availableSpots;
    }
}
