package com.ldtteam.armory.compatibility.recipes.anvil;

import com.ldtteam.armory.api.util.client.Textures;
import com.ldtteam.armory.api.util.client.TranslationKeys;
import com.ldtteam.armory.api.util.references.ModInventories;
import com.ldtteam.armory.api.util.references.ModItems;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.compatibility.JEICompatMod;
import com.ldtteam.smithscore.util.client.CustomResource;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IGuiIngredientGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlacksmithsAnvilRecipeCategory implements IRecipeCategory<BlackSmithsAnvilRecipeWrapper> {

    private static final CustomResource RESOURCE = Textures.Gui.Compatibility.JEI.ArmorsAnvil.GUI;

    private static final String LOCALENAME = I18n.format(TranslationKeys.Gui.JEI.AnvilRecipeName);
    private static final IDrawable BACKGROUND = JEICompatMod.getJeiHelpers().getGuiHelper().createDrawable(new ResourceLocation(RESOURCE.getPrimaryLocation()), RESOURCE.getU(), RESOURCE.getV(), RESOURCE.getWidth(), RESOURCE.getHeight());

    @Nonnull
    @Override
    public String getUid() {
        return References.Compatibility.JEI.RecipeTypes.ANVIL;
    }

    @Nonnull
    @Override
    public String getTitle() {
        return LOCALENAME;
    }

    @Override
    public String getModName()
    {
        return References.General.MOD_ID;
    }

    @Nonnull
    @Override
    public IDrawable getBackground() {
        return BACKGROUND;
    }

    /**
     * Optional icon for the category tab.
     * If no icon is defined here, JEI will use first item registered with ModRegistry#addRecipeCategoryCraftingItem(ItemStack, String...)
     *
     * @return icon to draw on the category tab, max size is 16x16 pixels.
     * @since 3.13.1
     */
    @Nullable
    @Override
    public IDrawable getIcon() {
        return null;
    }

    @Override
    public void drawExtras(@Nonnull Minecraft minecraft) {

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
    public void setRecipe(final IRecipeLayout recipeLayout, final BlackSmithsAnvilRecipeWrapper recipeWrapper, final IIngredients ingredients)
    {
        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
            int row = i / 5;
            int column = i % 5;

            recipeLayout.getItemStacks().init(i, true, 7 + column * 18, 7 + row * 18);
        }

        recipeLayout.getItemStacks().init(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, false, 137, 43);

        recipeLayout.getItemStacks().init(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS, true, 137, 7);
        recipeLayout.getItemStacks().init(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS, true, 137, 79);

        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
            recipeLayout.getItemStacks().init(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS + i, true, 51 + i * 22, 108);
        }

        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
            recipeLayout.getItemStacks().set(i, recipeWrapper.getInputs().get(i));
        }

        recipeLayout.getItemStacks().set(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, recipeWrapper.getOutputs().get(0));

        if (recipeWrapper.getHammerUsage() > 0)
            recipeLayout.getItemStacks().set(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS, new ItemStack(ModItems.IT_HAMMER, recipeWrapper.getHammerUsage()));

        if (recipeWrapper.getTongUsage() > 0)
            recipeLayout.getItemStacks().set(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS, new ItemStack(ModItems.IT_TONGS, recipeWrapper.getTongUsage()));

        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
            recipeLayout.getItemStacks().set(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS + i, recipeWrapper.getAdditionalStacks().get(i));
        }
    }

    /**
     * Get the tooltip for whatever's under the mouse.
     * ItemStack and fluid tooltips are already handled by JEI, this is for anything else.
     * <p>
     * To add to ingredient tooltips, see {@link IGuiIngredientGroup#addTooltipCallback(ITooltipCallback)}
     * To add tooltips for a recipe wrapper, see {@link IRecipeWrapper#getTooltipStrings(int, int)}
     *
     * @param mouseX the X position of the mouse, relative to the recipe.
     * @param mouseY the Y position of the mouse, relative to the recipe.
     * @return tooltip strings. If there is no tooltip at this position, return an empty list.
     *
     * @since JEI 4.2.5
     */
    @Override
    public List<String> getTooltipStrings(final int mouseX, final int mouseY)
    {
        return Collections.emptyList();
    }
}
