package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadWorld(CallbackInfo callbackInfo) {
        MinecraftServer server = (MinecraftServer)(Object)this;
        ServerPlayerStats.setWorldDirectory("saves/" + server.getWorldDirName());
    }
}
