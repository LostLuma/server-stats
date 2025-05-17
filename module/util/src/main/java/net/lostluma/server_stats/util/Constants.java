package net.lostluma.server_stats.util;

import net.lostluma.server_stats.util.platform.Platform;

public class Constants {
	public static final String MOD_ID = "server_stats";
	public static final String USER_AGENT = buildUserAgent();

	// Server Stats 1.1+ 32 Bit stats sync
	public static final String STATS_PACKET_SMALL_CHANNEL = MOD_ID + "|s";
	// Server Stats 1.4+ 64 Bit stats sync
	public static final String STATS_PACKET_LARGE_CHANNEL = MOD_ID + "|l";

	// Server Stats 1.4+ live sync of data
	public static final String STATS_PACKET_AMEND_CHANNEL = MOD_ID + "|a";
	// Server Stats 1.4+ live sync of data
	public static final String STATS_PACKET_RESET_CHANNEL = MOD_ID + "|r";

	// Server Stats 1.4+ player data fetch
	public static final String STATS_PACKET_FETCH_CHANNEL = MOD_ID + "|f";

	private static String buildUserAgent() {
		return String.format("Server Stats/%s (+%s)", Platform.getModVersion(MOD_ID), Platform.getHomepageUrl(MOD_ID));
	}
}
