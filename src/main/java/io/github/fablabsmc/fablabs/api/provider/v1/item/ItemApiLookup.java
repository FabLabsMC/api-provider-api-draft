package io.github.fablabsmc.fablabs.api.provider.v1.item;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookup;
import org.jetbrains.annotations.Nullable;

import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

public interface ItemApiLookup<T, C> extends ApiLookup<C> {
	@Nullable T get(ItemStack stack, C context);

	void register(ItemApiProvider<T, C> provider, ItemConvertible... items);

	interface ItemApiProvider<T, C> {
		@Nullable
		T get(ItemStack stack, C context);
	}
}
