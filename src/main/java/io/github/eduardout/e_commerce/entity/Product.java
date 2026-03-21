package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Product implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(nullable = false)
    private String description;
    @Getter
    @Setter
    @Column(name = "discount_percentage", options = "unsigned not null")
    private Byte discountPercentage;
    @Getter
    @Setter
    @Column(name = "discount_amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal discountAmount;
    @Getter
    @Setter
    @Column(name = "sell_price", nullable = false, precision = 9, scale = 2)
    private BigDecimal sellPrice;
    @Getter
    @Setter
    @Column(name = "purchase_price", nullable = false, precision = 9, scale = 2)
    private BigDecimal purchasePrice;
    @Getter
    @Setter
    @Column(nullable = false)
    private Integer stock;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_category_id", nullable = false)
    private ProductCategory productCategory;
    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder(builderMethodName = "aProduct", setterPrefix = "with")
    private Product(String name,
                    String description,
                    Byte discountPercentage,
                    BigDecimal discountAmount,
                    BigDecimal sellPrice,
                    BigDecimal purchasePrice,
                    Integer stock,
                    ProductCategory productCategory) {
        this.name = name;
        this.description = description;
        this.discountPercentage = discountPercentage;
        this.discountAmount = discountAmount;
        this.sellPrice = sellPrice;
        this.purchasePrice = purchasePrice;
        this.stock = stock;
        this.productCategory = productCategory;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Product other)) {
            return false;
        }
        return id != null && other.getId() != null && id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
