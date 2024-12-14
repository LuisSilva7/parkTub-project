package org.parkTub.bonus.bonus;

public record CustomerBonusResponse(
        Long bonusId,
        Long customerId,
        Double discountPercentage
) {
}
