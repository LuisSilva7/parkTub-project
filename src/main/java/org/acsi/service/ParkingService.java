package org.acsi.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.acsi.dto.ActiveParkingSessionDto;
import org.acsi.dto.InactiveParkingSessionDto;
import org.acsi.dto.ParkingLotDto;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.ParkingSession;
import org.acsi.entity.Payment;
import org.acsi.entity.User;
import org.acsi.exceptions.ObjectNotFound;
import org.acsi.request.ParkingSessionRequest;
import org.acsi.request.UpdateParkingSessionRequest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class ParkingService {

    public List<ParkingLotDto> getAllParkingLots() {
        List<ParkingLot> parkingLots = ParkingLot.listAll();
        return convertToParkingLotDtos(parkingLots);
    }

    public ActiveParkingSessionDto getActiveParkingSession() {
        if(ParkingSession.getActiveParkingSession() == null) {
            throw new ObjectNotFound("Active parking Session not found!");
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

        parkingSession.parkingLot = ParkingLot.findByAddress(parkingSessionRequest.address);
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

    public ParkingLotDto convertToParkingLotDto(ParkingLot parkingLot) {
        return new ParkingLotDto(
                parkingLot.address,
                parkingLot.availableSpots
        );
    }

    public List<ParkingLotDto> convertToParkingLotDtos(List<ParkingLot> parkingLots) {
        List<ParkingLotDto> parkingLotDtos = new ArrayList<>();
        for(ParkingLot parkingLot : parkingLots) {
            ParkingLotDto parkingLotDto = convertToParkingLotDto(parkingLot);
            parkingLotDtos.add(parkingLotDto);
        }

        return parkingLotDtos;
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
