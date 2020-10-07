package io.github.fablabsmc.fablabs.test.provider.mixin;

import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ClientWorld.class)
public interface ClientWorldMixin {
    @Accessor
    WorldRenderer getWorldRenderer();
}
