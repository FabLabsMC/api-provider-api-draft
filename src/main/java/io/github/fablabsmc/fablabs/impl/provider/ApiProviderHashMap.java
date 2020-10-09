package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderMap;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class ApiProviderHashMap<K, V> implements ApiProviderMap<K, V> {
    private volatile Map<K, V> lookups = new Reference2ReferenceOpenHashMap<>();

    public @Nullable V get(K key) {
        return lookups.get(key);
    }

    public synchronized V putIfAbsent(K key, V provider) {
        // We use a copy-on-write strategy to allow any number of reads to concur with a write
        Map<K, V> lookupsCopy = new Reference2ReferenceOpenHashMap<>(lookups);
        V result = lookupsCopy.putIfAbsent(key, provider);
        lookups = lookupsCopy;
        return result;
    }
}
