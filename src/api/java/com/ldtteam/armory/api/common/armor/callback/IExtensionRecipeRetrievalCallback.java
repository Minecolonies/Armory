package com.ldtteam.armory.api.common.armor.callback;

import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;

import javax.annotation.Nullable;

/**
 * Interface used tro get the recipe if an extension via a callback.
 */
public interface IExtensionRecipeRetrievalCallback {

    /**
     * Method used during initialization to register a Recipe to create this Extension.
     * Method is only called when hasItemStack is true.
     * Returning null will cause this extension to not have a recipe. Eg. Creative Extensions.
     *
     * @param extension The extension the recipe should be created for.
     *
     * @return The recipe for this extension if it has one. Null else.
     */
    @Nullable
    IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension);

    /**
     * Method used during initialization to register a Recipe that is used to attach the extension to the given armor
     * of the given ICoreArmorMaterial
     * @param extension
     * @param armor
     * @param coreMaterial
     * @return
     */
    @Nullable
    IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial);
}
