package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.Product;
import io.github.eduardout.e_commerce.entity.ProductCategory;
import io.github.eduardout.e_commerce.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.*;

import static io.github.eduardout.e_commerce.entity.data.ProductCategoryTestDataLoader.findProductCategory;
import static io.github.eduardout.e_commerce.entity.data.builder.Products.aProduct;

public class ProductTestDataLoader extends TestDataLoader<Product> {
    private final Set<ProductCategory> productCategories;

    public ProductTestDataLoader(ProductRepository productRepository,
                                 Set<ProductCategory> productCategories) {
        super(productRepository);
        validateEntities(productCategories);
        this.productCategories = productCategories;
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(aProduct()
                .withName("Palmolive")
                .withDescription("Shampoo para dama")
                .withPurchasePrice(new BigDecimal("250.00"))
                .withSellPrice(new BigDecimal("290.00"))
                .withStock(50)
                .withProductCategory(findProductCategory(productCategories, "Higiene personal"))
                .build());
        addEntity(aProduct().withName("Xiaomi Redmi 9")
                .withDescription("Smartphone de la serie Redmi de Xiaomi")
                .withPurchasePrice(new BigDecimal("1900.00"))
                .withSellPrice(new BigDecimal("2000.00"))
                .withStock(10)
                .withProductCategory(findProductCategory(productCategories, "Tecnología"))
                .build());
        addEntity(aProduct()
                .withName("Pringles")
                .withDescription("Papas a base de harina de trigo")
                .withPurchasePrice(new BigDecimal("60.00"))
                .withSellPrice(new BigDecimal("70.00"))
                .withStock(20)
                .withProductCategory(findProductCategory(productCategories, "Alimentos y Bebidas"))
                .build());
    }
}
