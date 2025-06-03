package net.lostluma.server_stats.api.util;

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
	boolean isOk();

	/**
	 * @return Whether the operation produced an error.
	 */
	boolean isError();

	/**
	 * The operation's result.
	 *
	 * @return The result value.
	 * @throws IllegalStateException No value is available.
	 */
	T value() throws IllegalStateException;

	/**
	 * The operation's error result
	 *.
	 * @return The error value.
	 * @throws IllegalStateException No error is available.
	 */
	Error error() throws IllegalStateException;
}
