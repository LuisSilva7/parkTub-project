package org.parkTub.customer.customer;

import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {
    public CustomerResponse toCustomerResponse(Customer customer) {
        if(customer == null) {
            return null;
        }

        return new CustomerResponse(
                customer.getId(),
                customer.getName(),
                customer.getEmail(),
                customer.getPoints()
        );
    }
}
