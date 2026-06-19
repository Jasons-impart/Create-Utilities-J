package io.github.jasonsimpart.createutilitiesj.networking;

import io.github.jasonsimpart.createutilitiesj.networking.packets.VoidBatteryUpdatePacket;
import io.github.jasonsimpart.createutilitiesj.networking.packets.VoidTankUpdatePacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class CUPackets {

	public static final int NETWORK_VERSION = 2;
	public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);

	public static void register(IEventBus modEventBus) {
		modEventBus.addListener(CUPackets::registerPayloads);
	}

	private static void registerPayloads(RegisterPayloadHandlersEvent event) {
		PayloadRegistrar registrar = event.registrar(NETWORK_VERSION_STR);
		registrar.playToClient(VoidTankUpdatePacket.TYPE, VoidTankUpdatePacket.STREAM_CODEC, VoidTankUpdatePacket::handle);
		registrar.playToClient(VoidBatteryUpdatePacket.TYPE, VoidBatteryUpdatePacket.STREAM_CODEC, VoidBatteryUpdatePacket::handle);
	}

	public static void sendToAll(CustomPacketPayload payload) {
		PacketDistributor.sendToAllPlayers(payload);
	}
}
