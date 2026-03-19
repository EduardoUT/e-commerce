package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

import java.util.Set;

public record ShoppingCartCreationRequest(
        @NotEmpty(message = "Add at least one cart item")
        Set<CartItemCreationRequest> cartItems
) {
}
