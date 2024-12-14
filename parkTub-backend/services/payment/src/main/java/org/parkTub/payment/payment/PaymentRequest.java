package org.parkTub.payment.payment;

import jakarta.validation.constraints.NotNull;

public record PaymentRequest(
        @NotNull(message = "ParkingSession id must not be null")
        Long parkingSessionId,
        @NotNull(message = "ParkingSession total time must not be null")
        Long parkingSessionTotalTime
) {
}
