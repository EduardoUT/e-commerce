package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.util.PaymentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PaymentResponse(Long id,
                              CustomerWithUserResponse customerWithUserResponse,
                              PaymentStatus paymentStatus,
                              String externalId,
                              String gatewayName,
                              BigDecimal amount,
                              LocalDateTime createdAt) {
}
