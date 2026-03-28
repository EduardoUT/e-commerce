package io.github.eduardout.e_commerce.entity;

public record PurchaseItemId(Long purchase, Long product) implements Keyable {
}