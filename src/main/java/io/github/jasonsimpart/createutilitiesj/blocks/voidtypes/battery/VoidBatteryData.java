package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery;

import org.jetbrains.annotations.NotNull;

import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.VoidStorageData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;

public class VoidBatteryData extends VoidStorageData<VoidBattery> {

	public VoidBattery computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidBattery::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag, HolderLookup.Provider registries) {
		return super.save(tag, registries, VoidBattery::isEmpty, VoidBattery::serializeNBT);
	}

	public static VoidBatteryData load(CompoundTag tag, HolderLookup.Provider registries) {
		return load(tag, registries, VoidBatteryData::new, VoidBattery::new, VoidBattery::deserializeNBT);
	}

}
