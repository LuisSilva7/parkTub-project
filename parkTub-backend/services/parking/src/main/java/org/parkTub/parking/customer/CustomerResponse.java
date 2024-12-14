package org.parkTub.parking.customer;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        int points
) {
}
