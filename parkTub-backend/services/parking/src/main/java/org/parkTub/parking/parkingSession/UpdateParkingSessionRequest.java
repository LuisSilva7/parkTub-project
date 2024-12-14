package org.parkTub.parking.parkingSession;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record UpdateParkingSessionRequest(
        @NotNull(message = "Check-out time must not be null")
        LocalDateTime checkOutTime
) {
}
