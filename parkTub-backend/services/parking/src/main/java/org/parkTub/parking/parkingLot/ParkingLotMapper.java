package org.parkTub.parking.parkingLot;

import org.springframework.stereotype.Service;

@Service
public class ParkingLotMapper {
    public ParkingLotResponse toParkingLotResponse(ParkingLot parkingLot) {
        if(parkingLot == null) {
            return null;
        }

        return new ParkingLotResponse(
                parkingLot.getId(),
                parkingLot.getName(),
                parkingLot.getTotalSpots(),
                parkingLot.getAvailableSpots(),
                parkingLot.getLatitude(),
                parkingLot.getLongitude()
        );
    }
}
