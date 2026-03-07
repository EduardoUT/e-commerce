package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class Address {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String street;
    @Getter
    @Setter
    @Column(nullable = false)
    private String neighborhood;
    @Getter
    @Setter
    @Column(nullable = false)
    private String municipality;
    @Getter
    @Setter
    @Column(nullable = false)
    private String state;
    @Getter
    @Setter
    @Column(nullable = false)
    private String zipCode;
    @Getter
    @Setter
    @Column(nullable = false)
    private Integer externalNumber;
    @Getter
    @Setter
    private Integer internalNumber;

    @Builder(builderMethodName = "anAddress", setterPrefix = "with")
    private Address(String street, String neighborhood, String municipality, String state, String zipCode,
                   Integer externalNumber, Integer internalNumber) {
        this.street = street;
        this.neighborhood = neighborhood;
        this.municipality = municipality;
        this.state = state;
        this.zipCode = zipCode;
        this.externalNumber = externalNumber;
        this.internalNumber = internalNumber;
    }
}
