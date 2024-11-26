package org.acsi.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.ParkingSession;
import org.acsi.entity.User;
import org.acsi.exceptions.ActiveParkingSessionNotFound;
import org.acsi.request.ParkingSessionRequest;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ParkingService {

    public ActiveParkingSessionDto getActiveParkingSession() {
        if(ParkingSession.getActiveParkingSession() == null) {
            throw new ActiveParkingSessionNotFound("Active parking Session not found!");
        }
        return convertToActiveParkingSessionDto(ParkingSession.getActiveParkingSession());
    }

    public ActiveParkingSessionDto createParkingSession(ParkingSessionRequest parkingSessionRequest) {
        ParkingSession parkingSession = new ParkingSession();
        parkingSession.licensePlate = parkingSessionRequest.licensePlate;
        parkingSession.latitude = parkingSessionRequest.latitude;
        parkingSession.longitude = parkingSessionRequest.longitude;
        parkingSession.checkInTime = parkingSessionRequest.checkInTime;
        parkingSession.isActive = true;

        parkingSession.parkingLot = ParkingLot.findByAddress("Avenida da Liberdade");
        parkingSession.user = User.findByName("Luís");

        parkingSession.persist();
        return convertToActiveParkingSessionDto(parkingSession);
    }

    public ActiveParkingSessionDto convertToActiveParkingSessionDto(ParkingSession parkingSession) {
        return new ActiveParkingSessionDto(
                parkingSession.licensePlate,
                parkingSession.latitude,
                parkingSession.longitude,
                parkingSession.checkInTime
        );
    }

    public List<ActiveParkingSessionDto> convertToActiveParkingSessionDtos(List<ParkingSession> parkingSessions) {
        List<ActiveParkingSessionDto> activeParkingSessionDtos = new ArrayList<>();
        for(ParkingSession parkingSession : parkingSessions) {
            ActiveParkingSessionDto activeParkingSessionDto = convertToActiveParkingSessionDto(parkingSession);
            activeParkingSessionDtos.add(activeParkingSessionDto);
        }

        return activeParkingSessionDtos;
    }
}
