package com.smithsmodding.armory.api.common.armor.callback;

import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import net.minecraftforge.common.capabilities.Capability;

public interface ICapabilityMapBuilder
{

    /**
     * Adds capability to the capability map that is about to be build.
     *
     * @param capability         The capability to add.
     * @param capabilityInstance The instance of the capability to add.
     * @param <C>                The type of the capability.
     * @return The builder.
     */
    <C extends IArmorCapability> ICapabilityMapBuilder put(final Capability<C> capability, final C capabilityInstance);
}
