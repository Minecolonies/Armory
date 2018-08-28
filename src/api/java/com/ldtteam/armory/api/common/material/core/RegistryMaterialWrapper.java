package com.ldtteam.armory.api.common.material.core;

import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/14/2017.
 */
public final class RegistryMaterialWrapper extends IForgeRegistryEntry.Impl<RegistryMaterialWrapper> {

    @Nonnull
    private final IMaterial wrapped;

    public RegistryMaterialWrapper(@Nonnull IMaterial wrapped) {
        this.wrapped = wrapped;
        final ModContainer armoryContainer = Loader.instance().activeModContainer();
        final ModContainer wrappedRegisteringContainer = Loader.instance()
                                                           .getActiveModList()
                                                           .stream()
                                                           .filter(modContainer -> modContainer.getModId().equals(wrapped.getRegistryName().getResourceDomain()))
                                                           .findFirst()
                                                           .orElseThrow(() -> new IllegalArgumentException("Given material is not registered by a known Mod."));

        Loader.instance().setActiveModContainer(wrappedRegisteringContainer);
        this.setRegistryName(wrapped.getRegistryName());
        Loader.instance().setActiveModContainer(armoryContainer);
    }

    /**
     * Method to get the wrapped entry of a other registry.
     * @return The wrapped nonnull entry.
     */
    @Nonnull
    public IMaterial getWrapped() {
        return wrapped;
    }
}
