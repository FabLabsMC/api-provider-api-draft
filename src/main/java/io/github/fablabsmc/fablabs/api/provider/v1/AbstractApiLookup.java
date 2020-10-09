package io.github.fablabsmc.fablabs.api.provider.v1;

import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own lookup class. You should extend this and only use `get` and `putIfAbsent`.
 */
public abstract class AbstractApiLookup<T, C> implements ApiLookup<T, C> {
    private final ApiKey<T> apiKey;
    private final ContextKey<C> contextKey;

    protected AbstractApiLookup(ApiKey<T> apiKey, ContextKey<C> contextKey) {
        this.apiKey = apiKey;
        this.contextKey = contextKey;
    }

    @NotNull
    public ApiKey<T> getApiKey() {
        return apiKey;
    }
    @NotNull
    public ContextKey<C> getContextKey() {
        return contextKey;
    }
}
