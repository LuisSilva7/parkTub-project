package org.parkTub.customer.customer;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record ActivateBonusRequest(
        @NotNull(message = "Points required must not be null")
        @Min(value = 1, message = "Points must be greater than or equal to 1")
        int pointsRequired
) {
}
