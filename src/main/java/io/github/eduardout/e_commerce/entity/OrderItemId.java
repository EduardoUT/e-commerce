package io.github.eduardout.e_commerce.entity;

public record OrderItemId(Long orders, Long product) implements Keyable {
}
