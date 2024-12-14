package org.parkTub.parking.exception.custom;

public class ActiveParkingSessionNotFoundException extends RuntimeException{
    public ActiveParkingSessionNotFoundException(String msg) {
        super(msg);
    }
}
