package org.acsi.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.dto.InactiveParkingSessionDto;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.ParkingSession;
import org.acsi.entity.Payment;
import org.acsi.entity.User;
import org.acsi.exceptions.ActiveParkingSessionNotFound;
import org.acsi.request.ParkingSessionRequest;
import org.acsi.request.UpdateParkingSessionRequest;

import java.time.Duration;

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

    public InactiveParkingSessionDto updateParkingSession(UpdateParkingSessionRequest updateParkingSessionRequest) {
        ParkingSession parkingSession = ParkingSession.getActiveParkingSession();
        parkingSession.checkOutTime = updateParkingSessionRequest.checkOutTime;
        parkingSession.totalTime = Duration.between(parkingSession.checkInTime, parkingSession.checkOutTime).toMinutes();
        parkingSession.isActive = false;

        Payment payment = new Payment();
        double amount = parkingSession.totalTime * 0.1;
        payment.amount = Math.round(amount * 100.0) / 100.0;
        payment.isPaid = false;
        payment.parkingSession = parkingSession;
        parkingSession.payment = payment;

        parkingSession.persist();
        return convertToInactiveParkingSessionDto(parkingSession);
    }

    public ActiveParkingSessionDto convertToActiveParkingSessionDto(ParkingSession parkingSession) {
        return new ActiveParkingSessionDto(
                parkingSession.licensePlate,
                parkingSession.latitude,
                parkingSession.longitude,
                parkingSession.checkInTime
        );
    }

    public InactiveParkingSessionDto convertToInactiveParkingSessionDto(ParkingSession parkingSession) {
        return new InactiveParkingSessionDto(
                parkingSession.licensePlate,
                parkingSession.totalTime,
                parkingSession.checkInTime,
                parkingSession.checkOutTime
        );
    }
}
