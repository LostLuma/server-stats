package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(World.class)
public class WorldMixin {
	@Inject(method = "saveData", at = @At("TAIL"))
	public void onSave(CallbackInfo callbackInfo) {
		PlayerStatsCache.getInstance().save();
	}
}
