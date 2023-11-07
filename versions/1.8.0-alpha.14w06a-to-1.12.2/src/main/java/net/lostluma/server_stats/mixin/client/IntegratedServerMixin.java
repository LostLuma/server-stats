package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.datafix.ServerPlayerStatFix;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {
    @Inject(method = "loadWorld", at = @At("HEAD"))
    private void loadWorld(CallbackInfo callbackInfo) throws IOException {
        MinecraftServer server = (MinecraftServer)(Object)this;
        ServerPlayerStatFix.upgradePlayerStats("saves/" + server.getWorldDirName());
    }
}
