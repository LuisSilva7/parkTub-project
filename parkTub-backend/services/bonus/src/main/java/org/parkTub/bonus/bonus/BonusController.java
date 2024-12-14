package org.parkTub.bonus.bonus;

import lombok.RequiredArgsConstructor;
import org.parkTub.bonus.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bonus")
@RequiredArgsConstructor
public class BonusController {

    private final BonusService bonusService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<BonusResponse>>> findAllBonus() {
        List<BonusResponse> bonusResponses = bonusService.findAll();

        return ResponseEntity.ok(new ApiResponse<>(
                "Bonus obtained successfully!", bonusResponses));
    }

    @PostMapping("{bonus-id}/customer/{customer-id}")
    public ResponseEntity<ApiResponse<Object>> activateBonus(
            @PathVariable("bonus-id") Long bonusId,
            @PathVariable("customer-id") Long customerId) {
        bonusService.activateBonus(bonusId, customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Bonus activated successfully!", null));
    }

    @PostMapping("/{customer-id}")
    void removeCustomerId(@PathVariable("customer-id") Long customerId) {
        bonusService.removeCustomerId(customerId);
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<ApiResponse<CustomerBonusResponse>> findCustomerActiveBonus(
            @PathVariable("customer-id") Long customerId) {
        CustomerBonusResponse customerActiveBonus = bonusService.findCustomerActiveBonus(customerId);

        return ResponseEntity.ok(new ApiResponse<>(
                "Search completed successfully!", customerActiveBonus));
    }
}
