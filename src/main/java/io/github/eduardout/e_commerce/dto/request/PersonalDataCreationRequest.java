package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonalDataCreationRequest(
        @NotBlank
        @Size(min = 2, max = 50, message = "First name is out of range")
        String firstName,
        @NotBlank
        @Size(min = 2, max = 50, message = "Maternal surname is out of range")
        String maternalSurname,
        @NotBlank
        @Size(min = 2, max = 50, message = "Paternal surname is Out of range")
        String paternalSurname,
        @NotNull(message = "Birth date must not be null")
        LocalDate birthDate,
        @NotBlank
        @Size(min = 8, max = 15, message = "Out of range, phone number must be between 8 and 15 digits")
        String phoneNumber
) {
}
