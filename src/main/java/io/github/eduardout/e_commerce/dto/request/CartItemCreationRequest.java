package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

public record CartItemCreationRequest(
        @NotNull(message = "Product identifier is mandatory")
        @Positive(message = "Must specify a positive identifier for product")
        Long productId,
        @NotNull(message = "Quantity is mandatory")
        @Min(value = 1, message = "Specify at least 1 quantity of the desired product")
        Integer quantity
) {
}
