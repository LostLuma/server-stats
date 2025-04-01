package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.datafix.ServerPlayerStatFix;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadWorld(CallbackInfo callbackInfo) throws IOException {
        MinecraftServer server = (MinecraftServer)(Object)this;
        ServerPlayerStatFix.upgradePlayerStats(server.getWorldDirName());
    }
}
