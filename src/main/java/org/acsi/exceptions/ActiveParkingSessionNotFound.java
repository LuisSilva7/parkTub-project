package org.acsi.exceptions;

public class ActiveParkingSessionNotFound extends RuntimeException{
    public ActiveParkingSessionNotFound(String message) {
        super(message);
    }
}
