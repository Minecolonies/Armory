package com.smithsmodding.armory.api.common.armor.callback;

/**
 * Created by marcf on 1/21/2017.
 */
@FunctionalInterface
public interface IDefaultCapabilitiesRetrievalCallback {

    /**
     * Method to get capabilities from the Callback.
     * @param capabilityMapBuilder The builder.
     */
    void get(final ICapabilityMapBuilder capabilityMapBuilder);
}