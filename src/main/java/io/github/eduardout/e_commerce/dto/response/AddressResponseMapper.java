package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.Address;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AddressResponseMapper implements Function<Address, AddressResponse> {
    @Override
    public AddressResponse apply(Address address) {
        return new AddressResponse(
                address.getId(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getMunicipality(),
                address.getState(),
                address.getZipCode(),
                address.getExternalNumber(),
                address.getInternalNumber()
        );
    }
}
