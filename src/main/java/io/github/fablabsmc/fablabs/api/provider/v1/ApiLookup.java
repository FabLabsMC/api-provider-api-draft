package io.github.fablabsmc.fablabs.api.provider.v1;

import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Lookup class.
 */
public abstract class ApiLookup<T, C> {
    private final ApiKey<T> apiKey;
    private final ContextKey<C> contextKey;

    protected ApiLookup(ApiKey<T> apiKey, ContextKey<C> contextKey) {
        this.apiKey = apiKey;
        this.contextKey = contextKey;
    }

    public final @NotNull ApiKey<T> getApiKey() {
        return apiKey;
    }

    public final @NotNull ContextKey<C> getContextKey() {
        return contextKey;
    }
}
