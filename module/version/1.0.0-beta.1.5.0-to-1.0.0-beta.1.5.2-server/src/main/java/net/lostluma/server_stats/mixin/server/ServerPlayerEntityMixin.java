package net.lostluma.server_stats.mixin.server;

import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements StatProvider {
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
}
