package org.parkTub.parking.parkingSession;

import java.time.LocalDateTime;

public record ActiveParkingSessionResponse(
        Long id,
        String licensePlate,
        Double latitude,
        Double longitude,
        LocalDateTime checkInTime
) {
}
