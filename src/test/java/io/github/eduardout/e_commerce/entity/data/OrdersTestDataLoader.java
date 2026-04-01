package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.*;
import io.github.eduardout.e_commerce.repository.OrdersRepository;
import io.github.eduardout.e_commerce.util.OrderStatus;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.*;

import static io.github.eduardout.e_commerce.entity.data.PaymentTestDataLoader.getRandomPayment;

@Slf4j
public class OrdersTestDataLoader extends TestDataLoader<Orders> {

    private final Customer customer;
    private final Seller seller;
    private final List<Payment> payments;
    private final List<Product> products;

    public OrdersTestDataLoader(OrdersRepository ordersRepository, Customer customer, Seller seller,
                                List<Payment> payments, List<Product> products) {
        super(ordersRepository);
        this.customer = validateEntity(customer);
        this.seller = validateEntity(seller);
        validateEntities(payments);
        validateEntities(products);
        this.payments = payments;
        this.products = products;
    }

    protected void setDefaultTestEntities() {
        try {
            Integer quantity = 5;
            List<Orders> orders = products.stream()
                    .map(product -> {
                        BigDecimal calculation = product.getSellPrice().multiply(new BigDecimal(quantity.toString()));
                        Payment randomPayment = getRandomPayment(payments);
                        return Orders.anOrder()
                                .withPayment(randomPayment)
                                .withCustomer(customer)
                                .withSeller(seller)
                                .withOrderStatus(OrderStatus.CONFIRMED)
                                .withOrderItemElement(OrderItem.anOrderItem()
                                        .withProduct(product)
                                        .withQuantity(quantity)
                                        .withLineAmount(calculation)
                                        .build())
                                .withSubTotal(calculation)
                                .withTotal(calculation)
                                .build();
                    })
                    .toList();
            addEntities(orders);
        } catch (Exception e) {
            log.error("Error while loading test entities: {}", (Object) e.getStackTrace());
            throw new RuntimeException(e);
        }
    }

    public List<OrderItem> getOrderItems(List<Orders> orders) {
        validateEntities(orders);
        return orders
                .stream()
                .flatMap(order -> order.getOrderItems().stream())
                .toList();
    }
}
