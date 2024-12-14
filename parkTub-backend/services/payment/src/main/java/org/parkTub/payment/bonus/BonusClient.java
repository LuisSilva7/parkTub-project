package org.parkTub.payment.bonus;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(
        name = "bonus-service",
        url = "${application.config.bonus-url}"
)
public interface BonusClient {

    @PostMapping("/{customer-id}")
    void removeCustomerId(@PathVariable("customer-id") Long customerId);
}
