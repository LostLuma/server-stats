package net.lostluma.server_stats.identity.server.mixin;

import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.server.network.handler.ServerPlayNetworkHandler;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.UUID;

@Mixin(ServerPlayNetworkHandler.class)
public class ServerPlayNetworkHandlerMixin implements Identifiable {
	@Shadow
	private ServerPlayerEntity player;

	@Unique
	private UUID server_stats$identifier;

	@Override
	public @NotNull String server_stats$name() {
		return this.player.server_stats$name();
	}

	@Override
	public @NotNull UUID server_stats$identifier() {
		return this.server_stats$identifier;
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		// Copy identifier from login player to persistent network handler
		// This way players that respawn or are otherwise recreated do not
		// Lose their stored identifiers, as network handlers stay around.
		this.server_stats$identifier = this.player.server_stats$identifier();
	}
}
