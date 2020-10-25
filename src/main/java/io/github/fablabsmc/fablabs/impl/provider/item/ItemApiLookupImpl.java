package io.github.fablabsmc.fablabs.impl.provider.item;

import java.util.Objects;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.item.ItemApiLookup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

final class ItemApiLookupImpl<T, C> implements ItemApiLookup<T, C> {
	private static final Logger LOGGER = LogManager.getLogger();
	private final ApiProviderMap<Item, ItemApiProvider<?, ?>> providerMap = ApiProviderMap.create();
	private final Identifier id;
	private final ContextKey<C> contextKey;

	ItemApiLookupImpl(Identifier apiId, ContextKey<C> contextKey) {
		this.id = apiId;
		this.contextKey = contextKey;
	}

	@Override
	public @Nullable T get(ItemStack stack, C context) {
		Objects.requireNonNull(stack, "World cannot be null");
		// Providers have the final say whether a null context is allowed.

		@SuppressWarnings("unchecked")
		@Nullable
		final ItemApiProvider<T, C> provider = (ItemApiProvider<T, C>) providerMap.get(stack.getItem());

		if (provider != null) {
			return provider.get(stack, context);
		}

		return null;
	}

	@Override
	public void register(ItemApiProvider<T, C> provider, ItemConvertible... items) {

	}

	@Override
	public @NotNull Identifier getApiId() {
		return null;
	}

	@Override
	public @NotNull ContextKey<C> getContextKey() {
		return null;
	}
}
