package io.github.eduardout.e_commerce.service;

import io.github.eduardout.e_commerce.dto.response.CustomerWithUserMapper;
import io.github.eduardout.e_commerce.dto.response.CustomerWithUserResponse;
import io.github.eduardout.e_commerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerWithUserMapper customerWithUserMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerWithUserMapper customerWithUserMapper) {
        this.customerRepository = customerRepository;
        this.customerWithUserMapper = customerWithUserMapper;
    }

    public CustomerWithUserResponse findByIdWithUser(Long customerId) {
        return customerRepository.findByIdWithUser(customerId)
                .map(customerWithUserMapper)
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
    }
}
