package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.response.ProductResponse;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record OrderItemCreationRequest(
        @NotNull(message = "Product is mandatory")
        ProductResponse productResponse,
        @Min(value = 1, message = "Negative value not valid")
        @Max(Integer.MAX_VALUE)
        @Size(min = 1, message = "Quantity out of range")
        Integer quantity,
        @NotBlank(message = "Line amount is mandatory")
        BigDecimal lineAmount
) {
}
