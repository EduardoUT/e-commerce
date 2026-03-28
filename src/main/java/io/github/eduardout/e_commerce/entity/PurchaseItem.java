package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity(name = "purchase_item")
@IdClass(PurchaseItemId.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"purchase"})
@Builder(builderMethodName = "aPurchaseItem", setterPrefix = "with")
public class PurchaseItem implements CompositeIdentifiable {
    @Getter
    @Setter
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "purchase_id", nullable = false)
    private Purchase purchase;
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

        if (!(obj instanceof PurchaseItem other)) {
            return false;
        }
        return purchase != null && product != null
                && Objects.equals(purchase.getId(), other.getPurchase().getId())
                && Objects.equals(product.getId(), other.getProduct().getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public Keyable getId() {
        return new PurchaseItemId(purchase.getId(), product.getId());
    }
}
