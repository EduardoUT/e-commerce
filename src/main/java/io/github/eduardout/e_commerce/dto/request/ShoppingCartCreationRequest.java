package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.Set;

public record ShoppingCartCreationRequest(
        @NotEmpty(message = "Add at least one cart item")
        Set<ShoppingCartItemCreationRequest> shoppingCartItems,
        @NotNull(message = "Subtotal is mandatory")
        @Digits(integer = 7, fraction = 2, message = """
                Maximum limit of integer digits must be less or equal to 7 and decimal are 2. 
                """)
        @DecimalMin(value = "0.00", message = "Subtotal must not be negative")
        @DecimalMax(value = "99999999.99", message = "Subtotal exceded maximum limit")
        BigDecimal subTotal
) {
}
