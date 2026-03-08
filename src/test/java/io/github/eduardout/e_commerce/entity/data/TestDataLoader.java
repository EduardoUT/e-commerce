package io.github.eduardout.e_commerce.entity.data;

import io.github.eduardout.e_commerce.entity.Identifiable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static io.github.eduardout.e_commerce.util.Validate.collectionNonNullAndNonEmpty;

/**
 * Class to load and save transient test entities that implement Identifiable interface.
 *
 * @param <T> Type of the test entities.
 */
public abstract class TestDataLoader<T extends Identifiable<?>> {

    @Getter
    private Set<T> transientEntities = new HashSet<>();

    @Getter(AccessLevel.PROTECTED)
    @Setter
    private JpaRepository<T, ?> jpaRepository;

    public TestDataLoader(JpaRepository<T, ?> jpaRepository) {
        Objects.requireNonNull(jpaRepository, "Repository should not be null");
        this.jpaRepository = jpaRepository;
    }

    /**
     * <p>
     * Terminal operation
     * </p>
     *
     * <p>
     * Intended to return the persistent entities.
     * </p>
     *
     * @return Persistent entities
     */
    public Set<T> setUp() {
        return loadAndRetrieve();
    }

    /**
     * Intended to load the default entities to persist.
     */
    protected abstract void setDefaultTestEntities();

    public void addEntity(T entity) {
        Objects.requireNonNull(entity, "Added entity should not be null");
        transientEntities.add(entity);
    }

    public void addEntities(Collection<T> elements) {
        transientEntities.addAll(elements);
    }

    /**
     * <p>
     * Terminal operation to save test entities.
     * </p>
     *
     * <p>
     * Persist default transientEntities added on setDefaultTestEntities() within the context of inheritance via Set
     * of transientEntities with addEntity() or addEntities() methods.
     * </p>
     *
     * @return Persistent entities returned by saveAll().
     */
    @Transactional
    protected Set<T> loadAndRetrieve() {
        setDefaultTestEntities();
        collectionNonNullAndNonEmpty(transientEntities);
        return new HashSet<>(jpaRepository.saveAll(transientEntities));
    }

    protected <U extends Identifiable<?>> void validateEntities(Collection<U> entities) {
        collectionNonNullAndNonEmpty(entities);
        entities.forEach(this::validateEntity);
    }

    protected <U extends Identifiable<?>> U validateEntity(U entity) {
        String className = entity.getClass().getName();
        Objects.requireNonNull(entity, "Entity " + className + " should not be null");
        if (entity.getId() == null) {
            throw new IllegalStateException("Entity " + className + " id should not be null, be sure its persisted");
        }
        return entity;
    }
}
