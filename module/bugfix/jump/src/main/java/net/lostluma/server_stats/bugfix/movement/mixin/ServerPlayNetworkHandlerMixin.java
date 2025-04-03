package net.lostluma.server_stats.bugfix.movement.mixin;

import net.minecraft.network.packet.PlayerMovePacket;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import net.minecraft.stat.Stats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin {
	@Shadow
	private ServerPlayerEntity player;

	@Unique
	private boolean server_stats$onGround = true;

	@Unique
	private double server_stats$y = Double.MAX_VALUE;

	@Inject(method = "handlePlayerMove", at = @At(value = "CONSTANT", args = "floatValue=0.0625F", ordinal = 0))
	public void incrementJumpStat(PlayerMovePacket packet, CallbackInfo callbackInfo) {
		double y = packet.hasPos ? packet.minY :  this.server_stats$y;

		if (this.server_stats$onGround && !packet.onGround && y > this.server_stats$y) {
			this.player.incrementStat(Stats.JUMPS, 1);
		}

		this.server_stats$onGround = packet.onGround;
		this.server_stats$y = y;
	}
}
