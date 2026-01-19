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
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

@Mod.EventBusSubscriber
public class CommonEvents {
	@SubscribeEvent
	public static void onLoad(LevelEvent.Load event) {

		MinecraftServer server = event.getLevel().getServer();
		if (server == null) return;

		LevelAccessor level = event.getLevel();
		DimensionDataStorage dataStorage = server.overworld().getDataStorage();

		CreateUtilitiesJ.VOID_MOTOR_LINK_NETWORK_HANDLER.onLoadWorld(level);

		CreateUtilitiesJ.VOID_CHEST_INVENTORIES_DATA = dataStorage
				.computeIfAbsent(VoidChestInventoriesData::load, VoidChestInventoriesData::new, "VoidChestInventories");

		CreateUtilitiesJ.VOID_TANKS_DATA = dataStorage
				.computeIfAbsent(VoidTanksData::load, VoidTanksData::new, "VoidTanks");

		CreateUtilitiesJ.VOID_BATTERIES_DATA = dataStorage
				.computeIfAbsent(VoidBatteryData::load, VoidBatteryData::new, "VoidBatteries");

	}

	@SubscribeEvent
	public static void onUnload(LevelEvent.Unload event) {
		CreateUtilitiesJ.VOID_MOTOR_LINK_NETWORK_HANDLER.onUnloadWorld(event.getLevel());
	}

	private static int counter = 0;
	@SubscribeEvent
	public static void onTick(TickEvent.ServerTickEvent event) {
		if (event.phase == TickEvent.Phase.END) {
			if (counter++ % 10 == 0) {
				for (var entry : VoidTank.updateMSG.entrySet()) {
					if (VoidTank.updated.getOrDefault(entry.getKey(), false)) {
						CUPackets.channel.send(PacketDistributor.ALL.noArg(), new VoidTankUpdatePacket(entry.getKey(), entry.getValue()));
					}
				}
				for (var entry : VoidBattery.updateMSG.entrySet()) {
					if (VoidBattery.updated.getOrDefault(entry.getKey(), false)) {
						CUPackets.channel.send(PacketDistributor.ALL.noArg(), new VoidBatteryUpdatePacket(entry.getKey(), entry.getValue()));
					}
				}
			}
		}
	}
}
