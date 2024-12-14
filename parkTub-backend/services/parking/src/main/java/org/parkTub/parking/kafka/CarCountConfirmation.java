package org.parkTub.parking.kafka;

import java.time.LocalDateTime;

public record CarCountConfirmation(
        Long id,
        int carCount,
        LocalDateTime timestamp,
        Long parkingLotId
) {
}
