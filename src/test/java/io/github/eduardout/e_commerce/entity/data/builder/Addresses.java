package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Address;

public class Addresses {
    public static Address.AddressBuilder anAddress() {
        return Address.anAddress()
                .withStreet("Some street")
                .withNeighborhood("Some neighborhood")
                .withMunicipality("Some municipality")
                .withState("Some state")
                .withZipCode("54213")
                .withExternalNumber(50)
                .withInternalNumber(2);
    }
}
