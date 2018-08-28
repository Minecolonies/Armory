package com.ldtteam.armory.common.helpers;

import com.ldtteam.armory.api.common.factories.IFactoryController;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectOverrideManager;
import com.ldtteam.armory.api.common.helpers.IArmoryHelpers;
import com.ldtteam.armory.api.common.helpers.IMaterialConstructionHelper;
import com.ldtteam.armory.api.common.helpers.IMedievalUpgradeConstructionHelper;
import com.ldtteam.armory.api.common.helpers.IRegistryHelper;
import com.ldtteam.armory.common.factories.FactoryController;
import com.ldtteam.armory.common.heatable.HeatedObjectOverrideManager;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class ArmoryHelpers implements IArmoryHelpers {

    @Nonnull
    private static ArmoryHelpers instance = new ArmoryHelpers();

    private ArmoryHelpers() {
    }

    @Nonnull
    public static ArmoryHelpers getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper() {
        return MedievalUpgradeConstructionHelper.getInstance();
    }

    @Nonnull
    @Override
    public IMaterialConstructionHelper getMaterialConstructionHelper() {
        return MaterialConstructionHelper.getInstance();
    }

    @Override
    public IFactoryController getFactories() {
        return FactoryController.getInstance();
    }

    @Override
    public IHeatedObjectOverrideManager getHeatableOverrideManager() {
        return HeatedObjectOverrideManager.getInstance();
    }

    @Override
    public IRegistryHelper getRegistryHelpers()
    {
        return RegistryHelper.getInstance();
    }
}
