package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerMapper implements Function<Customer, CustomerResponse> {

    private final UserResponseMapper userResponseMapper;
    private final PersonalDataResponseMapper personalDataResponseMapper;
    private final AddressResponseMapper addressResponseMapper;

    public CustomerMapper(UserResponseMapper userResponseMapper, PersonalDataResponseMapper personalDataResponseMapper, AddressResponseMapper addressResponseMapper) {
        this.userResponseMapper = userResponseMapper;
        this.personalDataResponseMapper = personalDataResponseMapper;
        this.addressResponseMapper = addressResponseMapper;
    }

    @Override
    public CustomerResponse apply(Customer customer) {
        return new CustomerResponse(
                customer.getId(),
                userResponseMapper.apply(customer.getUser()),
                personalDataResponseMapper.apply(customer.getPersonalData()),
                addressResponseMapper.apply(customer.getAddress())
        );
    }
}
