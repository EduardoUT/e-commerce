package io.github.eduardout.e_commerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductResponse(
        Long productId,
        String name,
        String description,
        BigDecimal sellPrice,
        BigDecimal purchasePrice,
        Integer stock,
        ProductCategoryResponse productCategoryResponse,
        LocalDateTime updatedAt
) {
}
