package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.util.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static io.github.eduardout.e_commerce.util.Calculation.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"customer", "orderItems"})
public class Orders implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true)
    private Long id;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id", nullable = false, updatable = false)
    private Payment payment;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;
    @Getter
    @OneToMany(mappedBy = "orders", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<OrderItem> orderItems = new HashSet<>();
    @Getter
    @Setter
    @Column(name = "discount_amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal discountAmount = DEFAULT_AMOUNT;
    @Getter
    @Setter
    @Column(name = "subtotal", nullable = false, precision = 9, scale = 2)
    private BigDecimal subTotal;
    @Getter
    @Setter
    @Column(name = "total", nullable = false, precision = 9, scale = 2)
    private BigDecimal total;
    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrders(this);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderItems.remove(orderItem);
        orderItem.setOrders(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Orders other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static OrdersBuilder anOrder() {
        return new OrdersBuilder();
    }

    public static class OrdersBuilder {
        private Seller seller;
        private Customer customer;
        private Payment payment;
        private OrderStatus orderStatus;
        private final Set<OrderItem> orderItems = new HashSet<>();
        private BigDecimal discount = DEFAULT_AMOUNT;
        private BigDecimal subTotal;
        private BigDecimal total;

        private OrdersBuilder() {
        }

        public OrdersBuilder withSeller(Seller seller) {
            this.seller = seller;
            return this;
        }

        public OrdersBuilder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public OrdersBuilder withPayment(Payment payment) {
            this.payment = payment;
            return this;
        }

        public OrdersBuilder withOrderStatus(OrderStatus orderStatus) {
            this.orderStatus = orderStatus;
            return this;
        }

        public OrdersBuilder withOrderItemElement(OrderItem orderItem) {
            this.orderItems.add(orderItem);
            return this;
        }

        public OrdersBuilder withDiscount(BigDecimal discount) {
            this.discount = discount;
            return this;
        }

        public OrdersBuilder withSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public OrdersBuilder withTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Orders build() {
            Orders orders = new Orders();
            orders.setSeller(seller);
            orders.setCustomer(customer);
            orders.setPayment(payment);
            orders.setOrderStatus(orderStatus);
            orders.setDiscountAmount(discount);
            orders.setSubTotal(subTotal);
            orders.setTotal(total);

            this.orderItems.forEach(orders::addOrderItem);
            return orders;
        }
    }
}
