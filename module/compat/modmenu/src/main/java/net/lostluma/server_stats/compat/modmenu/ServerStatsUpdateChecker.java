package net.lostluma.server_stats.compat.modmenu;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.terraformersmc.modmenu.api.UpdateChannel;
import com.terraformersmc.modmenu.api.UpdateChecker;
import com.terraformersmc.modmenu.api.UpdateInfo;
import net.lostluma.server_stats.util.Constants;
import net.lostluma.server_stats.util.Logging;
import net.lostluma.server_stats.util.platform.Version;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServerStatsUpdateChecker implements UpdateChecker {
	@Override
	public @Nullable UpdateInfo checkForUpdates() {
		@Nullable UpdateInfo update = null;

		try {
			update = checkForUpdates0();
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} catch (IOException e) {
			Logging.getLogger().warn("Failed to perform update check!", e);
		}

		return update;
	}

	private static UpdateInfo checkForUpdates0() throws IOException, InterruptedException {
		URL url = new URL("https://api.lostluma.net/updates/server-stats");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setReadTimeout(120000); // 2 minutes, as milliseconds
		connection.setRequestProperty("User-Agent", "Server Stats/" + Constants.MOD_VERSION);

		connection.connect();
		int status = connection.getResponseCode();

		if (status != 200) {
			throw new IOException("Received non-ok status code: " + status);
		}

		InputStream input = connection.getInputStream();
		InputStreamReader reader = new InputStreamReader(input);

		@SuppressWarnings("deprecation")
		JsonObject data = (JsonObject) new JsonParser().parse(reader);

		String download = data.get("website").getAsString();

		Version current = Version.of(Constants.MOD_VERSION);
		Version updated = Version.of(data.get("version").getAsString());

		return new ServerStatsUpdateInfo(updated.compareTo(current) > 0, download);
	}

	private static final class ServerStatsUpdateInfo implements UpdateInfo {
		private final boolean update;
		private final String website;

		private ServerStatsUpdateInfo(boolean update, String website) {
			this.update = update;
			this.website = website;
		}

		@Override
		public boolean isUpdateAvailable() {
			return this.update;
		}

		@Override
		public String getDownloadLink() {
			return this.website;
		}

		@Override
		public UpdateChannel getUpdateChannel() {
			return UpdateChannel.RELEASE;
		}
	}
}
