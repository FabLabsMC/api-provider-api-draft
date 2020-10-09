package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiKey;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookupMap;
import io.github.fablabsmc.fablabs.api.provider.v1.ContextKey;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;

public class ApiLookupMapImpl<L> implements ApiLookupMap<L> {
    private final Map<ApiKey<?>, Map<ContextKey<?>, L>> lookups = new Reference2ReferenceOpenHashMap<>();
    private final LookupFactory<L> lookupFactory;

    private ApiLookupMapImpl(LookupFactory<L> lookupFactory) {
        this.lookupFactory = lookupFactory;
    }

    public static <L> ApiLookupMap<L> create(LookupFactory<L> lookupFactory) {
        return new ApiLookupMapImpl<>(lookupFactory);
    }

    public synchronized @NotNull L getLookup(ApiKey<?> key, ContextKey<?> contextKey) {
        lookups.putIfAbsent(key, new Reference2ReferenceOpenHashMap<>());
        lookups.get(key).computeIfAbsent(contextKey, ctx -> lookupFactory.create(key, contextKey));
        return lookups.get(key).get(contextKey);
    }

    @NotNull
    @Override
    public synchronized Iterator<L> iterator() {
        return lookups.values().stream().flatMap(apiLookups -> apiLookups.values().stream()).collect(Collectors.toList()).iterator();
    }
}
