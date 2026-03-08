package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.CategoryTestDataLoader;
import io.github.eduardout.e_commerce.entity.data.builder.Products;
import io.github.eduardout.e_commerce.repository.ProductCategoryRepository;
import io.github.eduardout.e_commerce.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductTest {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    private ProductCategory actualProductCategory;

    private void setUpProductCategory() {
        CategoryTestDataLoader categoryTestDataLoader = new CategoryTestDataLoader(productCategoryRepository);
        actualProductCategory = categoryTestDataLoader.setUp()
                .stream()
                .findFirst()
                .orElseThrow();
    }


    @Nested
    class TestNullability {
        private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
                DataIntegrityViolationException.class;
        private Product unexpectedProduct;

        @BeforeEach
        void beforeEach() {
            setUpProductCategory();
        }

        @Test
        void testNullName() {
            unexpectedProduct = Products.aProduct()
                    .withName(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullDescription() {
            unexpectedProduct = Products.aProduct()
                    .withDescription(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullDiscountPercentage() {
            unexpectedProduct = Products.aProduct()
                    .withDiscountPercentage(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullDiscountAmount() {
            unexpectedProduct = Products.aProduct()
                    .withDiscountAmount(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }



        @Test
        void testNullSellPrice() {
            unexpectedProduct = Products.aProduct()
                    .withSellPrice(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullPurchasePrice() {
            unexpectedProduct = Products.aProduct()
                    .withPurchasePrice(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullStock() {
            unexpectedProduct = Products.aProduct()
                    .withStock(null)
                    .withProductCategory(actualProductCategory)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }

        @Test
        void testNullProductCategory() {
            unexpectedProduct = Products.aProduct()
                    .withProductCategory(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () -> productRepository.save(unexpectedProduct));
        }
    }
}