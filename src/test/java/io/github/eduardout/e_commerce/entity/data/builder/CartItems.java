package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.CartItem;

import java.math.BigDecimal;

public class CartItems {
    public static CartItem.CartItemBuilder aCartItem() {
        return CartItem.aCartItem()
                .withProduct(Products.aProduct().build())
                .withQuantity(4)
                .withLineAmount(new BigDecimal("500.00"));
    }
}
