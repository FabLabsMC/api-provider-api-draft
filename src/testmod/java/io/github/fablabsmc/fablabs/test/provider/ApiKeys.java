package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.block.BlockApiLookupRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

/**
 * PROVIDER-API
 * ProviderAccess for FluidInsertable
 */
public class ApiKeys {
    public static final Identifier FLUID_INSERTABLE = new Identifier("lba", "fluid_insertable");
    public static final ContextKey<Direction> SIDED = ContextKey.create(Direction.class, new Identifier("c", "sided"));
    public static final BlockApiLookup<FluidInsertable, @NotNull Direction> SIDED_FLUID_INSERTABLE = BlockApiLookupRegistry.getLookup(FLUID_INSERTABLE, SIDED);
}
