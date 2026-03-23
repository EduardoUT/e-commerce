package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCategoryCreationRequest(
        @NotBlank(message = "Name of category is mandatory")
        @Size(min = 3, max = 50, message = "Product category name must be between 3 and 50 characters")
        String name,
        @NotBlank(message = "Must provide a description of this category")
        @Size(min = 15, max = 250, message = "Product category description must be between 15 and 250 characters")
        String description
) {
}
