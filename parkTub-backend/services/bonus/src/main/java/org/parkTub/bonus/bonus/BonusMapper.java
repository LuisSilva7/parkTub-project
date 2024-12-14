package org.parkTub.bonus.bonus;

import org.springframework.stereotype.Service;

@Service
public class BonusMapper {

    public BonusResponse toBonusResponse(Bonus bonus) {
        if(bonus == null) {
            return null;
        }

        return new BonusResponse(
                bonus.getId(),
                bonus.getDescription(),
                bonus.getDiscountPercentage(),
                bonus.getPointsRequired()
        );
    }
}
