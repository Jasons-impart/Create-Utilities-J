package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest;

import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.VoidStorageData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;

import org.jetbrains.annotations.NotNull;

public class VoidChestInventoriesData extends VoidStorageData<VoidChestInventory> {

	public VoidChestInventory computeStorageIfAbsent(NetworkKey key) {
		return super.computeStorageIfAbsent(key, VoidChestInventory::new);
	}

	@Override
	public @NotNull CompoundTag save(@NotNull CompoundTag tag) {
		return super.save(tag, VoidChestInventory::isEmpty, VoidChestInventory::serializeNBT);
	}

	public static VoidChestInventoriesData load(CompoundTag tag) {
		return load(tag, VoidChestInventoriesData::new, VoidChestInventory::new, VoidChestInventory::deserializeNBT);
	}

}
