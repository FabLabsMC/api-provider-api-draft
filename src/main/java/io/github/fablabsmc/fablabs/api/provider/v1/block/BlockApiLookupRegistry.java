package io.github.fablabsmc.fablabs.api.provider.v1.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.impl.provider.block.BlockApiLookupRegistryImpl;
import org.jetbrains.annotations.NotNull;

public final class BlockApiLookupRegistry {
    public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(ApiKey<T> key, ContextKey<C> contextKey) {
        return BlockApiLookupRegistryImpl.getLookup(key, contextKey);
    }
}
