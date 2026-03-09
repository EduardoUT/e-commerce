package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.math.BigDecimal;
import java.util.Set;

public record PurchaseCreationRequest(
        @NotEmpty(message = "Add at least one purchase item")
        Set<PurchaseItemCreationRequest> purchaseItems,
        @NotBlank(message = "Sub total is mandatory")
        BigDecimal subTotal,
        @NotBlank(message = "Total is mandatory")
        BigDecimal total
) {
}
