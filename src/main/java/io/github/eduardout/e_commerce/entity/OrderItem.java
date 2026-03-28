package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "order_item")
@IdClass(OrderItemId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"orders"})
@Builder(builderMethodName = "anOrderItem", setterPrefix = "with")
public class OrderItem implements CompositeIdentifiable {
    @Getter
    @Setter
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Orders orders;
    @Getter
    @Setter
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Getter
    @Setter
    @Column(nullable = false)
    private Integer quantity;
    @Getter
    @Setter
    @Column(name = "line_amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal lineAmount;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OrderItem other)) {
            return false;
        }
        return orders != null && product != null
                && Objects.equals(orders.getId(), other.getOrders().getId())
                && Objects.equals(product.getId(), other.getProduct().getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Keyable getId() {
        return new OrderItemId(orders.getId(), product.getId());
    }
}
