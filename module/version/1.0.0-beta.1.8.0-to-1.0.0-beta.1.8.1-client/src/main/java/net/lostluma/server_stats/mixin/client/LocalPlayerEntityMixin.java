package net.lostluma.server_stats.mixin.client;

import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.lostluma.server_stats.impl.server.PlayerStatsCache;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.living.player.InputPlayerEntity;
import net.minecraft.client.entity.living.player.LocalPlayerEntity;
import net.minecraft.entity.living.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({InputPlayerEntity.class, LocalPlayerEntity.class})
public class LocalPlayerEntityMixin implements StatProvider {
	@Unique
	private PersistentStats server_stats$stats;

	@Override
	public  @Nullable PersistentStats server_stats$stats() {
		PlayerEntity player = (PlayerEntity)(Object) this;

		if (player.world.isMultiplayer) {
			return null;
		}

		if (this.server_stats$stats == null) {
			this.server_stats$stats = PlayerStatsCache.getInstance().get(Minecraft.INSTANCE.session);
			ClientPlayerStatsImpl.getPlayerStats().persist(this.server_stats$stats.server_stats$values(), true);
		}

		return server_stats$stats;
	}

}
