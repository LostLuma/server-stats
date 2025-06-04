package net.lostluma.server_stats.lifecycle.mixin;

import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public InputPlayerEntity player;

	@Inject(method = "startGame", at = @At("HEAD"))
	private void startGame(String worldDir, String worldName, WorldSettings worldSettings, CallbackInfo callbackInfo) {
		PlayerStatsCache.newInstance(String.format("saves/%s", worldDir));
	}

	@Inject(
		method = "setWorld(Lnet/minecraft/world/World;Ljava/lang/String;Lnet/minecraft/entity/living/player/PlayerEntity;)V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/client/Minecraft;world:Lnet/minecraft/world/World;",
			opcode = Opcodes.PUTFIELD
		)
	)
	private void assignWorld(World world, String message, PlayerEntity player, CallbackInfo callbackInfo) {
		if (world == null && this.player != null) {
			PlayerStatsCache.closeInstance();
		}
	}
}
