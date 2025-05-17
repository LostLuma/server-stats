package net.lostluma.server_stats.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class Format {
	private static final NumberFormat NUMBER_FORMAT = NumberFormat.getIntegerInstance(Locale.US);
	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("########0.00");

	public static String formatNumber(long value) {
		return NUMBER_FORMAT.format(value);
	}

	public static String formatTime(long value) {
		double seconds = value / 20.0;
		double minutes = seconds / 60.0;
		double hours = minutes / 60.0;
		double days = hours / 24.0;
		double years = days / 365.0;

		if (years > 0.5) {
			return DECIMAL_FORMAT.format(years) + " y";
		} else if (days > 0.5) {
			return DECIMAL_FORMAT.format(days) + " d";
		} else if (hours > 0.5) {
			return DECIMAL_FORMAT.format(hours) + " h";
		} else {
			return minutes > 0.5 ? DECIMAL_FORMAT.format(minutes) + " m" : seconds + " s";
		}
	}

	public static String formatDistance(long value) {
		double meters = value / 100.0;
		double kilometers = meters / 1000.0;

		if (kilometers > 0.5) {
			return DECIMAL_FORMAT.format(kilometers) + " km";
		} else {
			return meters > 0.5 ? DECIMAL_FORMAT.format(meters) + " m" : value + " cm";
		}
	}
}
