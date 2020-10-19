package io.github.fablabsmc.fablabs.api.provider.v1.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.impl.provider.block.BlockApiLookupRegistryImpl;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public final class BlockApiLookupRegistry {
    public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(Identifier apiId, ContextKey<C> contextKey) {
        return BlockApiLookupRegistryImpl.getLookup(apiId, contextKey);
    }
}
