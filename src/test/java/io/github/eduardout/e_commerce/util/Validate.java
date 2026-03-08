package io.github.eduardout.e_commerce.util;

import java.util.Collection;
import java.util.Objects;

public class Validate {
    private Validate() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static <T> void collectionNonNullAndNonEmpty(Collection<T> collection) {
        Objects.requireNonNull(collection, "Collection should not be null");
        if (collection.isEmpty())
            throw new IllegalStateException("Collection cannot be empty");
    }
}
