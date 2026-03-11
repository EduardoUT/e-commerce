package io.github.eduardout.e_commerce.dto.response;

public record AddressResponse(Long id,
                              String street,
                              String neighborhood,
                              String municipality,
                              String state,
                              String zipCode,
                              Integer externalNumber,
                              Integer internalNumber) {
}
