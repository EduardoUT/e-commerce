package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "fiscal_data")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class FiscalData {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String rfc;
    @Getter
    @Setter
    @Column(name = "regime_tax", nullable = false)
    private String regimeTax;
    @Getter
    @Setter
    @Column(name = "fiscal_address", nullable = false)
    private String fiscalAddress;

    @Builder(builderMethodName = "aFiscalData", setterPrefix = "with")
    private FiscalData(String rfc, String regimeTax, String fiscalAddress) {
        this.rfc = rfc;
        this.regimeTax = regimeTax;
        this.fiscalAddress = fiscalAddress;
    }
}
