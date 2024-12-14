package org.parkTub.parking.payment;

public record PaymentRequest(
        Long parkingSessionId,
        Long parkingSessionTotalTime
) {
}
