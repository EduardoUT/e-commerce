package io.github.eduardout.e_commerce.entity.data.builder;

import io.github.eduardout.e_commerce.entity.ProductCategory;

public class ProductCategoryTestBuilder {
    public static ProductCategory.ProductCategoryBuilder aProductCategory() {
        return ProductCategory.aProductCategory()
                .withName("Test category")
                .withDescription("Test description");
    }
}
