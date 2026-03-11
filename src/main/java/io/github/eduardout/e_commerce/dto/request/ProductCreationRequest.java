package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductCreationRequest(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, max = 50, message = "Product name is out of range which is minimum 3 and maximum 50 characters")
        String name,
        @NotBlank(message = "Description is mandatory")
        @Size(min = 8, max = 250, message = "Product description is out of range which is minimum 8 and maximum 250 " +
                "characters")
        String description,
        @NotNull(message = "Must provide a decimal discount between 1 and 100")
        @Min(0)
        @Max(100)
        Byte discountPercentage,
        @NotBlank(message = "Sell price is mandatory")
        BigDecimal sellPrice,
        @NotBlank(message = "Purchase price is mandatory")
        BigDecimal purchasePrice,
        @Size(min = 1, message = "Stock out of range")
        Integer stock,
        @NotNull(message = "Must specify a product category")
        ProductCategoryCreationRequest productCategoryCreationRequest
) {
}
