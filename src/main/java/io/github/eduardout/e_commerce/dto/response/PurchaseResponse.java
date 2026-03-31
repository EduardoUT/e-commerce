package io.github.eduardout.e_commerce.dto.response;

import io.github.eduardout.e_commerce.entity.PurchaseItem;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record PurchaseResponse(Long id,
                               CustomerWithUserResponse customerWithUserResponse,
                               List<PurchaseItem> purchaseItems,
                               BigDecimal discountAmount,
                               BigDecimal total,
                               LocalDateTime createdAt) {
}
