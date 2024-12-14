package org.parkTub.parking.parkingSession;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.parkTub.parking.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-sessions")
@RequiredArgsConstructor
public class ParkingSessionController {

    private final ParkingSessionService parkingSessionService;

    @GetMapping("/{parking-session-id}")
    public ResponseEntity<ApiResponse<ParkingSessionResponse>> findParkingSessionById(
            @PathVariable("parking-session-id") Long parkingSessionId) {
        ParkingSessionResponse parkingSessionResponse = parkingSessionService.findById(parkingSessionId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking session obtained successfully!", parkingSessionResponse));
    }

    @PostMapping("/active-session/customers/{customer-id}")
    public ResponseEntity<ApiResponse<Long>> createParkingSession(
            @PathVariable("customer-id") Long customerId,
            @RequestBody @Valid ParkingSessionRequest request) {
        Long parkingSessionId = parkingSessionService
                .createParkingSession(request, customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking session created successfully!", parkingSessionId));
    }

    @GetMapping("/active-session/customers/{customer-id}")
    public ResponseEntity<ApiResponse<ActiveParkingSessionResponse>> findActiveParkingSession(
            @PathVariable("customer-id") Long customerId) {
        ActiveParkingSessionResponse activeParkingSessionResponse = parkingSessionService
                .findActiveParkingSession(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking session obtained successfully!", activeParkingSessionResponse));
    }

    @PutMapping("/inactive-session/customers/{customer-id}")
    public ResponseEntity<ApiResponse<Long>> updateParkingSession(
            @PathVariable("customer-id") Long customerId,
            @RequestBody @Valid UpdateParkingSessionRequest request) {
        Long inactiveParkingSessionId = parkingSessionService
                .updateParkingSession(customerId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking session updated successfully!", inactiveParkingSessionId));
    }

    @GetMapping("/customers/{customer-id}")
    public ResponseEntity<ApiResponse<List<ParkingSessionResponse>>> findAllParkingSessionsByCostumerId(
            @PathVariable("customer-id") Long customerId) {
        List<ParkingSessionResponse> parkingSessionResponses = parkingSessionService
                .findAllParkingSessionsByCostumerId(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking sessions obtained successfully!", parkingSessionResponses));
    }
}
