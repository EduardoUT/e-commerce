package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = {"purchaseItems"})
public class Purchase implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;
    @Getter
    @OneToMany(mappedBy = "purchase", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<PurchaseItem> purchaseItems = new ArrayList<>();
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
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal total;
    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Purchase other)) {
            return false;
        }
        return id != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void addPurchaseItem(PurchaseItem purchaseItem) {
        purchaseItems.add(purchaseItem);
        purchaseItem.setPurchase(this);
    }

    public void removePurchaseItem(PurchaseItem purchaseItem) {
        purchaseItems.remove(purchaseItem);
        purchaseItem.setPurchase(null);
    }

    public static PurchaseBuilder aPurchase() {
        return new PurchaseBuilder();
    }

    public static class PurchaseBuilder {
        private Customer customer;
        private final List<PurchaseItem> purchaseItems = new ArrayList<>();
        private BigDecimal discountAmount = DEFAULT_AMOUNT;
        private BigDecimal subTotal;
        private BigDecimal total;

        private PurchaseBuilder() {
        }

        public PurchaseBuilder withCustomer(Customer customer) {
            this.customer = customer;
            return this;
        }

        public PurchaseBuilder withPurchaseItemElement(PurchaseItem purchaseItem) {
            this.purchaseItems.add(purchaseItem);
            return this;
        }

        public PurchaseBuilder withDiscountAmount(BigDecimal discountAmount) {
            this.discountAmount = discountAmount;
            return this;
        }

        public PurchaseBuilder withSubTotal(BigDecimal subTotal) {
            this.subTotal = subTotal;
            return this;
        }

        public PurchaseBuilder withTotal(BigDecimal total) {
            this.total = total;
            return this;
        }

        public Purchase build() {
            Purchase purchase = new Purchase();
            purchase.setCustomer(customer);
            purchase.setDiscountAmount(discountAmount);
            purchase.setSubTotal(subTotal);
            purchase.setTotal(total);

            this.purchaseItems.forEach(purchase::addPurchaseItem);
            return purchase;
        }
    }
}
