package net.lostluma.server_stats.impl.util;

import net.lostluma.server_stats.api.util.Result;
import org.jetbrains.annotations.NotNull;

public class ResultImpl<T, Error> implements Result<T, Error> {
	private final T value;
	private final Error error;

	private final boolean success;

	private ResultImpl(T value, Error error, boolean success) {
		this.value = value;
		this.error = error;

		this.success = success;
	}

	public static <A, B> Result<A, B> ok(A value) {
		return new ResultImpl<>(value, null, true);
	}

	public static <A, B> Result<A, B> error(B error) {
		return new ResultImpl<>(null, error, false);
	}

	@Override
	public boolean isOk() {
		return this.success;
	}

	@Override
	public boolean isError() {
		return !this.success;
	}

	@Override
	public @NotNull T value() throws IllegalStateException {
		if (this.isOk()) {
			return this.value;
		} else {
			throw new IllegalStateException("No value available.");
		}
	}

	@Override
	public @NotNull Error error() throws IllegalStateException {
		if (this.isError()) {
			return this.error;
		} else {
			throw new IllegalStateException("No error available.");
		}
	}
}
