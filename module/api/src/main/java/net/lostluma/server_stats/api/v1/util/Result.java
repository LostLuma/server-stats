package net.lostluma.server_stats.api.v1.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.UnknownNullability;

/**
 * Represents the result of an operation that may gracefully fail.
 *
 * @param <T> The result type.
 * @param <Error> The error type.
 */
public interface Result<T extends @UnknownNullability Object, Error> {
	/**
	 * @return Whether the operation succeeded.
	 */
	@Contract(pure = true)
	boolean isOk();

	/**
	 * @return Whether the operation produced an error.
	 */
	@Contract(pure = true)
	boolean isError();

	/**
	 * The operation's result.
	 *
	 * @return The result value.
	 * @throws IllegalStateException No value is available.
	 */
	@Contract(pure = true)
	T value() throws IllegalStateException;

	/**
	 * The operation's error result
	 *.
	 * @return The error value.
	 * @throws IllegalStateException No error is available.
	 */
	@Contract(pure = true)
	Error error() throws IllegalStateException;
}
