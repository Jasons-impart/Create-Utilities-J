package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.energy.EnergyStorage;

import java.util.HashMap;
import java.util.Map;

public class VoidBattery extends EnergyStorage {

	private final NetworkKey key;

	public VoidBattery(NetworkKey key) {
		super(32000, 4096, 4096);
		this.key = key;
	}

	public boolean isEmpty() {
		return energy == 0;
	}

	@Override
	public int receiveEnergy(int maxReceive, boolean simulate) {
		int inserted = super.receiveEnergy(maxReceive, simulate);
		if (inserted != 0) onContentsChanged();
		return inserted;
	}

	@Override
	public int extractEnergy(int maxExtract, boolean simulate) {
		int extracted = super.extractEnergy(maxExtract, simulate);
		if (extracted != 0) onContentsChanged();
		return extracted;
	}

	public static Map<NetworkKey, VoidBattery> updateMSG = new HashMap<>();
	public static Map<NetworkKey, Boolean> updated = new HashMap<>();
	private void onContentsChanged() {
		if (CreateUtilitiesJ.VOID_BATTERIES_DATA != null) CreateUtilitiesJ.VOID_BATTERIES_DATA.setDirty();
		updateMSG.put(key, this);
		updated.put(key, true);
	}

	public CompoundTag serializeNBT() {
		CompoundTag nbt = new CompoundTag();
		nbt.putLong("Energy", energy);
		return nbt;
	}

	public void deserializeNBT(CompoundTag nbt) {
		energy = nbt.getInt("Energy");
	}
}
