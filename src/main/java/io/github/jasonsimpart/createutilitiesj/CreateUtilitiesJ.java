package io.github.jasonsimpart.createutilitiesj;

import com.simibubi.create.foundation.data.CreateRegistrate;
import io.github.jasonsimpart.createutilitiesj.blocks.CUBlocks;
import io.github.jasonsimpart.createutilitiesj.blocks.CUTileEntities;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBatteryTileEntity;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest.VoidChestTileEntity;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTankTileEntity;
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
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.common.NeoForge;
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

	public CreateUtilitiesJ(IEventBus modEventBus, ModContainer modContainer) {
		onCtor(modEventBus);
	}

	public static void onCtor(IEventBus modEventBus) {

		IEventBus forgeEventBus = NeoForge.EVENT_BUS;

		REGISTRATE.registerEventListeners(modEventBus);

		CUCreativeTabs.register(modEventBus);
		CUCreativeTabs.useBaseTab();
		CUBlocks.register();
		CUItems.register();
		CUTileEntities.register();
		CUContainerTypes.register();
		CUMountedStorages.register();
		CUPackets.register(modEventBus);

		modEventBus.addListener(CreateUtilitiesJ::init);
		modEventBus.addListener(CreateUtilitiesJ::registerCapabilities);
		if (FMLEnvironment.dist == Dist.CLIENT)
			CreateUtilitiesClient.onCtorClient(modEventBus, forgeEventBus);

	}

	public static void init(final FMLCommonSetupEvent event) {
	}

	public static void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(Capabilities.ItemHandler.BLOCK, CUTileEntities.VOID_CHEST.get(), VoidChestTileEntity::getItemStorage);
		event.registerBlockEntity(Capabilities.FluidHandler.BLOCK, CUTileEntities.VOID_TANK.get(), VoidTankTileEntity::getFluidStorage);
		event.registerBlockEntity(Capabilities.EnergyStorage.BLOCK, CUTileEntities.VOID_BATTERY.get(), VoidBatteryTileEntity::getBattery);
	}

	public static ResourceLocation asResource(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
