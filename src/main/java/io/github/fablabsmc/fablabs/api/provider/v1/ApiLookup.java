package io.github.fablabsmc.fablabs.api.provider.v1;

import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Lookup class.
 */
public interface ApiLookup<C> {
    @NotNull Identifier getApiId();
    @NotNull ContextKey<C> getContextKey();
}
