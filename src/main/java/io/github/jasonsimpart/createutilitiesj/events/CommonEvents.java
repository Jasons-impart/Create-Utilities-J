package io.github.jasonsimpart.createutilitiesj.events;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBattery;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBatteryData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest.VoidChestInventoriesData;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTank;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTanksData;
import io.github.jasonsimpart.createutilitiesj.networking.CUPackets;
import io.github.jasonsimpart.createutilitiesj.networking.packets.VoidBatteryUpdatePacket;
import io.github.jasonsimpart.createutilitiesj.networking.packets.VoidTankUpdatePacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.level.LevelEvent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;

@EventBusSubscriber
public class CommonEvents {
	@SubscribeEvent
	public static void onLoad(LevelEvent.Load event) {

		MinecraftServer server = event.getLevel().getServer();
		if (server == null) return;

		LevelAccessor level = event.getLevel();
		DimensionDataStorage dataStorage = server.overworld().getDataStorage();

		CreateUtilitiesJ.VOID_MOTOR_LINK_NETWORK_HANDLER.onLoadWorld(level);

		CreateUtilitiesJ.VOID_CHEST_INVENTORIES_DATA = dataStorage
				.computeIfAbsent(new SavedData.Factory<>(VoidChestInventoriesData::new, VoidChestInventoriesData::load), "VoidChestInventories");

		CreateUtilitiesJ.VOID_TANKS_DATA = dataStorage
				.computeIfAbsent(new SavedData.Factory<>(VoidTanksData::new, VoidTanksData::load), "VoidTanks");

		CreateUtilitiesJ.VOID_BATTERIES_DATA = dataStorage
				.computeIfAbsent(new SavedData.Factory<>(VoidBatteryData::new, VoidBatteryData::load), "VoidBatteries");

	}

	@SubscribeEvent
	public static void onUnload(LevelEvent.Unload event) {
		CreateUtilitiesJ.VOID_MOTOR_LINK_NETWORK_HANDLER.onUnloadWorld(event.getLevel());
	}

	private static int counter = 0;
	@SubscribeEvent
	public static void onTick(ServerTickEvent.Post event) {
		if (counter++ % 10 == 0) {
			for (var entry : VoidTank.updateMSG.entrySet()) {
				if (VoidTank.updated.getOrDefault(entry.getKey(), false)) {
					CUPackets.sendToAll(new VoidTankUpdatePacket(entry.getKey(), entry.getValue()));
				}
			}
			for (var entry : VoidBattery.updateMSG.entrySet()) {
				if (VoidBattery.updated.getOrDefault(entry.getKey(), false)) {
					CUPackets.sendToAll(new VoidBatteryUpdatePacket(entry.getKey(), entry.getValue()));
				}
			}
		}
	}
}
