package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

public record AddressCreationRequest(
        @NotBlank(message = "Street is mandatory")
        @Size(max = 30, message = "Out of range, max characters for street are 30")
        String street,
        @NotBlank(message = "Neighborhood is mandatory")
        @Size(max = 30, message = "Out of range, max characters for neighborhood are 30")
        String neighborhood,
        @NotBlank(message = "Municipality is mandatory")
        @Size(max = 30, message = "Out of range, max characters for municipality are 30")
        String municipality,
        @NotBlank(message = "State is mandatory")
        @Size(max = 30, message = "Out of range, max characters for state are 30")
        String state,
        @NotBlank(message = "Zip code is mandatory")
        @Size(max = 15, message = "Out of range, max characters for zipCode are 15")
        String zipCode,
        @NotNull(message = "External number should not be null")
        @Min(value = 1, message = "External number must not be negative")
        @Max(value = Integer.MAX_VALUE, message = "External number out of maximum range")
        Integer externalNumber,
        @PositiveOrZero(message = "Internal number should not be negative, if omitted type null")
        Integer internalNumber
) {
    public AddressCreationRequest {
        if (internalNumber == null) internalNumber = 0;
    }
}
