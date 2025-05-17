package net.lostluma.server_stats.network.client.service;

import net.lostluma.server_stats.impl.service.ClientNetworking;
import net.lostluma.server_stats.network.common.RequestPacketHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.CustomPayloadPacket;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class ClientNetworkingImpl implements ClientNetworking {
	@Override
	public void fetch(@NotNull String name) {
		CustomPayloadPacket packet = RequestPacketHelper.write(name, null);
		Minecraft.getInstance().getNetworkHandler().sendPacket(packet);
	}

	@Override
	public void fetch(@NotNull UUID identifier) {
		CustomPayloadPacket packet = RequestPacketHelper.write(null, identifier);
		Minecraft.getInstance().getNetworkHandler().sendPacket(packet);
	}
}
