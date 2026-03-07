package io.github.eduardout.e_commerce.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
public class PurchaseItemId implements Serializable {
    @Getter
    private Long purchase;
    @Getter
    private Long product;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof PurchaseItemId other)) {
            return false;
        }
        return Objects.equals(getPurchase(), other.getPurchase())
                && Objects.equals(getProduct(), other.getProduct());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPurchase(), getProduct());
    }
}
