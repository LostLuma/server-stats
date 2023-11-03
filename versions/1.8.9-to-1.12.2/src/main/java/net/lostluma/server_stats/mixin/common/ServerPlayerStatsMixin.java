package net.lostluma.server_stats.mixin.common;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.lostluma.server_stats.datafix.ServerPlayerStatFix;
import net.minecraft.server.stat.ServerPlayerStats;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.File;
import java.io.IOException;

@Mixin(ServerPlayerStats.class)
public class ServerPlayerStatsMixin {
    @Shadow
    @Final
    private File file;

    /**
     * Fix old statistic key names before Minecraft discards them while loading the player's data.
     */
    @ModifyVariable(method = "deserialize", at = @At("STORE"), ordinal = 0)
    private JsonObject getAsJsonObject(JsonObject data) throws IOException {
        return ServerPlayerStatFix.upgradePlayerStats(this.file, data);
    }
}
