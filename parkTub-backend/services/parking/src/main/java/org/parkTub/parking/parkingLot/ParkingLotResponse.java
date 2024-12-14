package org.parkTub.parking.parkingLot;

public record ParkingLotResponse(
        Long id,
        String name,
        int totalSpots,
        int availableSpots,
        Double latitude,
        Double longitude
) {
}
