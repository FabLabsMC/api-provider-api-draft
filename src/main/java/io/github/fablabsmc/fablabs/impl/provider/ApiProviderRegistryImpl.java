/*
 * Copyright (c) 2016, 2017, 2018, 2019 FabricMC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.fablabsmc.fablabs.impl.provider;

import java.util.Map;
import java.util.Objects;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockApiProvider;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockEntityApiProvider;
import io.github.fablabsmc.fablabs.api.provider.v1.ProviderContext;
import io.github.fablabsmc.fablabs.mixin.provider.BlockEntityTypeAccessor;
import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public final class ApiProviderRegistryImpl {
    private static final Map<ApiKey<?>, Map<Block, BlockApiProvider<?>>> blockProviders = new Reference2ObjectOpenHashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    public static <T> @Nullable T getFromBlock(ApiKey<T> key, ProviderContext context, World world, BlockPos pos) {
        if(key != null) {
            Map<Block, BlockApiProvider<?>> providers = blockProviders.get(key);
            if(providers != null) {
                Block block = world.getBlockState(pos).getBlock();
                BlockApiProvider<?> provider = providers.get(block);
                if(provider != null) {
                    // TODO: FIXME: Null context
                    return (T) provider.get(context, world, pos);
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
                BlockApiProvider<T> blockProvider = (context, world, pos) -> {
                    BlockEntity be = world.getBlockEntity(pos);
                    if(be == null) {
                        return null;
                    } else {
                        return provider.get(context, be);
                    }
                };
                registerForBlock(key, blockProvider, blocks);
            }
        }
    }
}
