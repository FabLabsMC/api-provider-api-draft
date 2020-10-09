package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.AbstractApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.stream.Collectors;

public class ApiProviderMapImpl<L extends AbstractApiLookup<?, ?, K, P>, K, P> implements ApiProviderMap<L, K, P> {
    private final Map<ApiKey<?>, Map<ContextKey<?>, L>> lookups = new Reference2ReferenceOpenHashMap<>();
    private final LookupConstructor<L, K, P> lookupConstructor;

    private ApiProviderMapImpl(LookupConstructor<L, K, P> lookupConstructor) {
        this.lookupConstructor = lookupConstructor;
    }

    public static <L extends AbstractApiLookup<?, ?, K, P>, K, P> ApiProviderMap<L, K, P> create(LookupConstructor<L, K, P> lookupConstructor) {
        return new ApiProviderMapImpl<>(lookupConstructor);
    }

    public synchronized @NotNull L getLookup(ApiKey<?> key, ContextKey<?> contextKey) {
        lookups.putIfAbsent(key, new Reference2ReferenceOpenHashMap<>());
        lookups.get(key).computeIfAbsent(contextKey, ctx -> lookupConstructor.create(key, contextKey));
        return lookups.get(key).get(contextKey);
    }

    @Override
    public synchronized Iterable<L> getLookups() {
        return lookups.values().stream().flatMap(apiLookups -> apiLookups.values().stream()).collect(Collectors.toList());
    }
}
