package io.github.fablabsmc.fablabs.api.provider.v1.block;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface BlockApiLookup<T, C> extends ApiLookup<C> {
    @Nullable T get(World world, BlockPos pos, C context);

    void registerForBlocks(BlockApiProvider<T, C> provider, Block... blocks);
    void registerForBlockEntities(BlockEntityApiProvider<T, C> provider, BlockEntityType<?>... blockEntityTypes);

    @FunctionalInterface
    interface BlockApiProvider<T, C> {
        @Nullable T get(World world, BlockPos pos, C context);
    }

    @FunctionalInterface
    interface BlockEntityApiProvider<T, C> {
        @Nullable T get(BlockEntity blockEntity, @NotNull C context);
    }
}
