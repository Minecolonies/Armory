package com.smithsmodding.armory.api.common.armor.callback;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import net.minecraftforge.common.capabilities.Capability;

public interface ICapabilityMapBuilder
{

    /**
     * Adds capability to the capability map that is about to be build.
     * Overrides a capability if it has been registered before.
     *
     * @param capability         The capability to add.
     * @param capabilityInstance The instance of the capability to add.
     * @param <C>                The type of the capability.
     * @return The builder.
     */
    <C extends IArmorCapability> ICapabilityMapBuilder register(final Capability<C> capability, final C capabilityInstance);

    /**
     * Returns all registered capabilities.
     *
     * @return All registered capabilities.
     */
    ImmutableMap<Capability<? extends IArmorCapability>, IArmorCapability> getCapabilities();
}
