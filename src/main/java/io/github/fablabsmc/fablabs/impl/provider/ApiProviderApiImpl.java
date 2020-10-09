package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.AbstractApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores synchronization for the api providers.
 */
public class ApiProviderApiImpl {
    public static final Object LOCK = new Object();
    private static boolean initialized = false;
    private static final List<ApiProviderMap<? extends AbstractApiLookup, ?, ?>> providerMaps = new ArrayList<>();

    public static boolean isInitialized() {
        synchronized (LOCK) {
            return initialized;
        }
    }

    static void initialize() {
        synchronized (LOCK) {
            // Here, we need to initialize the various lookups
            for(ApiProviderMap<? extends AbstractApiLookup, ?, ?> providerMap : providerMaps) {
                providerMap.getLookups().forEach(AbstractApiLookup::initialize);
            }
            initialized = true;
        }
    }

    public static void addProviderMap(ApiProviderMap<? extends AbstractApiLookup, ?, ?> map) {
        synchronized (LOCK) {
            if (!initialized) {
                providerMaps.add(map);
            }
        }
    }
}
