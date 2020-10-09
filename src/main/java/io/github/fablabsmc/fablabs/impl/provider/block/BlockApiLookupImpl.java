package io.github.fablabsmc.fablabs.impl.provider.block;

import io.github.fablabsmc.fablabs.api.provider.v1.AbstractApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiProvider;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockEntityApiProvider;
import io.github.fablabsmc.fablabs.mixin.provider.BlockEntityTypeAccessor;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class BlockApiLookupImpl<T, C> extends AbstractApiLookup<T, C, Block, BlockApiProvider<?, ?>> implements BlockApiLookup<T, C> {
    BlockApiLookupImpl(ApiKey<T> apiKey, ContextKey<C> contextKey) {
        super(apiKey, contextKey);
    }

    @Nullable
    @Override
    public T get(World world, BlockPos pos, C context) {
        BlockApiProvider<T, C> provider = (BlockApiProvider<T, C>) get(world.getBlockState(pos).getBlock());
        if(provider != null) {
            return provider.get(world, pos, context);
        } else {
            return null;
        }
    }

    @Override
    public void registerForBlocks(BlockApiProvider<T, C> provider, Block... blocks) {
        Objects.requireNonNull(provider, "encountered null BlockApiProvider");

        for(final Block block : blocks) {
            Objects.requireNonNull(block, "encountered null block while registering a block API provider mapping");

            putIfAbsent(block, provider);
        }
    }

    @Override
    public void registerForBlockEntities(BlockEntityApiProvider<T, C> provider, BlockEntityType<?>... blockEntityTypes) {
        Objects.requireNonNull(provider, "encountered null BlockEntityApiProvider");

        for(final BlockEntityType<?> bet : blockEntityTypes) {
            Objects.requireNonNull(bet, "encountered null block entity type while registering a block entity API provider mapping");

            Block[] blocks = ((BlockEntityTypeAccessor) bet).getBlocks().toArray(new Block[0]);
            BlockApiProvider<T, C> blockProvider = (world, pos, context) -> {
                BlockEntity be = world.getBlockEntity(pos);
                if(be == null) {
                    return null;
                } else {
                    return provider.get(be, context);
                }
            };
            registerForBlocks(blockProvider, blocks);
        }
    }
}
