package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderMapImpl;
import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Provider. You should store an instance of this interface in a static variable.
 */
public interface ApiProviderMap<L extends AbstractApiLookup<?, ?, K, P>, K, P> {

    static <L extends AbstractApiLookup<?, ?, K, P>, K, P> ApiProviderMap<L, K, P> create(LookupConstructor<L, K, P> lookupConstructor) {
        return ApiProviderMapImpl.create(lookupConstructor);
    }

    @NotNull L getLookup(ApiKey<?> apiKey, ContextKey<?> contextKey);

    Iterable<L> getLookups();

    interface LookupConstructor<L extends AbstractApiLookup<?, ?, K, P>, K, P> {
        L create(ApiKey<?> apiKey, ContextKey<?> contextKey);
    }
}
