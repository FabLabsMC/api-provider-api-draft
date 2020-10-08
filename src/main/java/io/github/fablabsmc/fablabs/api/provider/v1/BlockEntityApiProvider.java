package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockEntityApiProvider<Api> {
    @Nullable Api get(BlockEntity blockEntity, @NotNull Direction direction);
}
