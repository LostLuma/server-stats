package net.lostluma.server_stats.statistic.vanilla.client.util;

public class Position {
	public final double x;
	public final double y;
	public final double z;
	public final boolean onGround;

	public Position(double x, double y, double z, boolean onGround) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.onGround = onGround;
	}

	/**
	 * @param other A different point.
	 * @return The distance between the points, in centimeters.
	 */
	public int distanceTo(Position other) {
		double a = this.x - other.x;
		double b = this.y - other.y;
		double c = this.z - other.z;

		return Math.toIntExact(Math.round(Math.sqrt(a * a + b * b + c * c) * 100.0F));
	}

	/**
	 * @param other A different point.
	 * @return The horizontal distance between the points, in centimeters.
	 */
	public int horizontalDistanceTo(Position other) {
		double a = this.x - other.x;
		double b = this.y - other.y;

		return Math.toIntExact(Math.round(Math.sqrt(a * a + b * b) * 100.0F));
	}
}
