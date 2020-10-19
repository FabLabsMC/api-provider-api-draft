package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.mojang.datafixers.util.Unit;
import org.jetbrains.annotations.Nullable;

/**
 * Unique reference to a type of context.
 */
public final class ContextKey<C> {
    public static final ContextKey<@Nullable Void> NO_CONTEXT = create(Void.class, new Identifier("fabric:no_context"));
    private static final Map<Class<?>, Map<Identifier, ContextKey<?>>> contextKeys = new HashMap<>();

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
        Objects.requireNonNull(clazz, "encountered null class in ContextKey creation");
        Objects.requireNonNull(identifier, "encountered null identifier in ContextKey creation");

        contextKeys.putIfAbsent(clazz, new HashMap<>());
        contextKeys.get(clazz).putIfAbsent(identifier, new ContextKey<>(clazz, identifier));
        return (ContextKey<C>) contextKeys.get(clazz).get(identifier);
    }
}
