package org.parkTub.payment.parkingSession;

import java.time.LocalDateTime;

public record ParkingSessionResponse(
        Long id,
        String licensePlate,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        Long totalTime,

        String address
) {
}
