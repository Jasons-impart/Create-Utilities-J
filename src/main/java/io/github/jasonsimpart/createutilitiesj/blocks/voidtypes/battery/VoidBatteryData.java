package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery;

import org.jetbrains.annotations.NotNull;

import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.VoidStorageData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

public class VoidBatteryData extends VoidStorageData<VoidBattery> {

	public VoidBattery computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidBattery::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, VoidBattery::isEmpty, VoidBattery::serializeNBT);
	}

	public static VoidBatteryData load(CompoundTag tag) {
		return load(tag, VoidBatteryData::new, VoidBattery::new, VoidBattery::deserializeNBT);
	}

}
