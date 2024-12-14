package org.parkTub.customer.customer;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.parkTub.customer.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> findCustomerById(
            @PathVariable("customer-id") Long customerId
    ) {
        CustomerResponse customerResponse = customerService.findById(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Customer obtained successfully!", customerResponse));
    }

    @PutMapping("{customer-id}")
    public ResponseEntity<ApiResponse<Object>> activateBonus(
            @PathVariable("customer-id") Long customerId,
            @RequestBody @Valid ActivateBonusRequest request
    ) {
        customerService.updatePoints(customerId, request);

        return ResponseEntity.ok(new ApiResponse<>(
                "Customer points updated successfully!", null));
    }

    @PostMapping("/{customer-id}")
    void addPoints(@PathVariable("customer-id") Long customerId) {
        customerService.addPoints(customerId);
    }

}
