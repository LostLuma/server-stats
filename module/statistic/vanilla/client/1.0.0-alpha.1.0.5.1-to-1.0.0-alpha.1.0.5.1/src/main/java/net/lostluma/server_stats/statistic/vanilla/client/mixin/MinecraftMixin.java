package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.client.Vanilla;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.unmapped.C_5664496;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(C_5664496.class)
public class MinecraftMixin {
	@Shadow
	public World f_5854988;

	@Shadow
	public InputPlayerEntity f_6058446;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Vanilla.minecraft = (C_5664496)(Object) this;
	}

	@Inject(method = "m_9890357", at = @At("HEAD"))
	private void setWorld(World world, String string, CallbackInfo callbackInfo) {
		if (world == null && this.f_5854988 != null) {
			this.f_6058446.increment(Statistics.GAMES_LEFT);
		}
	}
}
