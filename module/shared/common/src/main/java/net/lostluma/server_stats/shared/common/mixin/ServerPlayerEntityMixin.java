package net.lostluma.server_stats.shared.common.mixin;

import com.google.gson.JsonObject;
import net.lostluma.server_stats.api.statistic.ServerAchievement;
import net.lostluma.server_stats.api.statistic.ServerStatistic;
import net.lostluma.server_stats.impl.statistic.RegistryImpl;
import net.lostluma.server_stats.util.platform.Platform;
import net.lostluma.server_stats.util.platform.Version;
import net.minecraft.network.packet.ChatMessagePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.achievement.AchievementStat;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin {
	@Shadow
	public MinecraftServer server;

	@Unique
	private ServerPlayerEntity getPlayer() {
		return (ServerPlayerEntity)(Object) this;
	}

	/**
	 * Count statistics server-side and announce newly-earned achievements.
	 */
	@Inject(method = "incrementStat", at = @At("HEAD"))
	private void incrementStat(Stat vanillaStat, int amount, CallbackInfo callbackInfo) {
		if (vanillaStat == null) {
			return;
		}

		ServerStatistic stat = RegistryImpl.byVanillaId(vanillaStat.id);

		if (stat == null) {
			return;
		}

		if (!this.server_stats$parentEarned(stat)) {
			return;
		}

		long before = this.getPlayer().increment(stat, amount);

		if (before != 0L || !(vanillaStat instanceof AchievementStat)) {
			return;
		}

		String name = this.getPlayer().networkHandler.server_stats$name();
		String message = "§c" + name + "§r has earned the achievement §a" + vanillaStat + "§r";

		if (this.server_stats$usesComponents()) {
			// The chat message packet still accepts strings, however
			// the parser code does not account for this and crashes.
			JsonObject root = new JsonObject();
			root.addProperty("text", message);

			message = root.toString();
		}

		this.server.playerManager.sendPacket(new ChatMessagePacket(message));
	}

	/**
	 * Whether the parent achievement has been earned, if an achievement is passed.
	 */
	@Unique
	private boolean server_stats$parentEarned(ServerStatistic stat) {
		if (!(stat instanceof ServerAchievement)) {
			return true;
		} else {
			Optional<ServerAchievement> parent = ((ServerAchievement) stat).parent();
			return !parent.isPresent() || this.getPlayer().isUnlocked(parent.get());
		}
	}

	/**
	 * Whether this Minecraft version supports / required usage of text components.
	 */
	@Unique
	private boolean server_stats$usesComponents() {
		// Snapshot that adds text components
		Version version = Version.of("1.6-alpha.13.21.a");
		return Platform.getModVersion("minecraft").compareTo(version) > -1;
	}
}
