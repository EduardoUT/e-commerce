package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.response.ProductResponse;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record PurchaseItemCreationRequest(
        @NotNull(message = "Must specify a product")
        ProductResponse productResponse,
        @Size(min = 1, message = "Product quantity out of range")
        Integer quantity,
        @NotBlank(message = "Line amount is mandatory")
        @Digits(integer = 7, fraction = 2, message = """
                Maximum limit of integer digits must be less or equal to 7 and decimal are 2. 
                """)
        @DecimalMin(value = "0.00", message = "Line amount must not be negative")
        @DecimalMax(value = "99999999.99", message = "Line amount exceded maximum limit")
        BigDecimal lineAmount
) {
}
