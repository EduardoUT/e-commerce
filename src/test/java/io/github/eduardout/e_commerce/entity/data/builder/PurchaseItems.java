package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.PurchaseItem;

import java.math.BigDecimal;

public class PurchaseItems {
    public static PurchaseItem.PurchaseItemBuilder aPurchaseItem() {
        return PurchaseItem.aPurchaseItem()
                .withProduct(Products.aProduct().build())
                .withQuantity(50)
                .withLineAmount(new BigDecimal("20.00"));
    }
}
