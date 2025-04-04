package net.lostluma.server_stats.shared.mixin.client;

import net.lostluma.server_stats.common.duck.DuckSession;
import net.lostluma.server_stats.common.util.Mojang;
import net.minecraft.client.Session;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.UUID;

@Mixin(Session.class)
public class SessionMixin implements DuckSession {
	@Shadow
	public String username;

	@Unique
	private String server_stats$identifier;

	@Override
	public @NotNull String server_stats$identifier() {
		return this.server_stats$identifier;
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		try {
			UUID uuid = Mojang.fetchUuid(this.username);
			this.server_stats$identifier = uuid.toString();
		} catch (IOException e) {
			throw new RuntimeException("Failed to fetch UUID for " + this.username);
		}
	}
}
