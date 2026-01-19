package io.github.jasonsimpart.createutilitiesj.ponder;

import io.github.jasonsimpart.createutilitiesj.CreateUtilitiesJ;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class CUPonderPlugin implements PonderPlugin {

    @Override
    public @NotNull String getModId() {
        return CreateUtilitiesJ.ID;
    }

    @Override
    public void registerScenes(@NotNull PonderSceneRegistrationHelper<ResourceLocation> helper) {
        CUPonders.registerScenes(helper);
    }

}
