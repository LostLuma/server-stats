package net.lostluma.server_stats.shared.common.mixin;

import com.google.gson.JsonObject;
import net.lostluma.server_stats.common.stat.ServerPlayerStats;
import net.lostluma.server_stats.common.stat.ServerStat;
import net.lostluma.server_stats.common.stat.ServerStats;
import net.minecraft.network.packet.ChatMessagePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.entity.living.player.ServerPlayerEntity;
import net.minecraft.stat.Stat;
import net.minecraft.stat.achievement.AchievementStat;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.loader.api.Version;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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

		ServerStat stat = ServerStats.byVanillaId(vanillaStat.id);
		ServerPlayerStats stats = this.getPlayer().server_stats$getStats();

		if (stat == null || stats == null) {
			return;
		}

		if (!this.server_stats$parentEarned(vanillaStat, stats)) {
			return;
		}

		long before = this.getPlayer().server_stats$incrementStat(stat, amount);

		if (before != 0L || !(vanillaStat instanceof AchievementStat)) {
			return;
		}

		String name = this.getPlayer().server_stats$name();
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
	private boolean server_stats$parentEarned(Stat stat, ServerPlayerStats stats) {
		if (!(stat instanceof AchievementStat)) {
			return true;
		}

		AchievementStat parent = ((AchievementStat) stat).parent;

		if (parent == null) {
			return true;
		}

		ServerStat serverStat = ServerStats.byVanillaId(parent.id);
		return serverStat != null && stats.get(serverStat) > 0;
	}

	/**
	 * Whether this Minecraft version supports / required usage of text components.
	 */
	@Unique
	private boolean server_stats$usesComponents() {
		ModContainer container = QuiltLoader.getModContainer("minecraft").get();

		// Snapshot that adds text components
		Version version = Version.of("1.6-alpha.13.21.a");
		return container.metadata().version().compareTo(version) > -1;
	}
}
