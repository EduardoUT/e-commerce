package io.github.eduardout.e_commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class OrderItemId implements Serializable {
    @Getter
    private Long orders;
    @Getter
    private Long product;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof OrderItemId other)) {
            return false;
        }
        return Objects.equals(getOrders(), other.getOrders())
                && Objects.equals(getProduct(), other.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrders(), getProduct());
    }
}
