package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockApiProvider<Api> {
    @Nullable Api get(World world, BlockPos pos, @NotNull Direction direction);
}
