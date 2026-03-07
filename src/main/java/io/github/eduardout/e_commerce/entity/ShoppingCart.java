package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;

@Entity(name = "shopping_cart")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"cartItems"})
public class ShoppingCart implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @OneToMany(mappedBy = "shoppingCart", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private final Set<CartItem> cartItems = new HashSet<>();
    @Getter
    @Setter
    private BigDecimal discountAmount = DEFAULT_AMOUNT;
    @Getter
    @Setter
    @Column(name = "subtotal", nullable = false)
    private BigDecimal subTotal;
    @Getter
    @Setter
    @Column(name = "total", nullable = false)
    private BigDecimal total;
    @Getter
    @Setter
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Getter
    @Setter
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public void addCartItem(CartItem cartItem) {
        cartItems.add(cartItem);
        cartItem.setShoppingCart(this);
    }

    public void removeCartItem(CartItem cartItem) {
        cartItems.remove(cartItem);
        cartItem.setShoppingCart(null);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof ShoppingCart other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public static ShoppingCartBuilder aShoppingCart() {
        return new ShoppingCartBuilder();
    }

    public static class ShoppingCartBuilder {
        private HashSet<CartItem> cartItems = new HashSet<>();
        private BigDecimal discountAmount = DEFAULT_AMOUNT;
        private BigDecimal subTotal;
        private BigDecimal total;

        private ShoppingCartBuilder() {
        }

        private ShoppingCartBuilder (ShoppingCartBuilder copy) {
            this.cartItems = copy.cartItems;
            this.subTotal = copy.subTotal;
            this.discountAmount = copy.discountAmount;
            this.total = copy.total;
        }

        public ShoppingCartBuilder but() {
            return new ShoppingCartBuilder(this);
        }

        public ShoppingCartBuilder withCartItemElement(CartItem cartItem) {
            cartItems.add(cartItem);
            return this;
        }

        public ShoppingCartBuilder withDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public ShoppingCartBuilder withSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public ShoppingCartBuilder withTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public ShoppingCart build() {
            ShoppingCart shoppingCart = new ShoppingCart();
            shoppingCart.setDiscountAmount(discountAmount);
            shoppingCart.setSubTotal(subTotal);
            shoppingCart.setTotal(total);
            this.cartItems.forEach(shoppingCart::addCartItem);
            return shoppingCart;
        }
    }
}
