package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreationRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 50, message = "Product name must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Description is mandatory")
        @Size(min = 15, max = 250, message = "Product description must be between 8 and 250 characters")
        String description,
        @NotNull(message = "Discount percentage is mandatory to apply to this product")
        @Min(value = 0, message = "Must provide a decimal discount greater than or equal to 0")
        @Max(value = 100, message = "Must provide a decimal discount less than or equal to 100")
        Byte discountPercentage,
        @NotNull(message = "Sell price is mandatory")
        @Digits(integer = 7, fraction = 2, message = "Sell price must have at most 7 integer digits and 2 decimal " +
                "digits")
        @DecimalMin(value = "0.00", message = "Sell price must not be negative")
        @DecimalMax(value = "9999999.99", message = "Sell price exceeded maximum limit")
        BigDecimal sellPrice,
        @NotNull(message = "Purchase price is mandatory")
        @Digits(integer = 7, fraction = 2, message = "Purchase price must have at most 7 integer digits and 2 " +
                "decimal digits")
        @DecimalMin(value = "0.00", message = "Purchase price must not be negative")
        @DecimalMax(value = "9999999.99", message = "Purchase price exceeded maximum limit")
        BigDecimal purchasePrice,
        @NotNull(message = "Stock is mandatory")
        @PositiveOrZero(message = "Stock must be zero or a positive value")
        Integer stock,
        @NotNull(message = "Product category identifier is mandatory")
        @Positive(message = "Product category identifier must be a positive value")
        Integer productCategoryId
) {
}
