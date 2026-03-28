package io.github.eduardout.e_commerce.entity;

/**
 * <p>
 * Maps to an entity that has two or more {@link jakarta.persistence.Id} annotations to conform a composite key
 * identifier of type {@link Keyable}.
 * </p>
 * <p>
 * The implementation must provide the same type as the provided in the {@link jakarta.persistence.IdClass}
 * annotation.
 * </p>
 */
public interface CompositeIdentifiable extends Identifiable<Keyable> {
}
