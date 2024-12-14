package org.parkTub.parking.parkingSession;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record ParkingSessionRequest(
        @NotBlank(message = "Parking lot name must not be blank")
        String parkingLotName,
        @NotBlank(message = "License plate must not be blank")
        String licensePlate,
        @NotNull(message = "Latitude must not be null")
        Double latitude,
        @NotNull(message = "Longitude must not be null")
        Double longitude,
        @NotNull(message = "Check-in time must not be null")
        LocalDateTime checkInTime
) {
}
