package io.github.eduardout.e_commerce.dto.response;

public record CustomerResponse(Long id,
                               UserResponse userResponse,
                               PersonalDataResponse personalDataResponse,
                               AddressResponse addressResponse) {
}
