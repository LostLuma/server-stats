package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.minecraft.entity.Entities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.living.player.PlayerEntity;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements StatProvider {
	@Unique
	private PlayerEntity getPlayer() {
		return (PlayerEntity)(Object) this;
	}

	@Unique
	private PersistentStats server_stats$stats;

	@Shadow
	public ServerPlayNetworkHandler networkHandler;

	@Override
	public @Nullable PersistentStats server_stats$stats() {
		if (this.server_stats$stats == null) {
			this.server_stats$stats = PlayerStatsCache.getInstance().get(this.networkHandler);
		}

		return server_stats$stats;
	}

	@Inject(method = "onKilled", at = @At("HEAD"))
	private void onKilled(Entity entity, CallbackInfo callbackInfo) {
		if (entity != null) {
			String type = Entities.getKey(entity);
			this.getPlayer().increment(RegistryImpl.getKilledByEntityStat(type), 1);
		}

		this.getPlayer().increment(RegistryImpl.DEATHS, 1);
	}
}
