package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Seller implements Identifiable<Long> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;
    @Getter
    @Setter
    @Embedded
    @Column(nullable = false, unique = true)
    private PersonalData personalData;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "business_address_id", nullable = false, unique = true)
    private Address address;
    @Getter
    @Setter
    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinTable(name = "seller_fiscal_data",
            joinColumns = @JoinColumn(name = "seller_id"), inverseJoinColumns = @JoinColumn(name = "fiscal_data_id"))
    private FiscalData fiscalData;

    @Builder(builderMethodName = "aSeller", setterPrefix = "with")
    private Seller(User user,
                   PersonalData personalData,
                   Address address,
                   FiscalData fiscalData) {
        this.user = user;
        this.personalData = personalData;
        this.address = address;
        this.fiscalData = fiscalData;
    }
}
