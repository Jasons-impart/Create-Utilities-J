package io.github.jasonsimpart.createutilitiesj.networking.packets;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesClient;
import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBattery;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class VoidBatteryUpdatePacket implements CustomPacketPayload {

	public static final Type<VoidBatteryUpdatePacket> TYPE = new Type<>(CreateUtilitiesJ.asResource("void_battery_update"));
	public static final StreamCodec<RegistryFriendlyByteBuf, VoidBatteryUpdatePacket> STREAM_CODEC =
			StreamCodec.of((buffer, packet) -> packet.write(buffer), VoidBatteryUpdatePacket::new);

	private final NetworkKey key;
	private final VoidBattery battery;

	public VoidBatteryUpdatePacket(NetworkKey key, VoidBattery battery) {
		this.key = key;
		this.battery = battery;
	}

	public VoidBatteryUpdatePacket(RegistryFriendlyByteBuf buffer) {
		key = NetworkKey.fromBuffer(buffer);
		battery = new VoidBattery(key);
		battery.deserializeNBT(buffer.registryAccess(), buffer.readNbt());
	}

	public void write(RegistryFriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(battery.serializeNBT(buffer.registryAccess()));
	}

	public void handle(IPayloadContext context) {
		context.enqueueWork(() -> CreateUtilitiesClient.VOID_BATTERIES.storages.put(key, battery));
	}

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

}
