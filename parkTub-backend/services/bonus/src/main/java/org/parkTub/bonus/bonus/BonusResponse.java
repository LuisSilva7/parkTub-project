package org.parkTub.bonus.bonus;

public record BonusResponse(
        Long id,
        String description,
        Double discountPercentage,
        int pointsRequired
) {
}
