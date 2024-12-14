package org.parkTub.customer.customer;

public record CustomerResponse(
        Long id,
        String name,
        String email,
        int points
) {
}
