package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.lostluma.server_stats.stats.Stats;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "main", at = @At("HEAD"))
    private static void onMain(CallbackInfo callbackInfo) {
        Stats.init();
    }

    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadWorld(CallbackInfo callbackInfo) {
        MinecraftServer server = (MinecraftServer)(Object)this;
        ServerPlayerStats.setWorldDirectory(server.getWorldDirName());
    }
}
