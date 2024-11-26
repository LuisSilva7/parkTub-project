package org.acsi.dto;

import java.time.LocalDateTime;

public class ActiveParkingSessionDto {
    public String licensePlate;
    public Double latitude;
    public Double longitude;
    public LocalDateTime checkInTime;

    public ActiveParkingSessionDto(String licensePlate, Double latitude, Double longitude, LocalDateTime checkInTime) {
        this.licensePlate = licensePlate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.checkInTime = checkInTime;
    }
}
