package net.lostluma.server_stats.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logging {
	private static final Logger LOGGER = LoggerFactory.getLogger(Constants.MOD_ID);

	public static Logger getLogger() {
		return LOGGER;
	}
}
