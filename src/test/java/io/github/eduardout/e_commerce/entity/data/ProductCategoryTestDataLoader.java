package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.ProductCategory;
import io.github.eduardout.e_commerce.repository.ProductCategoryRepository;

import java.util.*;

public class ProductCategoryTestDataLoader extends TestDataLoader<ProductCategory> {

    public ProductCategoryTestDataLoader(ProductCategoryRepository productCategoryRepository) {
        super(productCategoryRepository);
    }

    public static ProductCategory findProductCategory(Set<ProductCategory> productCategories, String name) {
        return productCategories.stream()
                .filter(productCategory -> productCategory.getName().equals(name)
                        && productCategory.getId() != null)
                .findFirst().orElseThrow(() -> new NoSuchElementException("Product Category with name="
                        + name + " not found, be sure its persisted and the name is correct"));
    }

    @Override
    protected void setDefaultTestEntities() {
        addEntity(ProductCategory.aProductCategory()
                .withName("Tecnología")
                .withDescription("Productos tecnológicos")
                .build());
        addEntity(ProductCategory.aProductCategory()
                .withName("Higiene personal")
                .withDescription("Productos de higiene personal")
                .build());
        addEntity(ProductCategory.aProductCategory()
                .withName("Alimentos y Bebidas")
                .withDescription("Productos de consumo naturales y/o procesados")
                .build());
        addEntity(ProductCategory.aProductCategory()
                .withName("Limpieza")
                .withDescription("Productos de limpieza del hogar")
                .build());
    }
}
