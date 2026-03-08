package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.OrderItem;

import java.math.BigDecimal;

public class OrderItems {
    public static OrderItem.OrderItemBuilder anOrderItem() {
        return OrderItem.anOrderItem()
                .withOrders(OrdersTestBuilder.anOrder().build())
                .withProduct(Products.aProduct().build())
                .withQuantity(20)
                .withLineAmount(new BigDecimal("20.00"));
    }
}
