package org.acsi.request;

import java.time.LocalDateTime;

public class ParkingSessionRequest {
    public String licensePlate;
    public Double latitude;
    public Double longitude;
    public LocalDateTime checkInTime;
}
