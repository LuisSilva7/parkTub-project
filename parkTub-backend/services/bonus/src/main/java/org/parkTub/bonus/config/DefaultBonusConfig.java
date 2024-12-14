package org.parkTub.bonus.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.parkTub.bonus.bonus.Bonus;
import org.parkTub.bonus.bonus.BonusService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultBonusConfig implements CommandLineRunner {

    private final BonusService bonusService;

    @Override
    @Transactional
    public void run(String... args) {
        if(bonusService.isBonusRepositoryEmpty()) {
            // create 1st bonus
            bonusService.createDefaultBonus(
                    Bonus.builder()
                            .description("Obtém um desconto no pagamento!")
                            .discountPercentage(10.0)
                            .pointsRequired(10)
                            .build()
            );

            // create 2nd bonus
            bonusService.createDefaultBonus(
                    Bonus.builder()
                            .description("Obtém um desconto no pagamento!")
                            .discountPercentage(20.0)
                            .pointsRequired(20)
                            .build()
            );

            // create 3rd bonus
            bonusService.createDefaultBonus(
                    Bonus.builder()
                            .description("Obtém um desconto no pagamento!")
                            .discountPercentage(30.0)
                            .pointsRequired(30)
                            .build()
            );

            // create 4th bonus
            bonusService.createDefaultBonus(
                    Bonus.builder()
                            .description("Obtém um desconto no pagamento!")
                            .discountPercentage(40.0)
                            .pointsRequired(40)
                            .build()
            );

            System.out.println("Default bonus were created!");
        } else {
            System.out.println("Default bonus already exist!");
        }
    }
}

