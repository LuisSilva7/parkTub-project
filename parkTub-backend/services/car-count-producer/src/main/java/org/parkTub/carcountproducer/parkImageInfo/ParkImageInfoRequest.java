package org.parkTub.carcountproducer.parkImageInfo;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ParkImageInfoRequest(
        @NotNull(message = "carCount cannot be null")
        int carCount,
        @NotNull(message = "timestamp cannot be null")
        LocalDateTime timestamp,
        @NotNull(message = "parkingLotId cannot be null")
        Long parkingLotId
) {
}
