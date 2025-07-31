package net.lostluma.server_stats.provider.client.mixin;

import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.lostluma.server_stats.provider.client.Provider;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({ InputPlayerEntity.class })
public class InputPlayerEntityMixin implements StatProvider {
	@Unique
	private PersistentStats server_stats$stats;

	@Override
	public  @Nullable PersistentStats server_stats$stats() {
		/*
		Multiplayer is not available in these versions ...
		PlayerEntity player = (PlayerEntity)(Object) this;

		if (player.world.isMultiplayer) {
			return null;
		}
		 */

		if (this.server_stats$stats == null) {
			this.server_stats$stats = PlayerStatsCache.getInstance().get(Provider.client.f_2424468);
			ClientPlayerStatsImpl.getPlayerStats().persist(this.server_stats$stats.server_stats$values(), true);
		}

		return server_stats$stats;
	}
}
