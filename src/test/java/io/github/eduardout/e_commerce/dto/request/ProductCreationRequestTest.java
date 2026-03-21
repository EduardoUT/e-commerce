package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.Product;
import io.github.eduardout.e_commerce.entity.data.builder.Products;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

class ProductCreationRequestTest {
    private static final Integer PRODUCT_CATEGORY_ID = 1;
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private Product productTest;

    @BeforeEach
    void setUp() {
        productTest = Products.aProduct().build();
    }

    @Test
    void testValidProductCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testNotNullName() {
        constraintViolationAssertion.assertConstraintViolation(
                "Name is mandatory",
                "name",
                new ProductCreationRequest(
                        null,
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testNotBlankName() {
        constraintViolationAssertion.assertConstraintViolation(
                "Name is mandatory",
                "name",
                new ProductCreationRequest(
                        "",
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidSizeNames")
    void testSizeName(String name) {
        constraintViolationAssertion.assertConstraintViolation(
                "Product name must be between 3 and 50 characters",
                "name",
                new ProductCreationRequest(
                        name,
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    static Stream<String> streamOfInvalidSizeNames() {
        return Stream.of(
                "This is an intentional large product name and exceeds the maximum size",
                "T"
        );
    }

    @Test
    void testNotNullDescription() {
        constraintViolationAssertion.assertConstraintViolation(
                "Description is mandatory",
                "description",
                new ProductCreationRequest(
                        productTest.getName(),
                        null,
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testNotBlankDescription() {
        constraintViolationAssertion.assertConstraintViolation(
                "Description is mandatory",
                "description",
                new ProductCreationRequest(
                        productTest.getName(),
                        "",
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidDescriptions")
    void testSizeDescription(String description) {
        constraintViolationAssertion.assertConstraintViolation(
                "Product description must be between 8 and 250 characters",
                "description",
                new ProductCreationRequest(
                        productTest.getName(),
                        description,
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    static Stream<String> streamOfInvalidDescriptions() {
        return Stream.of(
                """
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been
                         the industry's standard dummy text ever since the 1500s, when an unknown printer took a
                         galley of type and scrambled it to make a type specimen book. It has""",
                "Description"
        );
    }

    @Test
    void testNotNullDiscountPercentage() {
        constraintViolationAssertion.assertConstraintViolation(
                "Discount percentage is mandatory to apply to this product",
                "discountPercentage",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        null,
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testMinDiscountPercentage() {
        constraintViolationAssertion.assertConstraintViolation(
                "Must provide a decimal discount greater than or equal to 0",
                "discountPercentage",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        Byte.parseByte("-1"),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }
    @Test
    void testMaxDiscountPercentage() {
        constraintViolationAssertion.assertConstraintViolation(
                "Must provide a decimal discount less than or equal to 100",
                "discountPercentage",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        Byte.parseByte("101"),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testDigitsSellPrice() {
        constraintViolationAssertion.assertConstraintViolation(
                "Sell price must have at most 7 integer digits and 2 decimal digits",
                "sellPrice",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        new BigDecimal("99999999.999"),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testDecimalMinSellPrice() {
        constraintViolationAssertion.assertConstraintViolation(
                "Sell price must not be negative",
                "sellPrice",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        new BigDecimal("-1.00"),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }
    @Test
    void testDecimalMaxSellPrice() {
        constraintViolationAssertion.assertConstraintViolation(
                "Sell price exceeded maximum limit",
                "sellPrice",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        new BigDecimal("99999999.99"),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testNotNullStock() {
        constraintViolationAssertion.assertConstraintViolation(
                "Stock is mandatory",
                "stock",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        null,
                        PRODUCT_CATEGORY_ID
                )
        );
    }
    @Test
    void testPositiveOrZeroStock() {
        constraintViolationAssertion.assertConstraintViolation(
                "Stock must be zero or a positive value",
                "stock",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        -1,
                        PRODUCT_CATEGORY_ID
                )
        );
    }

    @Test
    void testNotNullProductCategoryId() {
        constraintViolationAssertion.assertConstraintViolation(
                "Product category identifier is mandatory",
                "productCategoryId",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        null
                )
        );
    }

    @Test
    void testPositiveProductCategoryId() {
        constraintViolationAssertion.assertConstraintViolation(
                "Product category identifier must be a positive value",
                "productCategoryId",
                new ProductCreationRequest(
                        productTest.getName(),
                        productTest.getDescription(),
                        productTest.getDiscountPercentage(),
                        productTest.getSellPrice(),
                        productTest.getPurchasePrice(),
                        productTest.getStock(),
                        0
                )
        );
    }

}