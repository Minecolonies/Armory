package com.ldtteam.armory.api.common.armor.callback;

/**
 * Interface used to get the default capabilities via a callback during material construction.
 */
@FunctionalInterface
public interface IDefaultCapabilitiesRetrievalCallback {

    /**
     * Method to get capabilities from the Callback.
     * @param capabilityMapBuilder The builder.
     */
    void get(final ICapabilityMapBuilder capabilityMapBuilder);
}