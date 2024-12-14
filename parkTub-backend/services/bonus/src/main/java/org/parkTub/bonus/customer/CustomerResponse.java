package org.parkTub.bonus.customer;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        int points
) {
}

