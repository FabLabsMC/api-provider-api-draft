package io.github.fablabsmc.fablabs.test.provider.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;

public class TestModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModelLoadingRegistry.INSTANCE.registerResourceProvider(rm -> new TestModelProvider());

        System.out.println("TestMod client setup ok!");
    }
}
