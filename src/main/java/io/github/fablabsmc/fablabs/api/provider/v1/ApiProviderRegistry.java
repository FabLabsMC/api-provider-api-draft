package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Api accesses for Blocks, BlockEntities and ItemStacks. TODO: implement ItemStacks
 */
public final class ApiProviderRegistry {
    public static @Nullable <T> T getFromBlock(ApiKey<T> key, World world, BlockPos pos, @NotNull Direction direction) {
        return ApiProviderRegistryImpl.getFromBlock(key, world, pos, direction);
    }

    public static <T> void registerForBlock(ApiKey<T> key, BlockApiProvider<T> provider, Block... blocks) {
        ApiProviderRegistryImpl.registerForBlock(key, provider, blocks);
    }

    public static <T> void registerForBlockEntity(ApiKey<T> key, BlockEntityApiProvider<T> provider, BlockEntityType<?>... types) {
        ApiProviderRegistryImpl.registerForBlockEntity(key, provider, types);
    }
}
