package org.acsi.dto;

import java.time.LocalDateTime;

public class PaymentDto {

    public Long id;
    public Double amount;
    public Boolean isPaid;
    public LocalDateTime paymentDate;
    public String paymentTimeFrame;

    public String licensePlate;
    public LocalDateTime checkInTime;
    public LocalDateTime checkOutTime;
    public Long totalTime;

    public String address;

    public PaymentDto(Long id, Double amount, Boolean isPaid, LocalDateTime paymentDate, String paymentTimeFrame,
                      String licensePlate, LocalDateTime checkInTime, LocalDateTime checkOutTime,
                      Long totalTime, String address) {
        this.id = id;
        this.amount = amount;
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.paymentTimeFrame = paymentTimeFrame;
        this.licensePlate = licensePlate;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.totalTime = totalTime;
        this.address = address;
    }
}
