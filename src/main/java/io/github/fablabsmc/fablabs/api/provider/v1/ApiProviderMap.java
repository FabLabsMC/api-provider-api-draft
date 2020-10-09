package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderHashMap;
import org.jetbrains.annotations.Nullable;

/**
 * The building block for creating your own Lookup. You should store an instance of this interface in every instance of the Lookup.
 * This map allows very fast lock-free concurrent reads, and uses a copy-on-write strategy for writes. This means in particular that writes are very expensive.
 * Note that keys are compared by reference (==) and not using .equals!
 */
public interface ApiProviderMap<K, V> {
    @Nullable V get(K key);
    V putIfAbsent(K key, V provider);

    static <K, V> ApiProviderMap<K, V> create() {
        return new ApiProviderHashMap<>();
    }
}
