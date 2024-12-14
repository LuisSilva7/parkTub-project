package org.parkTub.bonus.customer;

import org.parkTub.bonus.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {

    @GetMapping("/{customer-id}")
    ResponseEntity<ApiResponse<CustomerResponse>> findCustomerById(
            @PathVariable("customer-id") Long customerId);

    @PutMapping("/{customer-id}")
    ResponseEntity<ApiResponse<Object>> activateBonus(
            @PathVariable("customer-id") Long customerId,
            @RequestBody ActivateBonusRequest request);
}
