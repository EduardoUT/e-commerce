package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.util.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

public record OrderCreationRequest(
        @NotNull(message = "Orders status is mandatory")
        OrderStatus orderStatus,
        @NotEmpty(message = "Add at least one order item")
        Set<OrderItemCreationRequest> orderItems) {
}
