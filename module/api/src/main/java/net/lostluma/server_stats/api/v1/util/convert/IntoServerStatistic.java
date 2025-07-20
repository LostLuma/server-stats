package net.lostluma.server_stats.api.v1.util.convert;

/**
 * A type which may be converted into a {@code ServerStatistic}.
 * <br>
 * To do so call {@code ServerStatistic::from} with the marked object.
 * <br><br>
 * On applicable Minecraft versions {@code Stat} implements this interface.
 */
public interface IntoServerStatistic {}
