package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.constraint.annotation.CheckAge;
import io.github.eduardout.e_commerce.dto.constraint.annotation.CheckOnlyLetters;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PersonalDataCreationRequest(
        @NotNull(message = "First name is mandatory")
        @CheckOnlyLetters
        @Size(min = 2, max = 30, message = "First name is out of range")
        String firstName,
        @NotNull(message = "Maternal surname is mandatory")
        @CheckOnlyLetters
        @Size(min = 2, max = 30, message = "Maternal surname is out of range")
        String maternalSurname,
        @NotNull(message = "Paternal surname is mandatory")
        @CheckOnlyLetters
        @Size(min = 2, max = 30, message = "Paternal surname is out of range")
        String paternalSurname,
        @NotNull(message = "Birth date is mandatory")
        @CheckAge
        LocalDate birthDate,
        @NotNull(message = "Phone number is mandatory")
        @Pattern(regexp = "^(?:\\+?\\d{1,3}(?:-?\\d{3,4})?)?-?\\d+(?:-?\\d{2}){4}$", message = """
                Phone number is invalid:
                - It should not start or end whit white spaces
                - Use only single hyphens as separators
                - White spaces aren't allowed
                - Plus symbol is optional
                - Dialing code (area code) is mandatory
                """)
        @Size(min = 10, max = 24, message = "Out of range, phone number must be between 10 and 24 digits including " +
                "area code")
        String phoneNumber
) {
}
