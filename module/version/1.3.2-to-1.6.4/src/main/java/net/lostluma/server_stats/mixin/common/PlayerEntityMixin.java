package net.lostluma.server_stats.mixin.common;

import net.lostluma.server_stats.impl.ext.player.PersistentStats;
import net.lostluma.server_stats.impl.ext.player.StatProvider;
import net.minecraft.entity.living.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements StatProvider {
	/**
	 * Dummy stat provider implementation, to ensure that
	 * client-side actions such as dropping items does not cause crashes.
	 */
	@Override
	public @Nullable PersistentStats server_stats$stats() {
		return null;
	}
}
