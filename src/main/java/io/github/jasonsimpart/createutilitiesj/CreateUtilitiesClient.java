package io.github.jasonsimpart.createutilitiesj;

import io.github.jasonsimpart.createutilitiesj.blocks.CUPartialsModels;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.VoidStorageClient;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBattery;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTank;
import io.github.jasonsimpart.createutilitiesj.ponder.CUPonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.capability.templates.FluidTank;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class CreateUtilitiesClient {

	public static final VoidStorageClient<FluidTank> VOID_TANKS = new VoidStorageClient<>(
			k -> new FluidTank(VoidTank.CAPACITY));

	public static final VoidStorageClient<VoidBattery> VOID_BATTERIES = new VoidStorageClient<>(
			VoidBattery::new);

	public static void onCtorClient(IEventBus modEventBus, IEventBus forgeEventBus) {
		modEventBus.addListener(CreateUtilitiesClient::clientInit);
		CUPartialsModels.init();
	}

	public static void clientInit(final FMLClientSetupEvent event) {
		PonderIndex.addPlugin(new CUPonderPlugin());
	}

}
