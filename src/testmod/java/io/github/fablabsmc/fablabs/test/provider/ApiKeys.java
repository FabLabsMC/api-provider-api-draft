package io.github.fablabsmc.fablabs.test.provider;

import alexiil.mc.lib.attributes.fluid.FluidInsertable;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;

/**
 * PROVIDER-API
 * ProviderAccess for FluidInsertable
 */
public class ApiKeys {
    public static final ApiKey<FluidInsertable> FLUID_INSERTABLE = ApiKey.create(FluidInsertable.class);
}
