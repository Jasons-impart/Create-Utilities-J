package io.github.jasonsimpart.createutilitiesj;

import com.simibubi.create.foundation.data.CreateRegistrate;
import io.github.jasonsimpart.createutilitiesj.blocks.CUBlocks;
import io.github.jasonsimpart.createutilitiesj.blocks.CUTileEntities;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.CUContainerTypes;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBatteryData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest.VoidChestInventoriesData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorNetworkHandler;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTanksData;
import io.github.jasonsimpart.createutilitiesj.items.CUItems;
import io.github.jasonsimpart.createutilitiesj.mountedstorage.CUMountedStorages;
import io.github.jasonsimpart.createutilitiesj.networking.CUPackets;
import io.github.jasonsimpart.createutilitiesj.tabs.CUCreativeTabs;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreateUtilitiesJ.ID)
public class CreateUtilitiesJ {

	public static final String ID = "createutilities";
	public static final String NAME = "Create Utilities J";
	public static final Logger LOGGER = LoggerFactory.getLogger(NAME);

	public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(ID);

	public static final VoidMotorNetworkHandler VOID_MOTOR_LINK_NETWORK_HANDLER = new VoidMotorNetworkHandler();
	public static VoidChestInventoriesData VOID_CHEST_INVENTORIES_DATA;

	public static VoidTanksData VOID_TANKS_DATA;
	public static VoidBatteryData VOID_BATTERIES_DATA;

	public CreateUtilitiesJ() {
		onCtor();
	}

	public static void onCtor() {

		IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
		IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		CUBlocks.register();
		CUItems.register();
		CUTileEntities.register();
		CUContainerTypes.register();
		CUCreativeTabs.register(modEventBus);
		CUMountedStorages.register();

		modEventBus.addListener(CreateUtilitiesJ::init);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () ->
				CreateUtilitiesClient.onCtorClient(modEventBus, forgeEventBus)
		);

	}

	public static void init(final FMLCommonSetupEvent event) {
		CUPackets.registerPackets();
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(ID, path);
	}
}
