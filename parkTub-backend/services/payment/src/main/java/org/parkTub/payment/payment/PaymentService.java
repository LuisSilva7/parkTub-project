package org.parkTub.payment.payment;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import lombok.RequiredArgsConstructor;
import org.parkTub.payment.bonus.BonusClient;
import org.parkTub.payment.customer.CustomerClient;
import org.parkTub.payment.exception.custom.ParkingSessionNotFoundException;
import org.parkTub.payment.exception.custom.PaymentNotFoundException;
import org.parkTub.payment.parkingSession.ParkingSessionClient;
import org.parkTub.payment.parkingSession.ParkingSessionResponse;
import org.parkTub.payment.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ParkingSessionClient parkingSessionClient;
    private final CustomerClient customerClient;
    private final BonusClient bonusClient;

    public boolean isPaymentRepositoryEmpty() {
        return paymentRepository.count() == 0;
    }

    public void createDefaultPayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void createPayment(PaymentRequest request) {
        Payment payment = new Payment();
        double amount = request.parkingSessionTotalTime() * 0.1;
        payment.setAmount(Math.round(amount * 100.0) / 100.0);
        payment.setIsPaid(false);
        payment.setParkingSessionId(request.parkingSessionId());
        paymentRepository.save(payment);
    }

    public List<PaymentResponse> findAllByCustomerId(Long customerId) {
        List<Payment> payments = paymentRepository.findAll();

        if (payments.isEmpty()) {
            throw new PaymentNotFoundException("No payments were found!");
        }


        ResponseEntity<ApiResponse<List<ParkingSessionResponse>>> response = parkingSessionClient
                .findAllParkingSessionsByCostumerId(customerId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<ParkingSessionResponse> parkingSessionResponses = response.getBody().getData();

            if (parkingSessionResponses.isEmpty()) {
                throw new ParkingSessionNotFoundException(
                        "No parking sessions were found for costumer with ID: " + customerId);
            }

            List<PaymentResponse> customerPayments = new ArrayList<>();

            for (Payment payment : payments) {
                for(ParkingSessionResponse parkingSessionResponse : parkingSessionResponses) {
                    if(parkingSessionResponse.id().equals(payment.getParkingSessionId())) {
                        long remainingMinutes = 2880 - Duration.between(
                                parkingSessionResponse.checkOutTime(), LocalDateTime.now()).toMinutes();
                        long hoursRemaining = remainingMinutes / 60;
                        long minutesRemaining = remainingMinutes % 60;
                        String paymentTimeFrame = String.format("%02dh%02dm", hoursRemaining, minutesRemaining);

                        customerPayments.add(paymentMapper.toPaymentResponse(
                                payment, parkingSessionResponse, paymentTimeFrame));
                    }
                }
            }

            return customerPayments;
        }

        throw new ParkingSessionNotFoundException(
                "No parking sessions were found for costumer with ID: " + customerId);
    }

    public String createCheckoutSession(Long paymentId, CheckoutSessionRequest request, Long customerId)
            throws StripeException {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new PaymentNotFoundException("Payment with ID: " + paymentId + " not found!"));

        ResponseEntity<ApiResponse<ParkingSessionResponse>> response = parkingSessionClient
                .findParkingSessionById(payment.getParkingSessionId());

        ParkingSessionResponse parkingSessionResponse = Objects.requireNonNull(response.getBody()).getData();

        if (parkingSessionResponse == null) {
            throw new ParkingSessionNotFoundException(
                    "No parking sessions were found");
        }

        long amount = (long) (payment.getAmount() * 100);

        // Calcula o valor do desconto
        long discountAmount = request.discount() != 0.0 ?
                (long) ((payment.getAmount() * (request.discount() / 100.0)) * 100) : 0;

        // Aplica o desconto
        amount = amount - discountAmount;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Pagamento de Estacionamento")
                        .setDescription("Local: " + parkingSessionResponse.address() +
                                " | Matrícula: " + parkingSessionResponse.licensePlate() +
                                " | CheckIn: " + parkingSessionResponse.checkInTime()
                                .toString().replace("T", " ") +
                                " | CheckOut: " + parkingSessionResponse.checkOutTime()
                                .toString().replace("T", " ") +
                                " | Tempo Usufruido: " + parkingSessionResponse.totalTime() + "min")
                        .build();

        SessionCreateParams.LineItem.PriceData priceData =
                SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur") // Substitua pela moeda desejada
                        .setUnitAmount(amount) // Valor em centavos
                        .setProductData(productData)
                        .build();

        SessionCreateParams.LineItem lineItem =
                SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(priceData)
                        .build();

        SessionCreateParams sessionParams =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl("http://localhost:5173/")
                        .setCancelUrl("http://localhost:5173/error")
                        .addLineItem(lineItem)
                        .build();

        Session session = Session.create(sessionParams);

        payment.setIsPaid(true);
        payment.setPaymentDate(LocalDateTime.now());
        paymentRepository.save(payment);

        customerClient.addPoints(customerId);

        bonusClient.removeCustomerId(customerId);

        return session.getUrl();
    }
}
