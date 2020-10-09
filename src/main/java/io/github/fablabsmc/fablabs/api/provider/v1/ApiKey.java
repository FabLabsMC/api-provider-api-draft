package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Unique reference to an API.
 */
public final class ApiKey<T> {
    private static final Map<Class<?>, Map<Identifier, ApiKey<?>>> apiKeys = new HashMap<>();

    private final Class<T> clazz;
    private final Identifier identifier;

    private ApiKey(Class<T> clazz, Identifier identifier) {
        this.clazz = clazz;
        this.identifier = identifier;
    }

    public Class<T> getApiClass() {
        return clazz;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public synchronized static <T> ApiKey<T> create(Class<T> clazz, Identifier identifier) {
        Objects.requireNonNull(clazz, "encountered null class in ApiKey creation");
        Objects.requireNonNull(identifier, "encountered null identifier in ApiKey creation");

        apiKeys.putIfAbsent(clazz, new HashMap<>());
        apiKeys.get(clazz).putIfAbsent(identifier, new ApiKey<>(clazz, identifier));
        return (ApiKey<T>) apiKeys.get(clazz).get(identifier);
    }
}
