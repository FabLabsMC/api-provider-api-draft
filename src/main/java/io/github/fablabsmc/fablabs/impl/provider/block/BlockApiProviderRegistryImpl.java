package io.github.fablabsmc.fablabs.impl.provider.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookupMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookup;
import org.jetbrains.annotations.NotNull;

public class BlockApiProviderRegistryImpl {
    private static final ApiLookupMap<BlockApiLookupImpl<?, ?>> providers = ApiLookupMap.create(BlockApiLookupImpl::new);

    public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(ApiKey<T> key, ContextKey<C> contextKey) {
        return (BlockApiLookup<T, C>) providers.getLookup(key, contextKey);
    }
}
