package org.parkTub.parking.exception.custom;

public class ParkingSessionNotFoundException extends RuntimeException{
    public ParkingSessionNotFoundException(String msg) {
        super(msg);
    }
}
