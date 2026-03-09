package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Null;

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
        @Min(value = 1, message = "External number must not be negative")
        @Max(value = Integer.MAX_VALUE, message = "External number out of maximum range")
        Integer externalNumber,
        @Null
        @Min(value = 1, message = "Internal number must not be negative")
        @Max(value = Integer.MAX_VALUE, message = "Internal number out of maximum range")
        Integer internalNumber
) {
}
