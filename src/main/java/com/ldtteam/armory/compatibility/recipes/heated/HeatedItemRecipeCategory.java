package com.ldtteam.armory.compatibility.recipes.heated;

import com.ldtteam.armory.api.util.client.Textures;
import com.ldtteam.armory.api.util.client.TranslationKeys;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.compatibility.JEICompatMod;
import com.ldtteam.smithscore.util.client.CustomResource;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class HeatedItemRecipeCategory implements IRecipeCategory<HeatedItemRecipeWrapper>
{
    private static final CustomResource RESOURCE   = Textures.Gui.Compatibility.JEI.HeatedItem.GUI;
    private static final String         LOCALENAME = I18n.format(TranslationKeys.Gui.JEI.HeatedItemName);
    private static final IDrawable BACKGROUND = JEICompatMod.getJeiHelpers().getGuiHelper().createDrawable(new ResourceLocation(RESOURCE.getPrimaryLocation()), RESOURCE.getU(), RESOURCE.getV(), RESOURCE.getWidth(), RESOURCE.getHeight());

    /**
     * Returns a unique ID for this recipe category.
     * Referenced from recipes to identify which recipe category they belong to.
     */
    @Override
    public String getUid()
    {
        return References.Compatibility.JEI.RecipeTypes.HEATEDITEM;
    }

    /**
     * Returns the localized name for this recipe type.
     * Drawn at the top of the recipe GUI pages for this category.
     */
    @Override
    public String getTitle()
    {
        return LOCALENAME;
    }

    /**
     * Return the mod name or id associated with this recipe category.
     * Used for the recipe category tab's tooltip.
     *
     * @since JEI 4.5.0
     */
    @Override
    public String getModName()
    {
        return References.General.MOD_ID;
    }

    /**
     * Returns the drawable background for a single recipe in this category.
     * <p>
     * The size of the background determines how recipes are laid out by JEI,
     * make sure it is the right size to contains everything being displayed.
     */
    @Override
    public IDrawable getBackground()
    {
        return BACKGROUND;
    }

    /**
     * Set the {@link IRecipeLayout} properties from the {@link IRecipeWrapper} and {@link IIngredients}.
     *
     * @param recipeLayout  the layout that needs its properties set.
     * @param recipeWrapper the recipeWrapper, for extra information.
     * @param ingredients   the ingredients, already set by the recipeWrapper
     * @since JEI 3.11.0
     */
    @Override
    public void setRecipe(final IRecipeLayout recipeLayout, final HeatedItemRecipeWrapper recipeWrapper, final IIngredients ingredients)
    {
        recipeLayout.getItemStacks().init(0, true, 7,7);
        recipeLayout.getItemStacks().init(1, true, 77, 7);

        recipeLayout.getItemStacks().set(0, recipeWrapper.getOriginalStack());
        recipeLayout.getItemStacks().set(1, recipeWrapper.getHeatedStacks());
    }
}
