package com.ldtteam.armory.api;

import com.ldtteam.armory.api.common.helpers.IArmoryHelpers;
import com.ldtteam.armory.api.common.registries.IRegistryManager;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IArmoryAPI
{

    /**
     * Getter for the RegistryManager.
     * @return The RegistryManager that is currently active.
     */
    @Nonnull
    IRegistryManager getRegistryManager();

    @Nonnull
    IArmoryHelpers getHelpers();

    class Holder {

        @Nullable
        private static IArmoryAPI INSTANCE;

        /**
         * Getter for the current API Instance.
         * @return The current instance of the ArmoryAPI
         * @throws IllegalStateException thrown when the API has not been initialized
         */
        @Nonnull
        public static IArmoryAPI getInstance() throws IllegalStateException {
            if (INSTANCE == null)
                throw new IllegalStateException();

            return INSTANCE;
        }

        public static void setInstance(@Nonnull IArmoryAPI api) {
            INSTANCE = api;
        }
    }
}
