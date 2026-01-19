package io.github.jasonsimpart.createutilitiesj.networking.packets;

import com.simibubi.create.foundation.networking.SimplePacketBase;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesClient;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBattery;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent;

public class VoidBatteryUpdatePacket extends SimplePacketBase {

	private final NetworkKey key;
	private final VoidBattery battery;

	public VoidBatteryUpdatePacket(NetworkKey key, VoidBattery battery) {
		this.key = key;
		this.battery = battery;
	}

	public VoidBatteryUpdatePacket(FriendlyByteBuf buffer) {
		key = NetworkKey.fromBuffer(buffer);
		battery = new VoidBattery(key);
		battery.deserializeNBT(buffer.readNbt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		key.writeToBuffer(buffer);
		buffer.writeNbt(battery.serializeNBT());
	}

	@Override
	public boolean handle(NetworkEvent.Context context) {
		context.enqueueWork(() -> DistExecutor.runWhenOn(Dist.CLIENT, () -> () ->
			CreateUtilitiesClient.VOID_BATTERIES.storages.put(key, battery)
		));
		return true;
	}

}
