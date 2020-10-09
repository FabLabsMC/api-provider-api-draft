package io.github.fablabsmc.fablabs.api.provider.v1;

import org.jetbrains.annotations.NotNull;

/**
 * Base interface implemented by every Lookup.
 */
public interface ApiLookup<T, C> {
    @NotNull ApiKey<T> getApiKey();
    @NotNull ContextKey<C> getContextKey();
}
