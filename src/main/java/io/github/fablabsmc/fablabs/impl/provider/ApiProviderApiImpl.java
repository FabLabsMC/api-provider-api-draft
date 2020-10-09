package io.github.fablabsmc.fablabs.impl.provider;

import io.github.fablabsmc.fablabs.api.provider.v1.ApiLookup;
import io.github.fablabsmc.fablabs.api.provider.v1.ApiProviderMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Stores synchronization for the api providers.
 */
public class ApiProviderApiImpl {
    public static final Object LOCK = new Object();
    private static boolean initialized = false;
    private static final List<ApiProviderMap<? extends ApiLookup, ?, ?>> providerMaps = new ArrayList<>();

    public static boolean isInitialized() {
        synchronized (LOCK) {
            return initialized;
        }
    }

    static void initialize() {
        synchronized (LOCK) {
            // Here, we need to initialize the various lookups
            for(ApiProviderMap<? extends ApiLookup, ?, ?> providerMap : providerMaps) {
                providerMap.forEachLookup(ApiLookup::initialize);
            }
            initialized = true;
        }
    }

    public static void addProviderMap(ApiProviderMap<? extends ApiLookup, ?, ?> map) {
        if(!initialized) {
            providerMaps.add(map);
        }
    }
}
