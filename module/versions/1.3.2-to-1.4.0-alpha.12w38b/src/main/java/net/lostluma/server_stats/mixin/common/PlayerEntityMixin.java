package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.common.duck.DuckPlayer;
import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.entity.Entities;
import net.minecraft.entity.living.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements DuckPlayer {
	@Shadow
	public String name;

	@Unique
	private String server_stats$identifier;

	@Unique
	private ServerPlayerStats server_stats$serverPlayerStats = null;

	@Unique
	private PlayerEntity player() {
		return (PlayerEntity)(Object) this;
	}

	@Override
	public @NotNull String server_stats$name() {
		return this.name;
	}

	@Override
	public @NotNull String server_stats$identifier() {
		return this.server_stats$identifier;
	}

	@Override
	public void server_stats$setIdentifier(@NotNull String identifier) {
		this.server_stats$identifier = identifier;
	}

	@Override
	public @Nullable ServerPlayerStats server_stats$getStats() {
		PlayerEntity player = this.player();

		if (player.world.isClient) {
			return null;
		}

		if (this.server_stats$serverPlayerStats == null) {
			this.server_stats$serverPlayerStats = new ServerPlayerStats(player);
		}

		return this.server_stats$serverPlayerStats;
	}

	@Inject(method = "onKill", at = @At("HEAD"))
	private void onKill(LivingEntity entity, CallbackInfo callbackInfo) {
		String type = Entities.getKey(entity);
		this.player().server_stats$incrementStat(ServerStats.getEntityKillStat(type), 1);
	}
}
