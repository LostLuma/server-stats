package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.World;

@Mixin(World.class)
public class WorldMixin {
	@Inject(method = "saveData", at = @At("TAIL"))
	public void onSave(CallbackInfo callbackInfo) {
		PlayerStatsCache.getInstance().save();
	}
}
