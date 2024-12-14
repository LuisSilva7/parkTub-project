package org.parkTub.bonus.bonus;

import lombok.RequiredArgsConstructor;
import org.parkTub.bonus.customer.ActivateBonusRequest;
import org.parkTub.bonus.customer.CustomerClient;
import org.parkTub.bonus.customer.CustomerResponse;
import org.parkTub.bonus.exception.custom.BonusNotFoundException;
import org.parkTub.bonus.exception.custom.CustomerNotFoundException;
import org.parkTub.bonus.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BonusService {

    private final BonusRepository bonusRepository;
    private final BonusMapper bonusMapper;
    private final CustomerClient customerClient;

    public boolean isBonusRepositoryEmpty() {
        return bonusRepository.count() == 0;
    }

    public void createDefaultBonus(Bonus bonus) {
        bonusRepository.save(bonus);
    }

    public List<BonusResponse> findAll() {
        List<Bonus> bonus = bonusRepository.findAll();

        if (bonus.isEmpty()) {
            throw new BonusNotFoundException("No bonus were found!");
        }

        return bonus.stream()
                .map(bonusMapper::toBonusResponse)
                .toList();
    }

    public void activateBonus(Long id, Long customerId) {
        Bonus bonus = bonusRepository.findById(id).orElseThrow(() -> new BonusNotFoundException(
                "Bonus with id: " + id + " was not found!"));


        ResponseEntity<ApiResponse<CustomerResponse>> response = customerClient
                .findCustomerById(customerId);

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Optional<CustomerResponse> customerResponse = Optional.ofNullable(response.getBody().getData());

            if (customerResponse.isEmpty()) {
                throw new CustomerNotFoundException(
                        "No customer was found with ID: " + customerId);
            }

            bonus.getCustomerIds().add(customerId);
            bonusRepository.save(bonus);

            customerClient.activateBonus(customerId, new ActivateBonusRequest(bonus.getPointsRequired()));
        }
    }

    public void removeCustomerId(Long customerId) {
        List<Bonus> bonus = bonusRepository.findAll();

        if (bonus.isEmpty()) {
            throw new BonusNotFoundException("No bonus were found!");
        }

        for(Bonus bo : bonus) {
            if(bo.getCustomerIds().contains(customerId)) {
                bo.getCustomerIds().remove(customerId);
                bonusRepository.save(bo);
            }
        }
    }

    public CustomerBonusResponse findCustomerActiveBonus(Long customerId) {
        List<Bonus> bonus = bonusRepository.findAll();

        if (bonus.isEmpty()) {
            throw new BonusNotFoundException("No bonus were found!");
        }

        for(Bonus bo : bonus) {
            if(bo.getCustomerIds().contains(customerId)) {
                return new CustomerBonusResponse(bo.getId(), customerId, bo.getDiscountPercentage());
            }
        }

        return new CustomerBonusResponse((long) -1, customerId, 0.0);
    }
}
