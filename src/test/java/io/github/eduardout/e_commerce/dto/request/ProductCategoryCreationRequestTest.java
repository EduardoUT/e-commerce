package io.github.eduardout.e_commerce.dto.request;

import io.github.eduardout.e_commerce.dto.ConstraintViolationAssertion;
import io.github.eduardout.e_commerce.entity.ProductCategory;
import io.github.eduardout.e_commerce.entity.data.builder.ProductCategoryTestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class ProductCategoryCreationRequestTest {
    private final ConstraintViolationAssertion constraintViolationAssertion = new ConstraintViolationAssertion();
    private ProductCategory productCategoryTest;

    @BeforeEach
    void setUp() {
        productCategoryTest = ProductCategoryTestBuilder.aProductCategory().build();
    }

    static Stream<String> streamOfInvalidStringValues() {
        return Stream.of("", null);
    }

    @Test
    void testValidProductCategoryCreationRequest() {
        constraintViolationAssertion.assertEmptyConstraintViolations(
                new ProductCategoryCreationRequest(productCategoryTest.getName(), productCategoryTest.getDescription())
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStringValues")
    void testNotNullName(String name) {
        constraintViolationAssertion.assertConstraintViolation(
                "Name of category is mandatory",
                "name",
                new ProductCategoryCreationRequest(name, productCategoryTest.getDescription())
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStringSizeName")
    void testSizeName(String name) {
        constraintViolationAssertion.assertConstraintViolation(
                "Product category name must be between 3 and 50 characters",
                "name",
                new ProductCategoryCreationRequest(name, productCategoryTest.getDescription())
        );
    }

    static Stream<String> streamOfInvalidStringSizeName() {
        return Stream.of(
                "This is an intentional large product name and exceeds the maximum size",
                "T"
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStringValues")
    void testNotBlankDescription(String description) {
        constraintViolationAssertion.assertConstraintViolation(
                "Must provide a description of this category",
                "description",
                new ProductCategoryCreationRequest(productCategoryTest.getName(), description)
        );
    }

    @ParameterizedTest
    @MethodSource("streamOfInvalidStringSizeDescription")
    void testSizeDescription(String description) {
        constraintViolationAssertion.assertConstraintViolation(
                "Product category description must be between 15 and 250 characters",
                "description",
                new ProductCategoryCreationRequest(productCategoryTest.getName(), description)
        );
    }

    static Stream<String> streamOfInvalidStringSizeDescription() {
        return Stream.of(
                """
                        Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been
                         the industry's standard dummy text ever since the 1500s, when an unknown printer took a
                         galley of type and scrambled it to make a type specimen book. It has""",
                "Description"
        );
    }
}