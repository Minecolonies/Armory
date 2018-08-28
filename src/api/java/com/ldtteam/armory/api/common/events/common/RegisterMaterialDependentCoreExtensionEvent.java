package com.ldtteam.armory.api.common.events.common;

import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.smithscore.common.events.SmithsCoreEvent;
import net.minecraftforge.registries.IForgeRegistry;

/**
 * Created by marcf on 1/24/2017.
 */
public class RegisterMaterialDependentCoreExtensionEvent extends SmithsCoreEvent {

    private final IForgeRegistry<IMultiComponentArmorExtension> registry;

    public RegisterMaterialDependentCoreExtensionEvent(IForgeRegistry<IMultiComponentArmorExtension> registry) {
        this.registry = registry;
    }

    public IForgeRegistry<IMultiComponentArmorExtension> getRegistry() {
        return registry;
    }
}
