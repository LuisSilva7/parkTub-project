package org.acsi.service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.enterprise.context.ApplicationScoped;
import org.acsi.dto.BonusDto;
import org.acsi.dto.PaymentDto;
import org.acsi.entity.Bonus;
import org.acsi.entity.Payment;
import org.acsi.entity.User;
import org.acsi.exceptions.ObjectNotFound;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class PaymentService {

    public List<PaymentDto> getAllPayments() {
        if(Payment.findAll() == null) {
            throw new ObjectNotFound("No payment found!");
        }
        return convertToPaymentsDto(Payment.listAll());
    }

    public String createCheckoutSession(Long id, Double discount) throws StripeException {

        Payment payment = Payment.findById(id);
        long amount = (long) (payment.amount * 100);

        // Calcula o valor do desconto
        long discountAmount = discount != 0.0 ? (long) ((payment.amount * (discount / 100.0)) * 100) : 0;

        // Aplica o desconto
        amount = amount - discountAmount;

        SessionCreateParams.LineItem.PriceData.ProductData productData =
                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                        .setName("Pagamento de Estacionamento")
                        .setDescription("Local: " + payment.parkingSession.parkingLot.address +
                                " | Matrícula: " + payment.parkingSession.licensePlate +
                                " | CheckIn: " + payment.parkingSession.checkInTime
                                .toString().replace("T", " ") +
                                " | CheckOut: " + payment.parkingSession.checkOutTime
                                .toString().replace("T", " ") +
                                " | Tempo Usufruido: " + payment.parkingSession.totalTime + "min")
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

        payment.isPaid = true;
        payment.paymentDate = LocalDateTime.now();
        payment.persist();

        User.findByName("Luís").bonus.clear();

        return session.getUrl();
    }

    public List<BonusDto> getAllBonus() {
        if(Bonus.findAll() == null) {
            throw new ObjectNotFound("No bonus found!");
        }
        return convertToBonusDto(Bonus.listAll());
    }

    public Object activateBonus(Long id, int bonusPoints) {
        Bonus bonus = Bonus.findById(id);
        User user = User.findByName("Luís");
        user.points = user.points - bonusPoints;
        user.bonus.add(bonus);

        return convertToBonusDto(bonus, true, user.points);
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

    public BonusDto convertToBonusDto(Bonus bonus, boolean isActive, int userPoints) {
        return new BonusDto(
                bonus.id,
                bonus.description,
                bonus.discountPercentage,
                bonus.pointsRequired,
                isActive,
                userPoints
        );
    }

    public List<BonusDto> convertToBonusDto(List<Bonus> bonus) {
        List<BonusDto> bonusDtos = new ArrayList<>();
        User user = User.findByName("Luís");
        for(Bonus bo : bonus) {
            if(user.bonus.stream().anyMatch(bonusSearch ->
                    bonusSearch.pointsRequired == bo.pointsRequired)) {
                BonusDto bonusDto = convertToBonusDto(bo, true, user.points);
                bonusDtos.add(bonusDto);
            } else {
                BonusDto bonusDto = convertToBonusDto(bo, false, user.points);
                bonusDtos.add(bonusDto);
            }
        }

        return bonusDtos;
    }

    public int getUserPoints() {
        return User.findByName("Luís").points;
    }

    public Double getActiveDiscount() {
        User user = User.findByName("Luís");
        if(user.bonus.isEmpty()) {
            return 0.0;
        } else {
            Bonus bonus = user.bonus.getFirst();
            return bonus.discountPercentage;
        }
    }
}
