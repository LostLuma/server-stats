package net.lostluma.server_stats.impl.statistic;

import net.lostluma.server_stats.api.statistic.ServerAchievement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerAchievementImpl extends ServerStatisticImpl implements ServerAchievement {
	private final ServerAchievement parent;

	public ServerAchievementImpl(int vanillaId, @NotNull String identifier, @Nullable ServerAchievement parent) {
		this(vanillaId, "minecraft", identifier, parent);
	}

	protected ServerAchievementImpl(@NotNull String namespace, @NotNull String identifier, @Nullable ServerAchievement parent) {
		this(-1, namespace, identifier, parent);
	}

	protected ServerAchievementImpl(int vanillaId, @NotNull String namespace, @NotNull String identifier, @Nullable ServerAchievement parent) {
		super(vanillaId, namespace, identifier);

		this.parent = parent;
	}

	public @NotNull String key() {
		if (this.namespace().equals("minecraft")) {
			return "achievement." + this.identifier();
		} else {
			return "achievement." + this.namespace() + "." + this.identifier();
		}
	}

	@Override
	public @Nullable ServerAchievement parent() {
		return this.parent;
	}

	public static class Builder implements ServerAchievement.Builder {
		private final @NotNull String namespace;
		private final @NotNull String identifier;

		private @Nullable ServerAchievement parent;

		public Builder(@NotNull String namespace, @NotNull String identifier) {
			this.namespace = namespace;
			this.identifier = identifier;

			this.parent = null;
		}

		@Override
		public @NotNull ServerAchievement build() {
			return new ServerAchievementImpl(this.namespace, this.identifier, this.parent);
		}

		@Override
		public ServerAchievement.@NotNull Builder parent(@NotNull ServerAchievement parent) {
			this.parent = parent;
			return this;
		}
	}
}
