package com.ldtteam.armory.common.armor.extension;

import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/21/2017.
 */
public class MedievalArmorExtensionPosition extends IForgeRegistryEntry.Impl<IMultiComponentArmorExtensionPosition> implements IMultiComponentArmorExtensionPosition {

    private final Integer armorLayer;

    public MedievalArmorExtensionPosition(Integer armorLayer) {
        this.armorLayer = armorLayer;
    }

    @Nonnull
    @Override
    public Integer getArmorLayer() {
        return armorLayer;
    }
}
