package io.github.jasonsimpart.createutilitiesj.networking.packets;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesClient;
import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.jetbrains.annotations.NotNull;

public class VoidTankUpdatePacket implements CustomPacketPayload {

	public static final Type<VoidTankUpdatePacket> TYPE = new Type<>(CreateUtilitiesJ.asResource("void_tank_update"));
	public static final StreamCodec<RegistryFriendlyByteBuf, VoidTankUpdatePacket> STREAM_CODEC =
			StreamCodec.of((buffer, packet) -> packet.write(buffer), VoidTankUpdatePacket::new);

	private final NetworkKey key;
	private final FluidTank tank;

	public VoidTankUpdatePacket(NetworkKey key, VoidTank tank) {
		this.key = key;
		this.tank = tank;
	}

	public VoidTankUpdatePacket(RegistryFriendlyByteBuf buffer) {
		key = NetworkKey.fromBuffer(buffer);
		tank = new FluidTank(VoidTank.CAPACITY);
		tank.readFromNBT(buffer.registryAccess(), buffer.readNbt());
	}

	public void write(RegistryFriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(tank.writeToNBT(buffer.registryAccess(), new CompoundTag()));
	}

	public void handle(IPayloadContext context) {
		context.enqueueWork(() -> CreateUtilitiesClient.VOID_TANKS.storages.put(key, tank));
	}

	@Override
	public @NotNull Type<? extends CustomPacketPayload> type() {
		return TYPE;
	}

}
