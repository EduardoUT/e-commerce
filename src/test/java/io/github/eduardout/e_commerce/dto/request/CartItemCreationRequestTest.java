package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import org.junit.jupiter.api.Test;

class CartItemCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();

    @Test
    void testNotNullProductId() {
        constraintViolationAssertion.assertConstraintViolation(
                "Product identifier is mandatory",
                "productId",
                new CartItemCreationRequest(null, 1)
        );
    }

    @Test
    void testPositiveProductId() {
        constraintViolationAssertion.assertConstraintViolation(
                "Must specify a positive identifier for product",
                "productId",
                new CartItemCreationRequest(-1L, 1)
        );
    }

    @Test
    void testNotNullQuantity() {
        constraintViolationAssertion.assertConstraintViolation(
                "Quantity is mandatory",
                "quantity",
                new CartItemCreationRequest(1L, null)
        );
    }

    @Test
    void testPositiveQuantity() {
        constraintViolationAssertion.assertConstraintViolation(
                "Specify at least 1 quantity of the desired product",
                "quantity",
                new CartItemCreationRequest(1L, -1)
        );
    }
}