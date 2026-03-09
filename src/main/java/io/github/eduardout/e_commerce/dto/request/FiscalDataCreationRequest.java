package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.Pattern;

public record FiscalDataCreationRequest(
        @Pattern(regexp = "^[A-Z&Ñ]{3,4}\\d{2}(0[1-9]|1[012])(0[1-9]|[12]\\d|3[01])[A-Z0-9]{2}[0-9A]$",
                message = "Invalid RFC")
        String rfc,
        String regimeTax,
        String fiscalAddress
        ) {
}
