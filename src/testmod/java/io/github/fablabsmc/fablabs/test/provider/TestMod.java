package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
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
		ApiAccesses.FLUID_INSERTABLE_BLOCK_ACCESS.registerProviderForBlockEntity(be -> new FluidInsertableProvider() {
			@Override
			public FluidInsertable getFluidInsertable(Direction side) {
				return side == Direction.UP && be instanceof TankBlockEntity ? (TankBlockEntity) be : getApi();
			}
		}, TANK_BLOCK_ENTITY_TYPE);

		System.out.println("TestMod setup ok!");
	}
}
