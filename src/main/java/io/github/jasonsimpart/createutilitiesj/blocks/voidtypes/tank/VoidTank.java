package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank;

import com.simibubi.create.infrastructure.config.AllConfigs;
import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import java.util.HashMap;
import java.util.Map;

public class VoidTank extends FluidTank {

	public static final int CAPACITY = AllConfigs.server().fluids.fluidTankCapacity.get() * 1000;

	private final NetworkKey key;

	public VoidTank(NetworkKey key) {
		super(CAPACITY);
		this.key = key;
	}

	public static Map<NetworkKey, VoidTank> updateMSG = new HashMap<>();
	public static Map<NetworkKey, Boolean> updated = new HashMap<>();
	@Override
	protected void onContentsChanged() {
		if (CreateUtilitiesJ.VOID_TANKS_DATA != null) CreateUtilitiesJ.VOID_TANKS_DATA.setDirty();
		updateMSG.put(key, this);
		updated.put(key, true);
	}
}
