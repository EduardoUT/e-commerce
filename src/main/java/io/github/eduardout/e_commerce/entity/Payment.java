package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.util.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString(exclude = "customer")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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
    @EqualsAndHashCode.Include
    private String externalId;
    @Getter
    @Setter
    @Column(name = "gateway_name")
    private String gatewayName;
    @Getter
    @Setter
    @Column(nullable = false)
    private BigDecimal amount;
    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

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
}
