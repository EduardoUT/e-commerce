package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.ShoppingCart;
import io.github.eduardout.e_commerce.entity.data.builder.ShoppingCarts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

class ShoppingCartCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    Set<CartItemCreationRequest> cartItemCreationRequests = new HashSet<>();

    @BeforeEach
    void setUp() {
        ShoppingCart shoppingCartTest = ShoppingCarts.aShoppingCart().build();

        shoppingCartTest
                .getCartItems()
                .forEach(cartItem ->
                        cartItemCreationRequests.add(
                                new CartItemCreationRequest(
                                        1L,
                                        cartItem.getQuantity()
                                )
                        ));
    }

    @Test
    void testNotNullCartItems() {
        constraintViolationAssertion.assertConstraintViolation(
                "Add at least one cart item",
                "cartItems",
                new ShoppingCartCreationRequest(null)
        );
    }

    @Test
    void testNotEmptyCartItems() {
        cartItemCreationRequests.clear();

        constraintViolationAssertion.assertConstraintViolation(
                "Add at least one cart item",
                "cartItems",
                new ShoppingCartCreationRequest(cartItemCreationRequests)
        );
    }
}