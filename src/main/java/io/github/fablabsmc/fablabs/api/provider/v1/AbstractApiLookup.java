package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderApiImpl;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * The building block for creating your own lookup class. You should extend this and only use `get` and `putIfAbsent`.
 */
public abstract class AbstractApiLookup<T, C, K, P> implements ApiLookup<T, C> {
    private static final Logger LOGGER = LogManager.getLogger();

    private final ApiKey<T> apiKey;
    private final ContextKey<C> contextKey;
    private final Map<K, P> lookups = new Reference2ReferenceOpenHashMap<>();
    private volatile boolean initialized = false;

    protected AbstractApiLookup(ApiKey<T> apiKey, ContextKey<C> contextKey) {
        this.apiKey = apiKey;
        this.contextKey = contextKey;
    }

    protected @Nullable P get(K key) {
        if(!initialized) {
            throw new RuntimeException("Attempted to get() from an uninitialized AbstractApiLookup");
        }
        return lookups.get(key);
    }

    protected void putIfAbsent(K key, P provider) {
        synchronized (ApiProviderApiImpl.LOCK) {
            if(initialized) {
                throw new RuntimeException("Attempted to register a provider in an initialized AbstractApiLookup");
            }
            if(lookups.putIfAbsent(key, provider) != null) {
                LOGGER.warn("Attempted to overwrite a provider in an AbstractApiLookup"); // TODO: better error message
            }
        }
    }

    @NotNull
    public ApiKey<T> getApiKey() {
        return apiKey;
    }
    @NotNull
    public ContextKey<C> getContextKey() {
        return contextKey;
    }

    public void initialize() {
        initialized = true;
    }
}
