package net.lostluma.server_stats.statistic.vanilla.client.mixin;

import net.lostluma.server_stats.statistic.vanilla.client.Vanilla;
import net.lostluma.server_stats.statistic.vanilla.registry.Statistics;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public World world;

	@Shadow
	public InputPlayerEntity player;

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		Vanilla.minecraft = (Minecraft)(Object) this;
	}

	@Inject(
		method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/living/player/PlayerEntity;)V",
		at = @At("HEAD")
	)
	private void setWorld(World world, String string, PlayerEntity playerEntity, CallbackInfo callbackInfo) {
		if (world == null && this.world != null) {
			this.player.increment(Statistics.GAMES_LEFT);
		}
	}
}
