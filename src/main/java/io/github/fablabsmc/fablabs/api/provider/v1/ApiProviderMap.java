package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderApiImpl;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * The building block for creating your own Provider. You should store an instance of this class in a static variable, and only use `getLookup`.
 */
public final class ApiProviderMap<L extends ApiLookup<T, P>, T, P> {
    private final Map<ApiKey<?>, Map<ContextKey<?>, L>> lookups = new Reference2ReferenceOpenHashMap<>();
    private final Supplier<L> lookupConstructor;

    public ApiProviderMap(Supplier<L> lookupConstructor) {
        this.lookupConstructor = lookupConstructor;

        synchronized (ApiProviderApiImpl.LOCK) {
            ApiProviderApiImpl.addProviderMap(this);
        }
    }

    public @NotNull L getLookup(ApiKey<?> key, ContextKey<?> contextKey) {
        synchronized (ApiProviderApiImpl.LOCK) {
            lookups.putIfAbsent(key, new Reference2ReferenceOpenHashMap<>());
            lookups.get(key).putIfAbsent(contextKey, lookupConstructor.get());
            return lookups.get(key).get(contextKey);
        }
    }

    @ApiStatus.Internal
    public void forEachLookup(Consumer<L> function) {
        synchronized (ApiProviderApiImpl.LOCK) {
            for (Map<ContextKey<?>, L> apiLookups : lookups.values()) {
                for (L lookup : apiLookups.values()) {
                    function.accept(lookup);
                }
            }
        }
    }
}
