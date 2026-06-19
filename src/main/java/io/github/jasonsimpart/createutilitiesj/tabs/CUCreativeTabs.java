package io.github.jasonsimpart.createutilitiesj.tabs;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import io.github.jasonsimpart.createutilitiesj.blocks.CUBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;

import static io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ.REGISTRATE;

public class CUCreativeTabs {

	private static final DeferredRegister<CreativeModeTab> TAB_REGISTER = DeferredRegister
			.create(Registries.CREATIVE_MODE_TAB, CreateUtilitiesJ.ID);

	private static final ResourceKey<CreativeModeTab> BASE_KEY = ResourceKey.create(
			Registries.CREATIVE_MODE_TAB, CreateUtilitiesJ.asResource("base"));

	public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BASE = TAB_REGISTER.register("base",
			() -> CreativeModeTab.builder()
					.title(Component.translatable("itemGroup.createutilities.base"))
					.icon(() -> CUBlocks.VOID_MOTOR.asStack())
					.build());

	public static void useBaseTab() {
		REGISTRATE.defaultCreativeTab(BASE_KEY);
		REGISTRATE.setCreativeTab(BASE);
	}

	public static void register(IEventBus modEventBus) {
		TAB_REGISTER.register(modEventBus);
	}
}
