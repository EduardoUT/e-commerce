package io.github.eduardout.e_commerce.dto.response;

public record CustomerResponse(Long customerId, UserResponse userResponse, PersonalDataResponse personalDataResponse, AddressResponse addressResponse) {
}
