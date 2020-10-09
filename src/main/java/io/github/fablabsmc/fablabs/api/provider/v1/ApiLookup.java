package io.github.fablabsmc.fablabs.api.provider.v1;

import io.github.fablabsmc.fablabs.impl.provider.ApiProviderApiImpl;
import it.unimi.dsi.fastutil.objects.Reference2ReferenceOpenHashMap;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * The building block for creating your own lookup class. You should extend this and only use `get` and `putIfAbsent`.
 */
public abstract class ApiLookup<T, P> {
    private final Map<T, P> lookups = new Reference2ReferenceOpenHashMap<>();
    private volatile boolean initialized = false;
    private static final Logger LOGGER = LogManager.getLogger();

    protected ApiLookup() {
        synchronized (ApiProviderApiImpl.LOCK) {
            if(ApiProviderApiImpl.isInitialized()) {
                initialize();
            }
        }
    }

    protected @Nullable P get(T key) {
        if(!initialized) {
            throw new RuntimeException("Attempted to get() from an uninitialized ApiLookup");
        }
        return lookups.get(key);
    }

    protected void putIfAbsent(T key, P provider) {
        synchronized (ApiProviderApiImpl.LOCK) {
            if(initialized) {
                throw new RuntimeException("Attempted to register a provider in an initialized ApiLookup");
            }
            if(lookups.putIfAbsent(key, provider) != null) {
                LOGGER.warn("Attempted to overwrite a provider in an ApiLookup"); // TODO: better error message
            }
        }
    }

    @ApiStatus.Internal
    public void initialize() {
        synchronized (ApiProviderApiImpl.LOCK) {
            // TODO: Perform actual initialization for more efficient access
            initialized = true;
        }
    }
}
