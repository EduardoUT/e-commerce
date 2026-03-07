package io.github.eduardout.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "product_category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@EqualsAndHashCode
public class ProductCategory implements Identifiable<Integer> {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Getter
    @Setter
    @Column(nullable = false)
    private String name;
    @Getter
    @Setter
    @Column(nullable = false)
    private String description;

    @Builder(builderMethodName = "aProductCategory", setterPrefix = "with")
    private ProductCategory(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
