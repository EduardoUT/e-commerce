package io.github.eduardout.e_commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class CartItemId implements Serializable {
    @Getter
    private Long shoppingCart;
    @Getter
    private Long product;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof CartItemId other)) {
            return false;
        }
        return Objects.equals(getShoppingCart(), other.getShoppingCart())
                && Objects.equals(getProduct(), other.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShoppingCart(), getProduct());
    }
}
