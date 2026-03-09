package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.util.PaymentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record PaymentCreationRequest(
        @NotNull(message = "Payment status is mandatory")
        PaymentStatus paymentStatus,
        String externalId,
        String gatewayName,
        @NotBlank(message = "Amount is mandatory")
        BigDecimal amount
) {
}
