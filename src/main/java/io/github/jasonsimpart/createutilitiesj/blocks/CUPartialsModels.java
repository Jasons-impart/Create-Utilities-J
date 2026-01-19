package io.github.jasonsimpart.createutilitiesj.blocks;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;

public class CUPartialsModels {

	public static final PartialModel VOID_CHEST_LID = block("void_chest/lid");

	public static final PartialModel VOID_BATTERY_DIAL = block("void_battery/dial");

	private static PartialModel block(String path) {
		return PartialModel.of(CreateUtilitiesJ.asResource("block/" + path));
	}

	public static void init() {}

}
