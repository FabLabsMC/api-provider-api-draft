package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unique reference to a type of context.
 */
public final class ContextKey<C> {
    private static final Map<Class<?>, Map<Identifier, ContextKey<?>>> CONTEXT_KEYS = new HashMap<>();
    private final Class<C> clazz;
    private final Identifier identifier;

    private ContextKey(Class<C> clazz, Identifier identifier) {
        this.clazz = clazz;
        this.identifier = identifier;
    }

    public Class<C> getContextClass() {
        return clazz;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public synchronized static <C> ContextKey<C> create(Class<C> clazz, Identifier identifier) {
        Objects.requireNonNull(clazz, "Class type cannot be null");
        Objects.requireNonNull(identifier, "Context key cannot be null");

        CONTEXT_KEYS.putIfAbsent(clazz, new HashMap<>());
        CONTEXT_KEYS.get(clazz).putIfAbsent(identifier, new ContextKey<>(clazz, identifier));

        //noinspection unchecked
        return (ContextKey<C>) CONTEXT_KEYS.get(clazz).get(identifier);
    }
}
