package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.util.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "customer")
public class Payment implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "customer_id", nullable = false, updatable = false)
    private Customer customer;
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;
    @Getter
    @Setter
    @Column(name = "external_id", nullable = false, unique = true, updatable = false)
    private String externalId;
    @Getter
    @Setter
    @Column(name = "gateway_name", nullable = false, updatable = false)
    private String gatewayName;
    @Getter
    @Setter
    @Column(nullable = false, precision = 9, scale = 2)
    private BigDecimal amount;
    @Getter
    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Getter
    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder(builderMethodName = "aPayment", setterPrefix = "with")
    private Payment(Customer customer,
                    PaymentStatus paymentStatus,
                    String externalId,
                    String gatewayName,
                    BigDecimal amount) {

        this.customer = customer;
        this.paymentStatus = paymentStatus;
        this.externalId = externalId;
        this.gatewayName = gatewayName;
        this.amount = amount;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Payment other)) return false;
        return id != null
                && id.equals(other.getId())
                && Objects.equals(externalId, other.getExternalId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
