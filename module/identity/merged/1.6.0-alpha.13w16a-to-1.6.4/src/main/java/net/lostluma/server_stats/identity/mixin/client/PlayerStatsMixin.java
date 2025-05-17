package net.lostluma.server_stats.identity.mixin.client;

import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.minecraft.client.Minecraft;
import net.minecraft.stat.PlayerStats;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;

import java.util.UUID;

@Mixin(PlayerStats.class)
public class PlayerStatsMixin implements Identifiable {
	@Override
	public @NotNull String server_stats$name() {
		return Minecraft.getInstance().getSession().server_stats$name();
	}

	@Override
	public @NotNull UUID server_stats$identifier() {
		return Minecraft.getInstance().getSession().server_stats$identifier();
	}
}
