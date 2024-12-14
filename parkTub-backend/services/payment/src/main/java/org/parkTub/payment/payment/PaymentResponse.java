package org.parkTub.payment.payment;

import java.time.LocalDateTime;

public record PaymentResponse(
        Long id,
        Double amount,
        Boolean isPaid,
        LocalDateTime paymentDate,
        String paymentTimeFrame,

        String licensePlate,
        LocalDateTime checkInTime,
        LocalDateTime checkOutTime,
        Long totalTime,

        String address
) {
}
