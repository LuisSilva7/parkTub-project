package org.acsi.config;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.User;

@ApplicationScoped
public class OnStartConfig {

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        if (User.findByName("Luís") == null) {
            User defaultUser = new User();
            defaultUser.name = "Luís";
            defaultUser.email = "luis17@gmail.com";

            defaultUser.persist();
        }

        if (ParkingLot.findByAddress("Avenida da Liberdade") == null) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.address = "Avenida da Liberdade";
            parkingLot.latitude = -23.561732;
            parkingLot.longitude = -46.656250;
            parkingLot.totalSpots = 10;
            parkingLot.availableSpots = 6;

            parkingLot.persist();
        }
    }
}