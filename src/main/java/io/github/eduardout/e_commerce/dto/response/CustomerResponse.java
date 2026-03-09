package io.github.eduardout.e_commerce.dto.response;

public record CustomerResponse(Long customerId, UserResponse userCreationRequest, PersonalDataResponse personalDataResponse, AddressResponse addressResponse) {
}
