package net.lostluma.server_stats.compat.osl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.lostluma.server_stats.stats.ServerPlayerStats;
import net.ornithemc.osl.networking.api.CustomPayload;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Map;

public class SyncStatsPacket implements CustomPayload {
    private String serialized;

    public SyncStatsPacket() {
        this.serialized = null;
    }

    public SyncStatsPacket(ServerPlayerStats statistics) {
        this.serialized = statistics.serialize();
    }

    @Override
    public void read(DataInputStream input) throws IOException {
        this.serialized = input.readUTF();
    }

    @Override
    public void write(DataOutputStream output) throws IOException {
        if (this.serialized != null) {
            output.writeUTF(this.serialized);
        } else {
            throw new RuntimeException("Tried to write incomplete SyncStatsPacket!");
        }
    }

    public Map<String, Integer> data() {
        if (this.serialized != null) {
            Type type = new TypeToken<Map<String, Integer>>(){}.getType();
            return new Gson().fromJson(this.serialized, type);
        } else {
            throw new RuntimeException("Tried to get data from incomplete SyncStatsPacket!");
        }
    }
}
