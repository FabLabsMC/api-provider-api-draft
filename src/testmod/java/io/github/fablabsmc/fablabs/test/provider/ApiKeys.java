package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

/**
 * PROVIDER-API
 * ProviderAccess for FluidInsertable
 */
public class ApiKeys {
    public static final ApiKey<FluidInsertable> FLUID_INSERTABLE = ApiKey.create(FluidInsertable.class, new Identifier("lba", "fluid_insertable"));
    public static final ContextKey<Direction> SIDED = ContextKey.create(Direction.class, new Identifier("c", "sided"));
}
