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
        @NotNull(message = "Line amount is mandatory")
        @Digits(integer = 7, fraction = 2, message = """
                Maximum limit of integer digits must be less or equal to 7 and decimal are 2. 
                """)
        @DecimalMin(value = "0.00", message = "Line amount must not be negative")
        @DecimalMax(value = "99999999.99", message = "Line amount exceded maximum limit")
        BigDecimal lineAmount
) {
}
