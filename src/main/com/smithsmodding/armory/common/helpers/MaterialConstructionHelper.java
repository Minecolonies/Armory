package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.common.armor.callback.ICapabilityMapBuilder;
import com.smithsmodding.armory.api.common.armor.callback.IDefaultCapabilitiesRetrievalCallback;
import com.smithsmodding.armory.api.common.armor.callback.SimpleCapabilityMapBuilder;
import com.smithsmodding.armory.api.common.capability.armor.ArmorCapabilityManager;
import com.smithsmodding.armory.api.common.helpers.IMaterialConstructionHelper;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.common.material.MedievalAddonArmorMaterial;
import com.smithsmodding.armory.common.material.MedievalAnvilMaterial;
import com.smithsmodding.armory.common.material.MedievalCoreArmorMaterial;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public class MaterialConstructionHelper implements IMaterialConstructionHelper {

    @Nonnull
    private static MaterialConstructionHelper instance = new MaterialConstructionHelper();

    private MaterialConstructionHelper() {
    }

    @Nonnull
    public static MaterialConstructionHelper getInstance() {
        return instance;
    }

    @Nonnull
    @Override
    public IAnvilMaterial createMedievalAnvilMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, Integer durability) {
        return new MedievalAnvilMaterial(translationKey, textFormatting, oreDictionaryIdentifier, meltingPoint, vaporizingPoint, meltingTime, vaporizingTime, heatCoefficient, durability);
    }

    @Nonnull
    @Override
    public ICoreArmorMaterial createMedievalCoreArmorMaterial(
      String translationKey,
      String textFormatting,
      String oreDictionaryIdentifier,
      Float meltingPoint,
      Float vaporizingPoint,
      Integer meltingTime,
      Integer vaporizingTime,
      Float heatCoefficient,
      IDefaultCapabilitiesRetrievalCallback callback)
    {
        final ICapabilityMapBuilder capabilityMapBuilder = new SimpleCapabilityMapBuilder();
        callback.get(capabilityMapBuilder);

        return new MedievalCoreArmorMaterial(translationKey,
          textFormatting,
          oreDictionaryIdentifier,
          meltingPoint,
          vaporizingPoint,
          meltingTime,
          vaporizingTime,
          heatCoefficient,
          new ArmorCapabilityManager(capabilityMapBuilder));
    }

    @Nonnull
    @Override
    public IAddonArmorMaterial createMedievalAddonArmorMaterial(
      String translationKey,
      String textFormatting,
      String oreDictionaryIdentifier,
      Float meltingPoint,
      Float vaporizingPoint,
      Integer meltingTime,
      Integer vaporizingTime,
      Float heatCoefficient,
      IDefaultCapabilitiesRetrievalCallback callback)
    {
        final ICapabilityMapBuilder capabilityMapBuilder = new SimpleCapabilityMapBuilder();
        callback.get(capabilityMapBuilder);

        return new MedievalAddonArmorMaterial(translationKey,
          textFormatting,
          oreDictionaryIdentifier,
          meltingPoint,
          vaporizingPoint,
          meltingTime,
          vaporizingTime,
          heatCoefficient,
          new ArmorCapabilityManager(capabilityMapBuilder));
    }
}
