package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.Customer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class CustomerWithUserMapper implements Function<Customer, CustomerWithUserResponse> {

    private final UserResponseMapper userResponseMapper;

    public CustomerWithUserMapper(UserResponseMapper userResponseMapper, PersonalDataResponseMapper personalDataResponseMapper, AddressResponseMapper addressResponseMapper) {
        this.userResponseMapper = userResponseMapper;
    }

    @Override
    public CustomerWithUserResponse apply(Customer customer) {
        return new CustomerWithUserResponse(
                customer.getId(),
                userResponseMapper.apply(customer.getUser())
        );
    }
}
