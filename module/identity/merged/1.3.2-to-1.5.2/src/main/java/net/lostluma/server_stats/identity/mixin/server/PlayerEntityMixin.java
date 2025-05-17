package net.lostluma.server_stats.identity.mixin.server;

import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.minecraft.entity.living.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.UUID;

/**
 * Internal {@code Identifiable} implementation to allow reading
 * these values in {@code ServerPlayNetworkHandler.<init>} again
 */
@Mixin(PlayerEntity.class)
public class PlayerEntityMixin implements Identifiable {
	@Shadow
	public String name;

	@Unique
	private UUID server_stats$identifier;

	@Override
	public @NotNull String server_stats$name() {
		return this.name;
	}

	@Override
	public @NotNull UUID server_stats$identifier() {
		return this.server_stats$identifier;
	}

	@Override
	public void server_stats$setIdentifier(@NotNull UUID identifier) {
		this.server_stats$identifier = identifier;
	}
}
