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

package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import io.github.fablabsmc.fablabs.api.provider.v1.AccessContextKeys;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderRegistry;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockEntityApiProvider;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;

/**
 * PROVIDER-API
 * This mod provides a tank that will send 1 bucket per second to any FluidInsertable placed directly under it.
 * The FluidInsertable is exposed and queried using the experimental fabric provider api.
 * Search for comments starting with PROVIDER-API in the files to see how the api is being used.
 */
public class TestMod implements ModInitializer {

    public static final TankBlock TANK_BLOCK = new TankBlock(FabricBlockSettings.of(Material.METAL));
    public static final BlockItem TANK_ITEM = new BlockItem(TANK_BLOCK, new Item.Settings().group(ItemGroup.MISC));
    public static BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY_TYPE;

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier("api-provider-testmod:tank"), TANK_BLOCK);
        Registry.register(Registry.ITEM, new Identifier("api-provider-testmod:tank"), TANK_ITEM);
        TANK_BLOCK_ENTITY_TYPE = BlockEntityType.Builder.create(TankBlockEntity::new, TANK_BLOCK).build(null);
        Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier("api-provider-testmod:tank"), TANK_BLOCK_ENTITY_TYPE);

        /*
         * PROVIDER-API
         * Exposing FluidInsertable for the TankBlockEntity (it implements FluidInsertable).
         */
        ApiProviderRegistry.registerForBlockEntity(ApiKeys.FLUID_INSERTABLE, (context, be) -> {
        	if (context.get(AccessContextKeys.DIRECTION) == Direction.UP) {
        		if (be instanceof TankBlockEntity) {
        			return (TankBlockEntity) be;
        		}
        	}

        	return null;
        }, TANK_BLOCK_ENTITY_TYPE);

        System.out.println("TestMod setup ok!");
    }
}
