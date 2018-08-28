package com.ldtteam.armory.common.api;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.helpers.IArmoryHelpers;
import com.ldtteam.armory.api.common.registries.IRegistryManager;
import com.ldtteam.armory.common.helpers.ArmoryHelpers;
import com.ldtteam.armory.common.registries.RegistryManager;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/2/2017.
 */
public class ArmoryAPI implements IArmoryAPI {

    @Nonnull private static final IArmoryAPI INSTANCE = new ArmoryAPI();

    @Nonnull
    public static IArmoryAPI getInstance() {
        return INSTANCE;
    }

    public static void initialize() {
        IArmoryAPI.Holder.setInstance(getInstance());
    }

    /**
     * Getter for the RegistryManager.
     *
     * @return The RegistryManager that is currently active.
     */
    @Nonnull
    @Override
    public IRegistryManager getRegistryManager() {
        return RegistryManager.getInstance();
    }

    @Nonnull
    @Override
    public IArmoryHelpers getHelpers() {
        return ArmoryHelpers.getInstance();
    }
}
