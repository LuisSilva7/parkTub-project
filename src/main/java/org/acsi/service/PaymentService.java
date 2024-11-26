package org.acsi.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;
import org.acsi.dto.PaymentDto;
import org.acsi.entity.Payment;
import org.acsi.exceptions.ActiveParkingSessionNotFound;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class PaymentService {

    public List<PaymentDto> getAllPayments() {
        if(Payment.findAll() == null) {
            throw new ActiveParkingSessionNotFound("No payment found!");
        }
        return convertToPaymentsDto(Payment.listAll());
    }

    public Response createCheckoutSession(Long id) throws StripeException {

        Payment payment = Payment.findById(id);

            // Cria um novo preço no Stripe
            SessionCreateParams.LineItem.PriceData.ProductData productData =
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                            .setName("Pagamento de Estacionamento" +
                                    "\nLocal: " + payment.parkingSession.parkingLot.address +
                                    "\nMatrícula: " + payment.parkingSession.licensePlate +
                                    "\nTempo de CheckIn: " + payment.parkingSession.checkInTime +
                                    "\nTempo de CheckOut: " + payment.parkingSession.checkOutTime +
                                    "\nTempo Dispendido no Local: " + payment.parkingSession.totalTime)
                            .build();

            SessionCreateParams.LineItem.PriceData priceData =
                    SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("eur") // Substitua pela moeda desejada
                            .setUnitAmount((long) (payment.amount * 100)) // Valor em centavos
                            .setProductData(productData)
                            .build();

            // Configura o Stripe para criar o preço dinamicamente
            SessionCreateParams.LineItem lineItem =
                    SessionCreateParams.LineItem.builder()
                            .setQuantity(1L)
                            .setPriceData(priceData)
                            .build();

            SessionCreateParams sessionParams =
                    SessionCreateParams.builder()
                            .setMode(SessionCreateParams.Mode.PAYMENT)
                            .setSuccessUrl("http://localhost:8080/payment")
                            .setCancelUrl("http://localhost:8080/error")
                            .addLineItem(lineItem)
                            .build();

            // Cria a sessão no Stripe
            Session session = Session.create(sessionParams);

            // Atualiza a entidade Payment com informações do pagamento
            payment.isPaid = true;
            payment.paymentDate = LocalDateTime.now();
            payment.persist();

            // Retorna o URL da sessão de checkout
            return Response.ok(Map.of("url", session.getUrl())).build();
    }

    public PaymentDto convertToPaymentDto(Payment payment) {
        long remainingMinutes = 2880 - Duration.between(payment.parkingSession.checkOutTime, LocalDateTime.now())
                .toMinutes();
        long hoursRemaining = remainingMinutes / 60;
        long minutesRemaining = remainingMinutes % 60;
        String paymentTimeFrame = String.format("%02dh%02dm", hoursRemaining, minutesRemaining);


        return new PaymentDto(
                payment.id,
                payment.amount,
                payment.isPaid,
                payment.paymentDate,
                paymentTimeFrame,
                payment.parkingSession.licensePlate,
                payment.parkingSession.checkInTime,
                payment.parkingSession.checkOutTime,
                payment.parkingSession.totalTime,
                payment.parkingSession.parkingLot.address
        );
    }

    public List<PaymentDto> convertToPaymentsDto(List<Payment> payments) {
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for(Payment payment : payments) {
            PaymentDto paymentDto = convertToPaymentDto(payment);
            paymentDtos.add(paymentDto);
        }

        return paymentDtos;
    }
}
