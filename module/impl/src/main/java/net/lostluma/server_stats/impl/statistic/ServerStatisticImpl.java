package net.lostluma.server_stats.impl.statistic;

import net.lostluma.server_stats.api.statistic.ServerStatistic;
import org.jetbrains.annotations.NotNull;

public class ServerStatisticImpl implements ServerStatistic {
	private final int vanillaId;

	private final @NotNull String namespace;
	private final @NotNull String identifier;

	private static final String NAMESPACE_PATTERN = "^[a-z0-9_]{1,63}$";
	// Identifier pattern is more lax due to the vanilla keys from 1.7.x
	private static final String IDENTIFIER_PATTERN = "^[a-zA-Z0-9._]{1,63}$";

	public ServerStatisticImpl(int vanillaId, @NotNull String identifier) {
		this(vanillaId, "minecraft", identifier);
	}

	protected ServerStatisticImpl(@NotNull String namespace, @NotNull String identifier) {
		this(-1, namespace, identifier);
	}

	protected ServerStatisticImpl(int vanillaId, @NotNull String namespace, @NotNull String identifier) {
		this.vanillaId = vanillaId;

		this.namespace = namespace;
		this.identifier = identifier;

		if (!namespace.matches(NAMESPACE_PATTERN)) {
			throw new RuntimeException("Statistic namespace must match " + NAMESPACE_PATTERN);
		}

		if (!identifier.matches(IDENTIFIER_PATTERN)) {
			throw new RuntimeException("Statistic identifier must match " + IDENTIFIER_PATTERN);
		}

		RegistryImpl.register(this);
	}

	public int vanillaId() {
		return this.vanillaId;
	}

	public @NotNull String key() {
		if (this.namespace.equals("minecraft")) {
			return "stat." + this.identifier();
		} else {
			return "stat." + this.namespace() + "." + this.identifier();
		}
	}

	@Override
	public @NotNull String namespace() {
		return this.namespace;
	}

	@Override
	public @NotNull String identifier() {
		return this.identifier;
	}

	public static class Builder implements ServerStatistic.Builder {
		private final @NotNull String namespace;
		private final @NotNull String identifier;

		public Builder(@NotNull String namespace, @NotNull String identifier) {
			this.namespace = namespace;
			this.identifier = identifier;
		}

		@Override
		public @NotNull ServerStatistic build() {
			return new ServerStatisticImpl(this.namespace, this.identifier);
		}
	}
}
