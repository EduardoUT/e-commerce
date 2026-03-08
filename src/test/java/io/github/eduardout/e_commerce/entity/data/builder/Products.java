package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.Product;

import java.math.BigDecimal;

import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_AMOUNT;
import static io.github.eduardout.e_commerce.util.Calculation.DEFAULT_PERCENTAGE;

public class Products {
    public static Product.ProductBuilder aProduct() {
        return Product.aProduct()
                .withName("Test product")
                .withDescription("Some test description")
                .withDiscountPercentage(DEFAULT_PERCENTAGE)
                .withDiscountAmount(DEFAULT_AMOUNT)
                .withSellPrice(new BigDecimal("23.99"))
                .withPurchasePrice(new BigDecimal("25.99"))
                .withStock(10)
                .withProductCategory(ProductCategoryTestBuilder.aProductCategory().build());
    }
}
