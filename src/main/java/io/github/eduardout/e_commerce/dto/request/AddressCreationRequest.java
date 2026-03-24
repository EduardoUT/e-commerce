package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

public record AddressCreationRequest(
        @NotBlank(message = "Street is mandatory")
        @Size(max = 70, message = "Out of range, max characters for street are 70")
        String street,
        @NotBlank(message = "Neighborhood is mandatory")
        @Size(max = 70, message = "Out of range, max characters for neighborhood are 70")
        String neighborhood,
        @NotBlank(message = "Municipality is mandatory")
        @Size(max = 50, message = "Out of range, max characters for municipality are 50")
        String municipality,
        @NotBlank(message = "State is mandatory")
        @Size(max = 50, message = "Out of range, max characters for state are 50")
        String state,
        @NotNull(message = "Zip code is mandatory")
        @Pattern(regexp = "^\\d{5}$", message = "The zip code must be of 5 numeric digits, and must not contain spaces")
        String zipCode,
        @NotNull(message = "External number should not be null")
        @Positive(message = "External number must not be negative")
        Integer externalNumber,
        @NotNull(message = "Internal number should not be null, if omitted write 0")
        @PositiveOrZero(message = "Internal number should not be negative, if omitted write 0")
        Integer internalNumber
) {
}
