package net.lostluma.server_stats.api.v1.util.convert;

import net.lostluma.server_stats.api.v1.statistic.ServerStatistic;

/**
 * A type which may be converted into a {@link ServerStatistic}.
 * <br>
 * To do so call {@link ServerStatistic#from} with the marked object.
 * <br><br>
 * On applicable Minecraft versions {@code Stat} implements this interface.
 */
public interface IntoServerStatistic {}
