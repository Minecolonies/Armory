package com.ldtteam.armory.api.common.armor.callback;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.ldtteam.armory.api.common.capability.armor.IArmorCapability;
import net.minecraftforge.common.capabilities.Capability;

import java.util.Map;

/**
 * Simple implementation of the {@link ICapabilityMapBuilder} interface.
 */
public class SimpleCapabilityMapBuilder implements ICapabilityMapBuilder
{
    private final Map<Capability<? extends IArmorCapability>, IArmorCapability> capabilityMap = Maps.newHashMap();

    /**
     * Adds capability to the capability map that is about to be build.
     * Overrides a capability if it has been registered before.
     *
     * @param capability         The capability to add.
     * @param capabilityInstance The instance of the capability to add.
     * @return The builder.
     */
    @Override
    public <C extends IArmorCapability> ICapabilityMapBuilder register(
      final Capability<C> capability, final C capabilityInstance)
    {
        capabilityMap.put(capability, capabilityInstance);
        return this;
    }

    /**
     * Returns all registered capabilities.
     *
     * @return All registered capabilities.
     */
    @Override
    public ImmutableMap<Capability<? extends IArmorCapability>, IArmorCapability> getCapabilities()
    {
        return ImmutableMap.copyOf(capabilityMap);
    }
}
