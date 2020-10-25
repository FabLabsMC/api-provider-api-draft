package io.github.fablabsmc.fablabs.api.provider.v1.item;

import java.util.Objects;

import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.impl.provider.item.ItemApiLookupRegistryImpl;
import org.jetbrains.annotations.NotNull;

import net.minecraft.util.Identifier;

public final class ItemApiLookupRegistry {
	public static <T, C> @NotNull ItemApiLookup<T, C> getLookup(Identifier apiId, ContextKey<C> contextKey) {
		Objects.requireNonNull(apiId, "Id of API cannot be null");
		Objects.requireNonNull(contextKey, "Context key cannot be null");

		return ItemApiLookupRegistryImpl.getLookup(apiId, contextKey);
	}

	private ItemApiLookupRegistry() {
	}
}
