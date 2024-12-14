package org.parkTub.parking.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.parkTub.parking.parkingLot.ParkingLot;
import org.parkTub.parking.parkingLot.ParkingLotRepository;
import org.parkTub.parking.parkingSession.ParkingSession;
import org.parkTub.parking.parkingSession.ParkingSessionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class DefaultParkingSessionsConfig implements CommandLineRunner {

    private final ParkingSessionService parkingSessionService;
    private final ParkingLotRepository parkingLotRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if(parkingSessionService.isParkingSessionRepositoryEmpty()) {
            List<ParkingLot> parkingLots = parkingLotRepository.findAll();

            // create 1st parkingSession
            parkingSessionService.createDefaultParkingSession(
                    ParkingSession.builder()
                            .licensePlate("07KF22")
                            .latitude(41.545453)
                            .longitude(-8.428053)
                            .checkInTime(LocalDateTime.parse("2024-12-04T14:00:00"))
                            .checkOutTime(LocalDateTime.parse("2024-12-04T15:00:00"))
                            .totalTime(60L)
                            .isActive(false)
                            .parkingLot(parkingLots.get(0))
                            .customerId(1L)
                            .build()
            );

            // create 2nd parkingSession
            parkingSessionService.createDefaultParkingSession(
                    ParkingSession.builder()
                            .licensePlate("85AL92")
                            .latitude(41.54534)
                            .longitude(-8.428454)
                            .checkInTime(LocalDateTime.parse("2024-12-06T16:30:00"))
                            .checkOutTime(LocalDateTime.parse("2024-12-06T17:00:00"))
                            .totalTime(30L)
                            .isActive(false)
                            .parkingLot(parkingLots.get(1))
                            .customerId(1L)
                            .build()
            );

            // create 3rd parkingSession
            parkingSessionService.createDefaultParkingSession(
                    ParkingSession.builder()
                            .licensePlate("22HF35")
                            .latitude(41.546371)
                            .longitude(-8.427934)
                            .checkInTime(LocalDateTime.parse("2024-12-09T11:30:00"))
                            .checkOutTime(LocalDateTime.parse("2024-12-09T13:30:00"))
                            .totalTime(120L)
                            .isActive(false)
                            .parkingLot(parkingLots.get(2))
                            .customerId(1L)
                            .build()
            );

            // create 4th parkingSession
            parkingSessionService.createDefaultParkingSession(
                    ParkingSession.builder()
                            .licensePlate("83DX21")
                            .latitude(41.545453)
                            .longitude(-8.428053)
                            .checkInTime(LocalDateTime.parse("2024-12-10T17:00:00"))
                            .checkOutTime(LocalDateTime.parse("2024-12-10T17:20:00"))
                            .totalTime(20L)
                            .isActive(false)
                            .parkingLot(parkingLots.get(0))
                            .customerId(1L)
                            .build()
            );

            // create 5th parkingSession
            parkingSessionService.createDefaultParkingSession(
                    ParkingSession.builder()
                            .licensePlate("44ZT90")
                            .latitude(41.545453)
                            .longitude(-8.428053)
                            .checkInTime(LocalDateTime.parse("2024-12-12T14:00:00"))
                            .checkOutTime(LocalDateTime.parse("2024-12-12T15:00:00"))
                            .totalTime(60L)
                            .isActive(false)
                            .parkingLot(parkingLots.get(0))
                            .customerId(1L)
                            .build()
            );

            System.out.println("Default sessions were created!");
        } else {
            System.out.println("Default sessions already exist!");
        }
    }
}