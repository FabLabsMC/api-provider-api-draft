package io.github.fablabsmc.fablabs.api.provider.v1;

import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Lookup class.
 */
public interface ApiLookup<T, C> {
    @NotNull ApiKey<T> getApiKey();
    @NotNull ContextKey<C> getContextKey();
}
