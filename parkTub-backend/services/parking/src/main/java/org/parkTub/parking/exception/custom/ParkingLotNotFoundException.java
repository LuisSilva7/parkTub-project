package org.parkTub.parking.exception.custom;

public class ParkingLotNotFoundException extends RuntimeException{
    public ParkingLotNotFoundException(String msg) {
        super(msg);
    }
}
