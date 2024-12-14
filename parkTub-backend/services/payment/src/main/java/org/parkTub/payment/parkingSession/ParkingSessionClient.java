package org.parkTub.payment.parkingSession;

import org.parkTub.payment.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(
        name = "parking-service",
        url = "${application.config.parking-session-url}"
)
public interface ParkingSessionClient {

    @GetMapping("/customers/{customer-id}")
    ResponseEntity<ApiResponse<List<ParkingSessionResponse>>> findAllParkingSessionsByCostumerId(
            @PathVariable("customer-id") Long customerId);

    @GetMapping("/{parking-session-id}")
    ResponseEntity<ApiResponse<ParkingSessionResponse>> findParkingSessionById(
            @PathVariable("parking-session-id") Long parkingSessionId);
}