package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.util.PaymentStatus;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PaymentCreationRequest(
        @NotNull(message = "Payment status is mandatory")
        PaymentStatus paymentStatus,
        String externalId,
        String gatewayName,
        @NotNull(message = "Amount is mandatory")
        @Digits(integer = 7, fraction = 2, message = """
                Maximum limit of integer digits must be less or equal to 7 and decimal are 2. 
                """)
        @DecimalMin(value = "0.00", message = "Amount must not be negative")
        @DecimalMax(value = "99999999.99", message = "Amount exceded maximum limit")
        BigDecimal amount
) {
}
