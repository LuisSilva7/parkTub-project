package org.acsi.config;

import com.stripe.Stripe;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.User;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class OnStartConfig {

    @ConfigProperty(name = "stripe.api.key") String apiKey;

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

        if (ParkingLot.findByAddress("Rua do Raio") == null) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.address = "Rua do Raio";
            parkingLot.latitude = -17.892728;
            parkingLot.longitude = -98.180482;
            parkingLot.totalSpots = 15;
            parkingLot.availableSpots = 13;

            parkingLot.persist();
        }

        Stripe.apiKey = apiKey;
    }
}