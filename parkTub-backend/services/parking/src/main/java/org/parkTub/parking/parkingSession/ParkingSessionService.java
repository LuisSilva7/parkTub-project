package org.parkTub.parking.parkingSession;

import lombok.RequiredArgsConstructor;
import org.parkTub.parking.customer.CustomerClient;
import org.parkTub.parking.customer.CustomerResponse;
import org.parkTub.parking.exception.custom.ActiveParkingSessionNotFoundException;
import org.parkTub.parking.exception.custom.CustomerNotFoundException;
import org.parkTub.parking.exception.custom.ParkingLotNotFoundException;
import org.parkTub.parking.exception.custom.ParkingSessionNotFoundException;
import org.parkTub.parking.parkingLot.ParkingLot;
import org.parkTub.parking.parkingLot.ParkingLotService;
import org.parkTub.parking.payment.PaymentClient;
import org.parkTub.parking.payment.PaymentRequest;
import org.parkTub.parking.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParkingSessionService {

    private final ParkingSessionRepository parkingSessionRepository;
    private final ParkingSessionMapper parkingSessionMapper;
    private final CustomerClient customerClient;
    private final PaymentClient paymentClient;
    private final ParkingLotService parkingLotService;

    public boolean isParkingSessionRepositoryEmpty() {
        return parkingSessionRepository.count() == 0;
    }

    public void createDefaultParkingSession(ParkingSession parkingSession) {
        parkingSessionRepository.save(parkingSession);
    }

    public ParkingSessionResponse findById(Long parkingSessionId) {
        return parkingSessionRepository.findById(parkingSessionId)
                .map(parkingSessionMapper::toParkingSessionResponse)
                .orElseThrow(() -> new ParkingSessionNotFoundException(
                        "No parking session found with the id: " + parkingSessionId));
    }

    public Long createParkingSession(ParkingSessionRequest request, Long customerId) {
        ParkingLot parkingLot = parkingLotService.findByName(request.parkingLotName());
        if(parkingLot == null) {
            throw new ParkingLotNotFoundException("No parking lot found with the name: "
                    + request.parkingLotName());
        }

        ResponseEntity<ApiResponse<CustomerResponse>> response = customerClient.findCustomerById(customerId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Optional<CustomerResponse> customerResponse = Optional.ofNullable(response.getBody().getData());

            if (customerResponse.isEmpty()) {
                throw new CustomerNotFoundException("No customer found with the ID: " + customerId);
            }

            ParkingSession parkingSession = parkingSessionMapper.toParkingSession(
                    request, parkingLot, customerResponse.get().id());

            return parkingSessionRepository.save(parkingSession).getId();
        }

        throw new CustomerNotFoundException("No customer found with the ID: " + customerId);
    }

    public ActiveParkingSessionResponse findActiveParkingSession(Long customerId) {
        return parkingSessionRepository.findActiveParkingSession(customerId)
                .map(parkingSessionMapper::toActiveParkingSessionResponse)
                .orElseThrow(() -> new ActiveParkingSessionNotFoundException(
                        "Active parking Session not found!"));
    }

    public Long updateParkingSession(Long customerId, UpdateParkingSessionRequest request) {
        ParkingSession activeParkingSession = parkingSessionRepository.findActiveParkingSession(customerId)
                .orElseThrow(() -> new ActiveParkingSessionNotFoundException(
                        "Active parking Session not found!"));

        activeParkingSession.setCheckOutTime(request.checkOutTime());
        activeParkingSession.setTotalTime(Duration.between(
                        activeParkingSession.getCheckInTime(), activeParkingSession.getCheckOutTime())
                .toMinutes());
        activeParkingSession.setIsActive(false);

        paymentClient.createPayment(new PaymentRequest(
                activeParkingSession.getId(),
                activeParkingSession.getTotalTime()
        ));

        return parkingSessionRepository.save(activeParkingSession).getId();
    }

    public List<ParkingSessionResponse> findAllParkingSessionsByCostumerId(Long customerId) {
        List<ParkingSession> parkingSessions = parkingSessionRepository.findAllByCustomerId(customerId);

        if (parkingSessions.isEmpty()) {
            throw new ParkingSessionNotFoundException(
                    "No parking sessions were found for costumer with ID: " + customerId);
        }

        return parkingSessions.stream()
                .map(parkingSessionMapper::toParkingSessionResponse)
                .toList();
    }
}
