package io.github.fablabsmc.fablabs.api.provider.v1.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class BlockApiLookup<T, C> extends ApiLookup<T, C> {
    public abstract @Nullable T get(World world, BlockPos pos, C context);

    public abstract void registerForBlocks(BlockApiProvider<T, C> provider, Block... blocks);
    public abstract void registerForBlockEntities(BlockEntityApiProvider<T, C> provider, BlockEntityType<?>... blockEntityTypes);

    protected BlockApiLookup(ApiKey<T> apiKey, ContextKey<C> contextKey) {
        super(apiKey, contextKey);
    }

    @FunctionalInterface
    public interface BlockApiProvider<T, C> {
        @Nullable T get(World world, BlockPos pos, C context);
    }

    @FunctionalInterface
    public interface BlockEntityApiProvider<T, C> {
        @Nullable T get(BlockEntity blockEntity, @NotNull C context);
    }
}
