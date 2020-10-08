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
public interface ApiProviderRegistry {
    ApiProviderRegistry INSTANCE = new ApiProviderRegistryImpl();

    @Nullable <Api> Api getFromBlock(ApiAccess<Api> apiAccess, World world, BlockPos pos, @NotNull Direction direction);

    <Api> void registerForBlock(ApiAccess<Api> apiAccess, BlockApiProvider<Api> provider, Block... blocks);

    <Api> void registerForBlockEntity(ApiAccess<Api> apiAccess, BlockEntityApiProvider<Api> provider, BlockEntityType<?>... types);
}
