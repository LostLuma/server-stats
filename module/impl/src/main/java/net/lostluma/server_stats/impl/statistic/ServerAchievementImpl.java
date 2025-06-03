package net.lostluma.server_stats.impl.statistic;

import net.lostluma.server_stats.api.statistic.ServerAchievement;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ServerAchievementImpl extends ServerStatisticImpl implements ServerAchievement {
	private final @Nullable ServerAchievement parent;

	public ServerAchievementImpl(int vanillaId, String identifier, @Nullable ServerAchievement parent) {
		this(vanillaId, "minecraft", identifier, parent);
	}

	protected ServerAchievementImpl(String namespace, String identifier, @Nullable ServerAchievement parent) {
		this(-1, namespace, identifier, parent);
	}

	protected ServerAchievementImpl(int vanillaId, String namespace, String identifier, @Nullable ServerAchievement parent) {
		super(vanillaId, namespace, identifier);

		this.parent = parent;
	}

	public String key() {
		if (this.namespace().equals("minecraft")) {
			return "achievement." + this.identifier();
		} else {
			return "achievement." + this.namespace() + "." + this.identifier();
		}
	}

	@Override
	public Optional<ServerAchievement> parent() {
		return Optional.ofNullable(this.parent);
	}

	public static class Builder implements ServerAchievement.Builder {
		private final String namespace;
		private final String identifier;

		private @Nullable ServerAchievement parent;

		public Builder(String namespace, String identifier) {
			this.namespace = namespace;
			this.identifier = identifier;

			this.parent = null;
		}

		@Override
		public ServerAchievement build() {
			return new ServerAchievementImpl(this.namespace, this.identifier, this.parent);
		}

		@Override
		public ServerAchievement.Builder parent(ServerAchievement parent) {
			this.parent = parent;
			return this;
		}
	}
}
