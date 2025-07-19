package net.lostluma.server_stats.impl.util;

import net.lostluma.server_stats.api.v1.util.Result;
import org.jetbrains.annotations.UnknownNullability;

public class ResultImpl {
	public static <T extends @UnknownNullability Object, Error> Result<T, Error> ok(T value) {
		return new OkResultImpl<>(value);
	}

	public static <T extends @UnknownNullability Object, Error> Result<T, Error> error(Error error) {
		return new ErrorResultImpl<>(error);
	}

	private static final class OkResultImpl<T extends @UnknownNullability Object, Error> implements Result<T, Error> {
		private final T value;

		private OkResultImpl(T value) {
			this.value = value;
		}

		@Override
		public boolean isOk() {
			return true;
		}

		@Override
		public boolean isError() {
			return false;
		}

		@Override
		public T value() throws IllegalStateException {
			return this.value;
		}

		@Override
		public Error error() throws IllegalStateException {
			throw new IllegalStateException("No error available.");
		}
	}

	private static final class ErrorResultImpl<T extends @UnknownNullability Object, Error> implements Result<T, Error> {
		private final Error error;

		private ErrorResultImpl(Error error) {
			this.error = error;
		}

		@Override
		public boolean isOk() {
			return false;
		}

		@Override
		public boolean isError() {
			return true;
		}

		@Override
		public T value() throws IllegalStateException {
			throw new IllegalStateException("No value available.");
		}

		@Override
		public Error error() throws IllegalStateException {
			return this.error;
		}
	}
}
