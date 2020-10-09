package io.github.fablabsmc.fablabs.api.provider.v1.block;

import net.minecraft.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@FunctionalInterface
public interface BlockEntityApiProvider<T, C> {
    @Nullable T get(BlockEntity blockEntity, @NotNull C context);
}
