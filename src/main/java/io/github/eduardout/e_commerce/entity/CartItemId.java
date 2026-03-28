package io.github.eduardout.e_commerce.entity;

public record CartItemId (Long shoppingCart, Long product) implements Keyable {
}
