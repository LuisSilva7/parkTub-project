package org.parkTub.payment.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.parkTub.payment.payment.Payment;
import org.parkTub.payment.payment.PaymentRepository;
import org.parkTub.payment.payment.PaymentService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultPaymentsConfig implements CommandLineRunner {

    private final PaymentService paymentService;
    private final PaymentRepository parkingLotRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if(paymentService.isPaymentRepositoryEmpty()) {
            // create 1st payment
            paymentService.createDefaultPayment(
                    Payment.builder()
                            .paymentDate(LocalDateTime.parse("2024-12-04T20:05:00"))
                            .amount(6.0)
                            .isPaid(true)
                            .parkingSessionId(1L)
                            .build()
            );

            // create 2nd payment
            paymentService.createDefaultPayment(
                    Payment.builder()
                            .paymentDate(LocalDateTime.parse("2024-12-06T19:45:00"))
                            .amount(3.0)
                            .isPaid(true)
                            .parkingSessionId(2L)
                            .build()
            );

            // create 3rd payment
            paymentService.createDefaultPayment(
                    Payment.builder()
                            .paymentDate(LocalDateTime.parse("2024-12-09T15:20:00"))
                            .amount(12.0)
                            .isPaid(true)
                            .parkingSessionId(3L)
                            .build()
            );

            // create 4th payment
            paymentService.createDefaultPayment(
                    Payment.builder()
                            .paymentDate(LocalDateTime.parse("2024-12-10T17:35:00"))
                            .amount(2.0)
                            .isPaid(true)
                            .parkingSessionId(4L)
                            .build()
            );

            // create 5th payment
            paymentService.createDefaultPayment(
                    Payment.builder()
                            .paymentDate(null)
                            .amount(6.0)
                            .isPaid(false)
                            .parkingSessionId(5L)
                            .build()
            );

            System.out.println("Default payments were created!");
        } else {
            System.out.println("Default payments already exist!");
        }
    }
}