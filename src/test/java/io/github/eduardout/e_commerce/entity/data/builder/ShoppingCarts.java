package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.ShoppingCart;

import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;

public class ShoppingCarts {
    public static ShoppingCart.ShoppingCartBuilder aShoppingCart() {
        return ShoppingCart.aShoppingCart()
                .withCartItemElement(CartItems.aCartItem().build())
                .withSubTotal(DEFAULT_AMOUNT)
                .withTotal(DEFAULT_AMOUNT);
    }
}
