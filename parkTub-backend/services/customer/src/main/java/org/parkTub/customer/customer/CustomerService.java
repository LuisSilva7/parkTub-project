package org.parkTub.customer.customer;

import lombok.RequiredArgsConstructor;
import org.parkTub.customer.exception.custom.CustomerNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public boolean isCustomerRepositoryEmpty() {
        return customerRepository.count() == 0;
    }

    public void createDefaultCustomer(Customer customer) {
        customerRepository.save(customer);
    }

    public CustomerResponse findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with the ID: " + id));
    }

    public void updatePoints(Long customerId, ActivateBonusRequest request) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with the ID: " + customerId));

        customer.setPoints(customer.getPoints() - request.pointsRequired());
        customerRepository.save(customer);
    }

    public void addPoints(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("No customer found with the ID: " + customerId));

        customer.setPoints(customer.getPoints() + 10);
        customerRepository.save(customer);
    }
}
