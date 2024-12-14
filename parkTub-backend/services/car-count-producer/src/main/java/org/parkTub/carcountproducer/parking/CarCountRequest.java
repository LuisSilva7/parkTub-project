package org.parkTub.carcountproducer.parking;

import java.time.LocalDateTime;

public record CarCountRequest(
        Long id,
        int carCount,
        LocalDateTime timestamp,
        Long parkingLotId
) {
}
