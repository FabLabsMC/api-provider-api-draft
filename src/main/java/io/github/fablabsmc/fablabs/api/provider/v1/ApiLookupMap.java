package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiLookupMapImpl;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * The building block for creating your own Provider. You should store an instance of this interface in a static variable.
 */
public interface ApiLookupMap<L extends ApiLookup<?>> extends Iterable<L> {
    static <L extends ApiLookup<?>> ApiLookupMap<L> create(LookupFactory<L> lookupFactory) {
        return new ApiLookupMapImpl<>(lookupFactory);
    }

    @NotNull L getLookup(Identifier apiId, ContextKey<?> contextKey);

    interface LookupFactory<L> {
        L create(Identifier apiKey, ContextKey<?> contextKey);
    }
}
