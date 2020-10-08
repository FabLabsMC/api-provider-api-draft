package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockApiProvider;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockEntityApiProvider;
import io.github.fablabsmc.fablabs.mixin.provider.BlockEntityTypeAccessor;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public final class ApiProviderRegistryImpl {
    private static final Map<ApiKey<?>, Map<Block, BlockApiProvider<?>>> blockProviders = new Reference2ObjectOpenHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T> @Nullable T getFromBlock(ApiKey<T> key, World world, BlockPos pos, @NotNull Direction direction) {
        if(key != null) {
            Map<Block, BlockApiProvider<?>> providers = blockProviders.get(key);
            if(providers != null) {
                Block block = world.getBlockState(pos).getBlock();
                BlockApiProvider<?> provider = providers.get(block);
                if(provider != null) {
                    return (T) provider.get(world, pos, direction);
                }
            }
        }
        return null;
    }

    public static <T> void registerForBlock(ApiKey<T> key, BlockApiProvider<T> provider, Block... blocks) {
        if(key != null) {
            Objects.requireNonNull(provider, "encountered null BlockApiProvider");

            for(final Block block : blocks) {
                Objects.requireNonNull(block, "encountered null block while registering a block API provider mapping");

                blockProviders.putIfAbsent(key, new Reference2ReferenceOpenHashMap<>());

                if(blockProviders.get(key).putIfAbsent(block, provider) != null) {
                    LOGGER.warn("Encountered duplicate API provider registration for block: " + Registry.BLOCK.getId(block));
                }
            }
        }
    }

    public static <T> void registerForBlockEntity(ApiKey<T> key, BlockEntityApiProvider<T> provider, BlockEntityType<?>... types) {
        if(key != null) {
            Objects.requireNonNull(provider, "encountered null BlockEntityApiProvider");

            for(final BlockEntityType<?> bet : types) {
                Objects.requireNonNull(bet, "encountered null block entity type while registering a block entity API provider mapping");

                Block[] blocks = ((BlockEntityTypeAccessor) bet).getBlocks().toArray(new Block[0]);
                BlockApiProvider<T> blockProvider = (world, pos, direction) -> {
                    BlockEntity be = world.getBlockEntity(pos);
                    if(be == null) {
                        return null;
                    } else {
                        return provider.get(be, direction);
                    }
                };
                registerForBlock(key, blockProvider, blocks);
            }
        }
    }
}
