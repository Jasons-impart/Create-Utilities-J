package io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler.NetworkKey;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class VoidChestInventory extends ItemStackHandler {

	private final NetworkKey key;

	public VoidChestInventory(NetworkKey key) {
		super(27);
		this.key = key;
	}

	@Override
	protected void onContentsChanged(int slot) {
		if (CreateUtilitiesJ.VOID_CHEST_INVENTORIES_DATA != null) CreateUtilitiesJ.VOID_CHEST_INVENTORIES_DATA.setDirty();
	}

	public boolean isEmpty() {
		return stacks.stream().allMatch(ItemStack::isEmpty);
	}

	public NetworkKey getKey() {
		return key;
	}

}
