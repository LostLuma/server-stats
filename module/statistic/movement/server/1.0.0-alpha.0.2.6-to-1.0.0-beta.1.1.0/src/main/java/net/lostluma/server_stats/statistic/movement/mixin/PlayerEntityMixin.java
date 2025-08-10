package net.lostluma.server_stats.statistic.movement.mixin;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;
import net.minecraft.entity.living.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@Unique
	private long oldValue;

	@Unique
	private static final ServerStatistic CM_CROUCHED = ServerStatistic.of("minecraft", "crouchOneCm").build();

	/**
     * If the player is sneaking, increment the sneak statistic.
	 * <br>
	 * The walk statistic is decremented as the vanilla code does not account for sneaking.
	 */
	@Inject(method = "tickAI", at = @At("RETURN"), order = 2000)
	private void tick(CallbackInfo callbackInfo) {
		PlayerEntity self = (PlayerEntity)(Object) this;
		long value = self.get(this.getWalkCmStatistic());

		if (value == this.oldValue || !self.m_0315596()) {
			this.oldValue = value;
		} else {
			long difference = value - this.oldValue;
			self.increment(CM_CROUCHED, difference);
			self.increment(this.getWalkCmStatistic(), -difference);
		}
	}

	@Unique
	private ServerStatistic getWalkCmStatistic() {
		Optional<ServerStatistic> statistic = ServerStatistic.get("minecraft", "walkOneCm");

		if (statistic.isPresent()) {
			return statistic.get();
		} else {
			throw new RuntimeException("Statistic \"minecraft.walkOneCm\" not registered!");
		}
	}
}
