package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;

/**
 * Unique reference to an API.
 * Never implement this! TODO: provide a way to instantiate and get this
 */
public interface ApiAccess<Api> {
    Identifier getId();
}
