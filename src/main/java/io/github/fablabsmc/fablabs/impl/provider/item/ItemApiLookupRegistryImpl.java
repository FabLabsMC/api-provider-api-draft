package io.github.fablabsmc.fablabs.impl.provider.item;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookupMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.item.ItemApiLookup;

import net.minecraft.util.Identifier;

public final class ItemApiLookupRegistryImpl {
	private static final ApiLookupMap<ItemApiLookupImpl<?, ?>> PROVIDERS = ApiLookupMap.create(ItemApiLookupImpl::new);

	public static <T, C> ItemApiLookup<T, C> getLookup(Identifier key, ContextKey<C> contextKey) {
		@SuppressWarnings("unchecked")
		final ItemApiLookup<T, C> lookup = (ItemApiLookup<T, C>) PROVIDERS.getLookup(key, contextKey);
		return lookup;
	}

	private ItemApiLookupRegistryImpl() {
	}
}
