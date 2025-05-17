package net.lostluma.server_stats.impl;

import org.jetbrains.annotations.ApiStatus;

import java.util.NoSuchElementException;
import java.util.ServiceLoader;

@ApiStatus.Internal
public class ProxyLoader {
	static final ApiProxy INSTANCE = getServiceInstance(ApiProxy.class);

	public static <T> T getServiceInstance(Class<T> type) {
		try {
			return ServiceLoader.load(type).iterator().next();
		} catch (NoSuchElementException e) {
			throw new RuntimeException("Failed to load Server Stats API service!");
		}
	}
}
