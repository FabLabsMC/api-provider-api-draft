package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unique reference to a type of context.
 */
public final class ContextKey<C> {
    private static final Map<Class<?>, Map<Identifier, ContextKey<?>>> contextKeys = new HashMap<>();

    private ContextKey() { }

    public static <C> ContextKey<C> create(Class<C> clazz, Identifier identifier) {
        Objects.requireNonNull(clazz, "encountered null class in ContextKey creation");
        Objects.requireNonNull(identifier, "encountered null identifier in ContextKey creation");

        contextKeys.putIfAbsent(clazz, new HashMap<>());
        contextKeys.get(clazz).putIfAbsent(identifier, new ContextKey<>());
        return (ContextKey<C>) contextKeys.get(clazz).get(identifier);
    }
}
