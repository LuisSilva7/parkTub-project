package org.parkTub.parking.customer;

import org.parkTub.parking.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "customer-service",
        url = "${application.config.customer-url}"
)
public interface CustomerClient {

    @GetMapping("/{customer-id}")
    ResponseEntity<ApiResponse<CustomerResponse>> findCustomerById(
            @PathVariable("customer-id") Long customerId);
}
