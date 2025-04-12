package net.lostluma.server_stats.common.util;

public final class Tuple<Left, Right> {
	private final Left left;
	private final Right right;

	public Tuple(Left left, Right right) {
		this.left = left;
		this.right = right;
	}

	public Left left() {
		return this.left;
	}

	public Right right() {
		return this.right;
	}
}
