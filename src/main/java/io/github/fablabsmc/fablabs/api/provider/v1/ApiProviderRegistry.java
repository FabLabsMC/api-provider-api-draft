package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

/**
 * Api accesses for Blocks, BlockEntities and ItemStacks. TODO: implement ItemStacks
 */
public final class ApiProviderRegistry {
    public static @Nullable <T, C> T getFromBlock(ApiKey<T> key, ContextKey<C> contextKey, World world, BlockPos pos, C context) {
        return ApiProviderRegistryImpl.getFromBlock(key, contextKey, world, pos, context);
    }

    public static <T, C> void registerForBlock(ApiKey<T> key, ContextKey<C> contextKey, BlockApiProvider<T, C> provider, Block... blocks) {
        ApiProviderRegistryImpl.registerForBlock(key, contextKey, provider, blocks);
    }

    public static <T, C> void registerForBlockEntity(ApiKey<T> key, ContextKey<C> contextKey, BlockEntityApiProvider<T, C> provider, BlockEntityType<?>... types) {
        ApiProviderRegistryImpl.registerForBlockEntity(key, contextKey, provider, types);
    }
}
