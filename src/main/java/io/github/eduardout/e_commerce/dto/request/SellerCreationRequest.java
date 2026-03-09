package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record SellerCreationRequest(
        @NotNull(message = "User is mandatory")
        UserCreationRequest userCreationRequest,
        @NotNull(message = "Personal data is mandatory")
        PersonalDataCreationRequest personalDataCreationRequest,
        @NotNull(message = "Address is mandatory")
        AddressCreationRequest addressCreationRequest,
        @NotNull(message = "Fiscal data is mandatory")
        FiscalDataCreationRequest fiscalDataCreationRequest
) {
}
