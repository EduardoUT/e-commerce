package io.github.eduardout.e_commerce.entity;

import io.github.eduardout.e_commerce.entity.data.builder.ProductCategoryTestBuilder;
import io.github.eduardout.e_commerce.repository.ProductCategoryRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductCategoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Nested
    class TestNullability {
        private final Class<DataIntegrityViolationException> dataIntegrityViolationExceptionClass =
                DataIntegrityViolationException.class;
        private ProductCategory unexpectedProductCategory;

        @Test
        void testNullName() {
            unexpectedProductCategory = ProductCategoryTestBuilder.aProductCategory()
                    .withName(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () ->
                    productCategoryRepository.save(unexpectedProductCategory));
        }

        @Test
        void testNullDescription() {
            unexpectedProductCategory = ProductCategoryTestBuilder.aProductCategory()
                    .withDescription(null)
                    .build();
            assertThrows(dataIntegrityViolationExceptionClass, () ->
                    productCategoryRepository.save(unexpectedProductCategory));
        }
    }
}