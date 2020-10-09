package io.github.fablabsmc.fablabs.impl.provider;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;

/**
 * Server environment entrypoint to make sure that we detect when the server is done loading.
 */
public class ApiProviderDedicatedServer implements DedicatedServerModInitializer {
    @Override
    public void onInitializeServer() {
        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            ApiProviderApiImpl.initialize();
        });
    }
}
