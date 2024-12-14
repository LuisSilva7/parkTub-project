package org.parkTub.payment.payment;

import org.parkTub.payment.parkingSession.ParkingSessionResponse;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {
    public PaymentResponse toPaymentResponse(Payment payment, ParkingSessionResponse parkingSessionResponse,
                                             String paymentTimeFrame) {
        return new PaymentResponse(
                payment.getId(),
                payment.getAmount(),
                payment.getIsPaid(),
                payment.getPaymentDate(),
                paymentTimeFrame,
                parkingSessionResponse.licensePlate(),
                parkingSessionResponse.checkInTime(),
                parkingSessionResponse.checkOutTime(),
                parkingSessionResponse.totalTime(),
                parkingSessionResponse.address()
        );
    }
}
