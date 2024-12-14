package org.parkTub.bonus.exception.custom;

public class BonusNotFoundException extends RuntimeException{
    public BonusNotFoundException(String msg) {
        super(msg);
    }
}

