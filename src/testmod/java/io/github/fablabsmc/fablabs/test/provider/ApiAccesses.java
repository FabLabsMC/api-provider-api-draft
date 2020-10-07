package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import alexiil.mc.lib.attributes.fluid.impl.EmptyFluidTransferable;
import io.github.fablabsmc.fablabs.api.provider.v1.BlockApiProviderAccess;
import net.minecraft.util.Identifier;

/**
 * PROVIDER-API
 * ProviderAccess for FluidInsertable
 */
public class ApiAccesses {
    public static final BlockApiProviderAccess<FluidInsertableProvider, FluidInsertable> FLUID_INSERTABLE_BLOCK_ACCESS =
            BlockApiProviderAccess.registerAccess(new Identifier("testmod:fluid_insertable"), FluidInsertable.class, side -> EmptyFluidTransferable.NULL);

}
