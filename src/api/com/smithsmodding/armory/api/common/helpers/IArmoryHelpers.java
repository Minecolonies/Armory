package com.smithsmodding.armory.api.common.helpers;

import com.smithsmodding.armory.api.common.factories.IFactoryController;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectOverrideManager;

/**
 * Interface describing classes that contain references to helper used to create several components for Armory.
 */
public interface IArmoryHelpers {

    IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper();

    IMaterialConstructionHelper getMaterialConstructionHelper();

    IFactoryController getFactories();

    IHeatedObjectOverrideManager getHeatableOverrideManager();

    IRegistryHelper getRegistryHelpers();
}
