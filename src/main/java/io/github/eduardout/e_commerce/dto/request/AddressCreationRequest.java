package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record AddressCreationRequest(
        @NotBlank(message = "Street is mandatory")
        String street,
        @NotBlank(message = "Neighborhood is mandatory")
        String neighborhood,
        @NotBlank(message = "Municipality is mandatory")
        String municipality,
        @NotBlank(message = "State is mandatory")
        String state,
        @NotBlank(message = "Zip code is mandatory")
        String zipCode,
        @NotBlank(message = "External number is mandatory")
        @Min(1)
        @Max(Integer.MAX_VALUE)
        Integer externalNumber,
        @Min(1)
        @Max(Integer.MAX_VALUE)
        Integer internalNumber
) {
}
