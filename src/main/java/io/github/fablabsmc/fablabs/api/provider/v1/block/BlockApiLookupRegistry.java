package io.github.fablabsmc.fablabs.api.provider.v1.block;

import java.util.Objects;

import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.impl.provider.block.BlockApiLookupRegistryImpl;

import net.minecraft.util.Identifier;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class BlockApiLookupRegistry {
	public static <T, C> @NotNull BlockApiLookup<T, C> getLookup(Identifier apiId, ContextKey<C> contextKey) {
		Objects.requireNonNull(apiId, "Id of API cannot be null");
		Objects.requireNonNull(contextKey, "Context key cannot be null");

		return BlockApiLookupRegistryImpl.getLookup(apiId, contextKey);
	}

	private BlockApiLookupRegistry() {
	}
}
