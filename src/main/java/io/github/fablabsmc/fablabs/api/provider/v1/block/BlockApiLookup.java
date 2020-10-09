package io.github.fablabsmc.fablabs.api.provider.v1.block;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public interface BlockApiLookup<T, C> {
    @Nullable T get(World world, BlockPos pos, C context);

    void registerForBlocks(BlockApiProvider<T, C> provider, Block... blocks);
    void registerForBlockEntities(BlockEntityApiProvider<T, C> provider, BlockEntityType<?>... blockEntityTypes);
}
