package io.github.fablabsmc.fablabs.impl.provider;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;

/**
 * Client environment entrypoint to make sure that we detect when the client is done loading.
 */
public class ApiProviderClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> {
            ApiProviderApiImpl.initialize();
        });
    }
}
