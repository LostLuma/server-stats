package net.lostluma.server_stats.identity.client.mixin;

import net.lostluma.server_stats.impl.client.ClientPlayerStatsImpl;
import net.lostluma.server_stats.impl.ext.common.Identifiable;
import net.lostluma.server_stats.util.Mojang;
import net.minecraft.client.C_8730536;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.util.UUID;

@Mixin(C_8730536.class)
public class SessionMixin implements Identifiable {
	@Shadow
	public String f_0507139;

	@Unique
	private UUID server_stats$identifier;

	@Override
	public @NotNull String server_stats$name() {
		return this.f_0507139;
	}

	@Override
	public @NotNull UUID server_stats$identifier() {
		return this.server_stats$identifier;
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	private void init(CallbackInfo callbackInfo) {
		try {
			this.server_stats$identifier = Mojang.fetchUuid(this.f_0507139);
		} catch (IOException e) {
			throw new RuntimeException("Failed to fetch UUID for " + this.f_0507139);
		}

		ClientPlayerStatsImpl.setSession(this);
	}
}
