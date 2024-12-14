package org.parkTub.parking.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.parkTub.parking.parkingLot.ParkingLot;
import org.parkTub.parking.parkingLot.ParkingLotService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultParkingLotsConfig implements CommandLineRunner {

    private final ParkingLotService parkingLotService;

    @Override
    @Transactional
    public void run(String... args) {
        if(parkingLotService.isParkingLotRepositoryEmpty()) {
            // create 1st parkingLot
            parkingLotService.createDefaultParkingLot(
                    ParkingLot.builder()
                            .name("Bombeiros Voluntários - Museu")
                            .address("R. dos Bombeiros Voluntários")
                            .latitude(41.545453)
                            .longitude(-8.428053)
                            .totalSpots(5)
                            .availableSpots(5)
                            .build()
            );

            // create 2nd parkingLot
            parkingLotService.createDefaultParkingLot(
                    ParkingLot.builder()
                            .name("Bombeiros Voluntários - Parque Radical")
                            .address("R. dos Bombeiros Voluntários")
                            .latitude(41.54534)
                            .longitude(-8.428454)
                            .totalSpots(5)
                            .availableSpots(5)
                            .build()
            );

            // create 3rd parkingLot
            parkingLotService.createDefaultParkingLot(
                    ParkingLot.builder()
                            .name("Bombeiros Voluntários - Colinatrum")
                            .address("R. dos Bombeiros Voluntários")
                            .latitude(41.546371)
                            .longitude(-8.427934)
                            .totalSpots(5)
                            .availableSpots(5)
                            .build()
            );

            System.out.println("Default parking lots were created!");
        } else {
            System.out.println("Default parking lots already exist!");
        }
    }
}
