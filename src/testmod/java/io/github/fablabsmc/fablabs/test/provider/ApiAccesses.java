package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiAccess;
import net.minecraft.util.Identifier;

/**
 * PROVIDER-API
 * ProviderAccess for FluidInsertable
 */
public class ApiAccesses {
    public static final ApiAccess<FluidInsertable> FLUID_INSERTABLE = new ApiAccess<FluidInsertable>() {
        @Override
        public Identifier getId() {
            return null; // not used (yet)
        }
    };
}
