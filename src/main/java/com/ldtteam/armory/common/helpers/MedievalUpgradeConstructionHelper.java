package com.ldtteam.armory.common.helpers;

import com.ldtteam.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMaterializableMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import com.ldtteam.armory.api.common.armor.callback.IDefaultCapabilitiesRetrievalCallback;
import com.ldtteam.armory.api.common.armor.callback.IExtensionRecipeRetrievalCallback;
import com.ldtteam.armory.api.common.helpers.IMedievalUpgradeConstructionHelper;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.common.armor.extension.MedievalArmorExtension;
import com.ldtteam.armory.common.armor.extension.MedievalMaterialDependantArmorExtension;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public final class MedievalUpgradeConstructionHelper implements IMedievalUpgradeConstructionHelper {

    @Nonnull
    private static MedievalUpgradeConstructionHelper instance = new MedievalUpgradeConstructionHelper();

    private MedievalUpgradeConstructionHelper() {
        super();
    }

    @Nonnull
    public static MedievalUpgradeConstructionHelper getInstance() {
        return instance;
    }


    /**
     * Method to build an armor extension that has no real special properties.
     *
     * @return A completely setup extension. However the caller will need to set the RegistryName and register it.
     */
    @Nonnull
    @Override
    public IMultiComponentArmorExtension buildStandardExtension(
      String translationKey,
      String textFormatting,
      IMultiComponentArmorExtensionPosition position,
      IDefaultCapabilitiesRetrievalCallback capabilitiesRetrievalCallback,
      IExtensionRecipeRetrievalCallback extensionRecipeRetrievalCallback)
    {
        return new MedievalArmorExtension.Builder(translationKey, textFormatting, position, capabilitiesRetrievalCallback, extensionRecipeRetrievalCallback).build();
    }

    /**
     * Method to build an armor extension that wraps an existing combination and a material.
     * Making the new extension material based. And setting the hasItemStack method to false on the depending extension.
     *
     * @return A completely setup extension that wraps the existing extension and a material. However the caller will need to set the RegistryNam and register it.
     */
    @Nonnull
    @Override
    public IMaterialDependantMultiComponentArmorExtension buildMaterialDependantExtension(
      String translationKey,
      String textFormatting,
      IMultiComponentArmorExtensionPosition position,
      IMaterializableMultiComponentArmorExtension materialIndependentArmorExtension,
      IAddonArmorMaterial material,
      IDefaultCapabilitiesRetrievalCallback capabilitiesRetrievalCallback)
    {
        return new MedievalMaterialDependantArmorExtension.Builder(translationKey,
          textFormatting,
          position,
          materialIndependentArmorExtension,
          material,
          capabilitiesRetrievalCallback).build();
    }
}
