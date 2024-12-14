package org.parkTub.payment.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {

    @PostMapping("/{customer-id}")
    void addPoints(@PathVariable("customer-id") Long customerId);
}
