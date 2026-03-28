package io.github.eduardout.e_commerce.entity;

/**
 * Specifies an object that represents an entity and must provide a qualified inmutable and unique identifier.
 *
 * @param <T> The type of the identifier.
 */
@FunctionalInterface
public interface Identifiable<T> {
    /**
     * @return An unique and inmutable identifier.
     */
    T getId();
}
