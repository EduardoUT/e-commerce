package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Address;

public class Addresses {
    public static Address.AddressBuilder anAddress() {
        return Address.anAddress()
                .withStreet("Circuito Interior 1")
                .withNeighborhood("Miguel Hidalgo")
                .withMunicipality("CDMX")
                .withState("CDMX")
                .withZipCode("54213")
                .withExternalNumber(50)
                .withInternalNumber(2);
    }
}
