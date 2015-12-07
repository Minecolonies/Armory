package com.SmithsModding.Armory.API.Armor;

import net.minecraft.util.ResourceLocation;

/**
 * Created by Marc on 07.12.2015.
 */
public abstract class MaterialDependentMLAAddon extends MLAAddon {
    String iMaterialName;
    String iInternalName;

    public MaterialDependentMLAAddon (String pInternalName, String pMaterialName, String pParentName, String pAddonPositionID, ResourceLocation pModelTextureLocation) {
        this(pInternalName, pMaterialName, pParentName, pAddonPositionID, 1, pModelTextureLocation);
    }

    public MaterialDependentMLAAddon (String pInternalName, String pMaterialName, String pParentName, String pAddonPositionID, Integer pMaxInstalledAmount, ResourceLocation pModelTextureLocation) {
        super(pInternalName + "-" + pMaterialName, pParentName, pAddonPositionID, pMaxInstalledAmount, pModelTextureLocation);

        iInternalName = pInternalName;
        iMaterialName = pMaterialName;
    }

    @Override
    public boolean isMaterialDependent () {
        return true;
    }

    public String getMaterialName () {
        return iMaterialName;
    }

    public String getAddonInternalName () {
        return iInternalName;
    }
}
