package io.github.jasonsimpart.createutilitiesj.blocks;

import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.simibubi.create.content.kinetics.gearbox.GearboxBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import io.github.jasonsimpart.createutilitiesj.blocks.gearcube.GearcubeVisual;
import io.github.jasonsimpart.createutilitiesj.blocks.gearcube.SimpleKineticRenderer;
import io.github.jasonsimpart.createutilitiesj.blocks.lgearbox.LShapedGearboxVisual;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBatteryRenderer;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.battery.VoidBatteryTileEntity;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest.VoidChestRenderer;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.chest.VoidChestTileEntity;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorRenderer;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.motor.VoidMotorTileEntity;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTankRenderer;
import io.github.jasonsimpart.createutilitiesj.blocks.voidtypes.tank.VoidTankTileEntity;

import static io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ.REGISTRATE;

public class CUTileEntities {

	public static final BlockEntityEntry<VoidMotorTileEntity> VOID_MOTOR = REGISTRATE
			.blockEntity("void_motor", VoidMotorTileEntity::new)
			.visual(() -> OrientedRotatingVisual.of(AllPartialModels.SHAFT_HALF), true)
			.validBlocks(CUBlocks.VOID_MOTOR)
			.renderer(() -> VoidMotorRenderer::new)
			.register();

	public static final BlockEntityEntry<VoidChestTileEntity> VOID_CHEST = REGISTRATE
			.blockEntity("void_chest", VoidChestTileEntity::new)
			.validBlocks(CUBlocks.VOID_CHEST)
			.renderer(() -> VoidChestRenderer::new)
			.register();

	public static final BlockEntityEntry<VoidTankTileEntity> VOID_TANK = REGISTRATE
			.blockEntity("void_tank", VoidTankTileEntity::new)
			.validBlocks(CUBlocks.VOID_TANK)
			.renderer(() -> VoidTankRenderer::new)
			.register();

	public static final BlockEntityEntry<VoidBatteryTileEntity> VOID_BATTERY = REGISTRATE
			.blockEntity("void_battery", VoidBatteryTileEntity::new)
			.validBlocks(CUBlocks.VOID_BATTERY)
			.renderer(() -> VoidBatteryRenderer::new)
			.register();

	public static final BlockEntityEntry<GearboxBlockEntity> GEARCUBE = REGISTRATE
			.blockEntity("gearcube", GearboxBlockEntity::new)
			.visual(() -> GearcubeVisual::new, false)
			.validBlocks(CUBlocks.GEARCUBE)
			.renderer(() -> SimpleKineticRenderer::new)
			.register();

	public static final BlockEntityEntry<GearboxBlockEntity> LSHAPED_GEARBOX = REGISTRATE
			.blockEntity("lshaped_gearbox", GearboxBlockEntity::new)
			.visual(() -> LShapedGearboxVisual::new, false)
			.validBlocks(CUBlocks.LSHAPED_GEARBOX)
			.renderer(() -> SimpleKineticRenderer::new)
			.register();

	public static void register() {}

}
