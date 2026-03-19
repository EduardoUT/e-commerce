package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record FiscalDataCreationRequest(
        @NotNull(message = "RFC is mandatory")
        @Pattern(regexp = "^[A-Z&Ñ]{3,4}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])[A-Z0-9]{2}[0-9A]$",
                message = "Invalid RFC")
        String rfc,
        @NotNull(message = "Regime tax is mandatory")
        @Pattern(regexp = "^\\d{3}$", message = "Regime tax must have 3 digits")
        String regimeTax,
        @NotNull(message = "Fiscal address is mandatory")
        @Size(min = 5, max = 60, message = "Fiscal address out of range")
        String fiscalAddress
        ) {
}
