package org.parkTub.parking.parkingSession;

import org.parkTub.parking.parkingLot.ParkingLot;
import org.springframework.stereotype.Service;

@Service
public class ParkingSessionMapper {

    public ParkingSession toParkingSession(ParkingSessionRequest request, ParkingLot parkingLot, Long customerId) {
        if(request == null) {
            return null;
        }

        ParkingSession parkingSession = new ParkingSession();
        parkingSession.setLicensePlate(request.licensePlate());
        parkingSession.setLatitude(request.latitude());
        parkingSession.setLongitude(request.longitude());
        parkingSession.setCheckInTime(request.checkInTime());
        parkingSession.setIsActive(true);
        parkingSession.setParkingLot(parkingLot);
        parkingSession.setCustomerId(customerId);

        return parkingSession;
    }

    public ActiveParkingSessionResponse toActiveParkingSessionResponse(ParkingSession parkingSession) {
        return new ActiveParkingSessionResponse(
                parkingSession.getId(),
                parkingSession.getLicensePlate(),
                parkingSession.getLatitude(),
                parkingSession.getLongitude(),
                parkingSession.getCheckInTime()
        );
    }

    public ParkingSessionResponse toParkingSessionResponse(ParkingSession parkingSession) {
        return new ParkingSessionResponse(
                parkingSession.getId(),
                parkingSession.getLicensePlate(),
                parkingSession.getCheckInTime(),
                parkingSession.getCheckOutTime(),
                parkingSession.getTotalTime(),
                parkingSession.getParkingLot().getAddress()
        );
    }
}
