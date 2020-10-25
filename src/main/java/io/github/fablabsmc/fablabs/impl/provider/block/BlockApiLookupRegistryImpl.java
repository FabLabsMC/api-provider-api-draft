package io.github.fablabsmc.fablabs.impl.provider.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookupMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookup;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BlockApiLookupRegistryImpl {
    private static final ApiLookupMap<BlockApiLookupImpl<?, ?>> PROVIDERS = ApiLookupMap.create(BlockApiLookupImpl::new);

    public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(Identifier key, ContextKey<C> contextKey) {
        @SuppressWarnings("unchecked")
		BlockApiLookup<T, C> lookup = (BlockApiLookup<T, C>) PROVIDERS.getLookup(key, contextKey);
        return lookup;
    }

    private BlockApiLookupRegistryImpl() {
	}
}
