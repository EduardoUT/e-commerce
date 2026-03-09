package io.github.eduardout.e_commerce.service;

import io.github.eduardout.e_commerce.dto.response.CustomerResponse;
import io.github.eduardout.e_commerce.dto.response.CustomerMapper;
import io.github.eduardout.e_commerce.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerResponse findCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper)
                .orElseThrow(() -> new IllegalStateException("Customer not found"));
    }
}
