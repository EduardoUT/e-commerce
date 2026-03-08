package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.OrderItem;
import io.github.eduardout.e_commerce.entity.Orders;
import io.github.eduardout.e_commerce.util.OrderStatus;

import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;

public class OrdersTestBuilder {
    public static Orders.OrdersBuilder anOrder() {
        return Orders.anOrder()
                .withPayment(Payments.aPayment().build())
                .withOrderStatus(OrderStatus.PENDING)
                .withOrderItemElement(OrderItem.anOrderItem().build())
                .withSubTotal(DEFAULT_AMOUNT)
                .withTotal(DEFAULT_AMOUNT);
    }
}
