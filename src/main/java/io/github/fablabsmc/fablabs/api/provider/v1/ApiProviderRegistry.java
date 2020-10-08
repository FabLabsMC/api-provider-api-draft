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
public final class ApiProviderRegistry {
    public static @Nullable <T> T getFromBlock(ApiKey<T> key, World world, BlockPos pos) {
        return getFromBlock(key, ProviderContext.empty(), world, pos);
    }

    public static @Nullable <T> T getFromBlock(ApiKey<T> key, ProviderContext context, World world, BlockPos pos) {
        return ApiProviderRegistryImpl.getFromBlock(key, context, world, pos);
    }

    public static <T> void registerForBlock(ApiKey<T> key, BlockApiProvider<T> provider, Block... blocks) {
        ApiProviderRegistryImpl.registerForBlock(key, provider, blocks);
    }

    public static <T> void registerForBlockEntity(ApiKey<T> key, BlockEntityApiProvider<T> provider, BlockEntityType<?>... types) {
        ApiProviderRegistryImpl.registerForBlockEntity(key, provider, types);
    }
}
