package net.lostluma.server_stats.common;

import net.lostluma.server_stats.common.util.Platform;

public class Constants {
	public static final String MOD_ID = "server_stats";
	public static final String USER_AGENT = buildUserAgent();

	// Legacy stats sync (32 Bit)
	public static final String STATS_PACKET_SMALL_CHANNEL = MOD_ID + "|s";
	// Server Stats 1.4+ 64 Bit stats sync
	public static final String STATS_PACKET_LARGE_CHANNEL = MOD_ID + "|l";
	// Server Stats 1.4+ live sync of data
	public static final String STATS_PACKET_AMEND_CHANNEL = MOD_ID + "|a";

	private static String buildUserAgent() {
		return String.format("Server Stats/%s (+%s)", Platform.getModVersion(MOD_ID), Platform.getHomepageUrl(MOD_ID));
	}
}
