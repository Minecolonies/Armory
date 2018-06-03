package com.smithsmodding.armory.api.common.armor;

import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/3/2017.
 */
public interface IMaterialDependantMultiComponentArmorExtension extends IMultiComponentArmorExtension {

    /**
     * Getter for the material independent extension.
     *
     * @return The material independent extension.
     */
    @Nonnull
    IMaterializableMultiComponentArmorExtension getMaterialIndependentExtension();

    /**
     * Getter for the material this Extension is made from.
     * @return The extension is made from.
     */
    @Nonnull
    IAddonArmorMaterial getMaterial();
}
