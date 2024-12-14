package org.parkTub.customer.config;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.parkTub.customer.customer.Customer;
import org.parkTub.customer.customer.CustomerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class DefaultCustomerConfig implements CommandLineRunner {

    private final CustomerService customerService;

    @Override
    @Transactional
    public void run(String... args) {
        if(customerService.isCustomerRepositoryEmpty()) {
            customerService.createDefaultCustomer(
                    Customer.builder()
                            .name("Luís")
                            .email("luis@gmail.com")
                            .points(160)
                            .build()
            );

            System.out.println("Default customer was created!");
        } else {
            System.out.println("Default customer already exist!");
        }
    }
}
