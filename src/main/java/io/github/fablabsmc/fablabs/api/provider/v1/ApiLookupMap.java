package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiLookupMapImpl;
import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Provider. You should store an instance of this interface in a static variable.
 */
public interface ApiLookupMap<L> extends Iterable<L> {

    static <L> ApiLookupMap<L> create(LookupFactory<L> lookupFactory) {
        return ApiLookupMapImpl.create(lookupFactory);
    }

    @NotNull L getLookup(ApiKey<?> apiKey, ContextKey<?> contextKey);

    interface LookupFactory<L> {
        L create(ApiKey<?> apiKey, ContextKey<?> contextKey);
    }
}
