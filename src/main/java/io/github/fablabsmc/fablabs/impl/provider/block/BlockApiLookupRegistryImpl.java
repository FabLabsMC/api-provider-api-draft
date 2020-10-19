package io.github.fablabsmc.fablabs.impl.provider.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookupMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public final class BlockApiLookupRegistryImpl {
    private static final ApiLookupMap<BlockApiLookupImpl<?, ?>> providers = ApiLookupMap.create(BlockApiLookupImpl::new);

    public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(Identifier key, ContextKey<C> contextKey) {
        return (BlockApiLookup<T, C>) providers.getLookup(key, contextKey);
    }
}
