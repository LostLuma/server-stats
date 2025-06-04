package net.lostluma.server_stats.mixin.client;

import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public InputPlayerEntity player;

	@Inject(method = "m_4977780", at = @At("TAIL"))
	private void changeDimension(CallbackInfo callbackInfo) {
		this.player.server_stats$save();
	}

	@Inject(
		method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/living/player/PlayerEntity;)V",
		at = @At("HEAD")
	)
	private void setWorld(World world, String message, PlayerEntity player, CallbackInfo callbackInfo) {
		if (world == null && this.player != null) {
			this.player.incrementStat(Stats.GAMES_LEFT);
		}
	}
}
