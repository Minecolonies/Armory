package com.ldtteam.armory.api.common.material.armor;

import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.common.capability.armor.IArmorCapability;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/24/2017.
 */
public interface ICoreArmorMaterialDataCallback {

    /**
     * Method to get all the default capabilities this ArmorMaterial provides.
     * @return All the default capabilities this ArmorMaterial provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> getOverrideCoreMaterialCapabilities(IMultiComponentArmor armor);
}
