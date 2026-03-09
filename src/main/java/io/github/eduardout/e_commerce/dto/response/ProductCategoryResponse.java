package io.github.eduardout.e_commerce.dto.response;

public record ProductCategoryResponse(
        Integer categoryId,
        String name,
        String description
) {
}
