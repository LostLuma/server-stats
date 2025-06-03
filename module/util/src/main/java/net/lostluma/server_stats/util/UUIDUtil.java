package net.lostluma.server_stats.util;

import java.util.UUID;
import java.util.regex.Pattern;

public class UUIDUtil {
	/**
	 * Turn a string UUID without dashes into a UUID.
	 */
	public static UUID fromMojang(String data) {
		Pattern pattern = Pattern.compile("(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})");
		return UUID.fromString(pattern.matcher(data).replaceAll("$1-$2-$3-$4-$5")); // :3
	}
}
