package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "cart_item")
@IdClass(CartItemId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"shoppingCart"})
@Builder(builderMethodName = "aCartItem", setterPrefix = "with")
public class CartItem {
    @Getter
    @Setter
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_cart_id", nullable = false)
    private ShoppingCart shoppingCart;
    @Getter
    @Setter
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
    @Getter
    @Setter
    @Column(nullable = false)
    private Integer quantity;
    @Getter
    @Setter
    @Column(name = "line_amount", nullable = false)
    private BigDecimal lineAmount;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CartItem other)) {
            return false;
        }
        return shoppingCart != null && product != null
                && Objects.equals(shoppingCart.getId(), other.getShoppingCart().getId())
                && Objects.equals(product.getId(), other.getProduct().getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
