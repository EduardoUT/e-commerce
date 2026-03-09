package io.github.eduardout.e_commerce.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ProductCategoryCreationRequest(
        @NotBlank(message = "Name of category is mandatory")
        @Size(min = 3, max = 50, message = "Name out of range which minimum is 3 and maximum 50 characters")
        String name,
        @NotBlank(message = "Must provide a description of this category")
        @Size(min = 10, max = 250, message = "Description out of range which minimum is 10 and maximum 250 characters")
        String description
) {
}
