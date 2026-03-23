package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotNull;

public record CustomerCreationRequest(
        @NotNull(message = "User is mandatory")
        UserCreationRequest userCreationRequest,
        @NotNull(message = "Personal data is mandatory")
        PersonalDataCreationRequest personalDataCreationRequest) {
}
