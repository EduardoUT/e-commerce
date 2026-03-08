package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Purchase;
import io.github.eduardout.e_commerce.entity.PurchaseItem;

import java.math.BigDecimal;

public class Purchases {
    public static Purchase.PurchaseBuilder aPurchase() {
        return Purchase.aPurchase()
                .withPurchaseItemElement(PurchaseItem.aPurchaseItem().build())
                .withSubTotal(new BigDecimal("120.00"))
                .withTotal(new BigDecimal("300.00"));
    }
}
