package org.acsi.dto;

import java.time.LocalDateTime;

public class InactiveParkingSessionDto {
    public String licensePlate;
    public Long totalTime;
    public LocalDateTime checkInTime;
    public LocalDateTime checkOutTime;

    public InactiveParkingSessionDto(String licensePlate, Long totalTime, LocalDateTime checkInTime, LocalDateTime checkOutTime) {
        this.licensePlate = licensePlate;
        this.totalTime = totalTime;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
    }
}
