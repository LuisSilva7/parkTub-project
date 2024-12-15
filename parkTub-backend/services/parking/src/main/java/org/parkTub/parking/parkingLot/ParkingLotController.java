package org.parkTub.parking.parkingLot;

import lombok.RequiredArgsConstructor;
import org.parkTub.parking.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lots")
@RequiredArgsConstructor
public class ParkingLotController {

    private final ParkingLotService parkingLotService;
    private final SseNotifier sseNotifier;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ParkingLotResponse>>> findAllParkingLots() {

        List<ParkingLotResponse> parkingLotResponses = parkingLotService.findAll();

        return ResponseEntity.ok(new ApiResponse<>(
                "Parking lots obtained successfully!", parkingLotResponses));
    }

    @GetMapping("/update-available-spots")
    public SseEmitter subscribe() {
        return sseNotifier.subscribe();
    }
}
