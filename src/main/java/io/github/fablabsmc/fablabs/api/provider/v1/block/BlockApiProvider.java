package io.github.fablabsmc.fablabs.api.provider.v1.block;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockApiProvider<T, C> {
    @Nullable T get(World world, BlockPos pos, C context);
}
