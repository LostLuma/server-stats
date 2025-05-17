package net.lostluma.server_stats.identity.client.mixin;

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
		return Minecraft.INSTANCE.session.server_stats$name();
	}

	@Override
	public @NotNull UUID server_stats$identifier() {
		return Minecraft.INSTANCE.session.server_stats$identifier();
	}
}
