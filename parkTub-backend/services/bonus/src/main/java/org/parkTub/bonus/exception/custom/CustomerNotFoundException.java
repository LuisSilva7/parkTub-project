package org.parkTub.bonus.exception.custom;

public class CustomerNotFoundException extends RuntimeException{
    public CustomerNotFoundException(String msg) {
        super(msg);
    }
}
