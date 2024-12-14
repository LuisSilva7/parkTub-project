package org.parkTub.payment.exception.custom;

public class PaymentNotFoundException extends RuntimeException{
    public PaymentNotFoundException(String msg) {
        super(msg);
    }
}
