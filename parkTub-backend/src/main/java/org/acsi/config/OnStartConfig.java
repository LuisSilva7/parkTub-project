package org.acsi.config;

import com.stripe.Stripe;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.transaction.Transactional;
import org.acsi.entity.Bonus;
import org.acsi.entity.ParkingLot;
import org.acsi.entity.User;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
public class OnStartConfig {

    @ConfigProperty(name = "stripe.api.key") String apiKey;

    @Transactional
    void onStart(@Observes StartupEvent ev) {
        // create user
        if (User.findByName("Luís") == null) {
            User defaultUser = new User();
            defaultUser.name = "Luís";
            defaultUser.email = "luis17@gmail.com";
            defaultUser.points = 60;

            defaultUser.persist();
        }

        // create 1st bonus
        if(Bonus.findByPointsRequired(10) == null) {
            Bonus bonus = new Bonus();
            bonus.description = "Obtém um desconto no pagamento!";
            bonus.pointsRequired = 10;
            bonus.discountPercentage = 10.0;

            bonus.persist();
        }

        // create 2nd bonus
        if(Bonus.findByPointsRequired(20) == null) {
            Bonus bonus = new Bonus();
            bonus.description = "Obtém um desconto no pagamento!";
            bonus.pointsRequired = 20;
            bonus.discountPercentage = 20.0;
            bonus.users.add(User.findByName("Luís"));

            bonus.persist();
        }

        // create 1st parkingLot
        if (ParkingLot.findByName("Bombeiros Voluntários - Museu") == null) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.name = "Bombeiros Voluntários - Museu";
            parkingLot.address = "R. dos Bombeiros Voluntários";
            parkingLot.latitude = 41.545453;
            parkingLot.longitude = -8.428053;
            parkingLot.totalSpots = 20;
            parkingLot.availableSpots = 14;

            parkingLot.persist();
        }

        // create 2nd parkingLot
        if (ParkingLot.findByName("Bombeiros Voluntários - Parque Radical") == null) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.name = "Bombeiros Voluntários - Parque Radical";
            parkingLot.address = "R. dos Bombeiros Voluntários";
            parkingLot.latitude = 41.54534;
            parkingLot.longitude = -8.428454;
            parkingLot.totalSpots = 15;
            parkingLot.availableSpots = 4;

            parkingLot.persist();
        }

        // create 3rd parkingLot
        if (ParkingLot.findByName("Bombeiros Voluntários - Colinatrum") == null) {
            ParkingLot parkingLot = new ParkingLot();
            parkingLot.name = "Bombeiros Voluntários - Colinatrum";
            parkingLot.address = "R. dos Bombeiros Voluntários";
            parkingLot.latitude = 41.546371;
            parkingLot.longitude = -8.427934;
            parkingLot.totalSpots = 10;
            parkingLot.availableSpots = 3;

            parkingLot.persist();
        }

        // import stripeApiKey
        Stripe.apiKey = apiKey;
    }
}