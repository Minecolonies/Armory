package com.smithsmodding.armory.api.common.helpers;

import com.smithsmodding.armory.api.common.factories.IFactoryController;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectOverrideManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IArmoryHelpers {

    IMedievalUpgradeConstructionHelper getMedievalUpgradeConstructionHelper();

    IMaterialConstructionHelper getMaterialConstructionHelper();

    IFactoryController getFactories();

    IHeatedObjectOverrideManager getHeatableOverrideManager();
}
