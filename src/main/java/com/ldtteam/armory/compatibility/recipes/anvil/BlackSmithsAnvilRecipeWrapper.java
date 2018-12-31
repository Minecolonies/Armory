package com.ldtteam.armory.compatibility.recipes.anvil;

import com.ldtteam.armory.api.common.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.util.common.ItemStackHelper;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.api.util.references.ModInventories;
import com.ldtteam.armory.common.factories.HeatedItemFactory;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author Orion (Created on: 21.06.2016)
 */
public class BlackSmithsAnvilRecipeWrapper implements IRecipeWrapper
{

    int hammerUsage = -1;
    int tongUsage = -1;
    private ItemStack[] inputs;
    private ItemStack[] additionalStacks;
    private ArrayList<ItemStack> output;

    public BlackSmithsAnvilRecipeWrapper(@Nonnull IAnvilRecipe recipe) {
        inputs = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS];

        ItemStackHelper.InitializeItemStackArray(inputs);
        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; i++) {
            IAnvilRecipeComponent component = recipe.getComponent(i);
            if (component == null)
                continue;

            inputs[i] = component.getComponentTargetStack();
        }

        additionalStacks = new ItemStack[ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS];
        ItemStackHelper.InitializeItemStackArray(additionalStacks);
        for (int i = 0; i < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; i++) {
            IAnvilRecipeComponent component = recipe.getAdditionalComponent(i);
            if (component == null)
                continue;

            additionalStacks[i] = component.getComponentTargetStack();
        }

        output = new ArrayList<>();
        ItemStack recipeResult = recipe.getResult(inputs, additionalStacks);
        if (recipeResult != null) {
            output.add(recipeResult);

            if (recipeResult.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null)) {
                ItemStack heatedVariant = HeatedItemFactory.getInstance().convertToHeatedIngot(recipeResult);
                output.add(heatedVariant);
            }
        }

        if (output.size() == 0)
            throw new IllegalArgumentException("Given recipe has no output!");

        if (recipe.getUsesHammer())
            hammerUsage = recipe.getHammerUsage();

        if (recipe.getUsesTongs())
            tongUsage = recipe.getTongsUsage();
    }

    @Nonnull
    public List<ItemStack> getInputs() {
        return Arrays.asList(inputs);
    }

    @Nonnull
    public List<ItemStack> getOutputs() {
        return output;
    }

    @Nonnull
    public List<ItemStack> getAdditionalStacks() {
        return Arrays.asList(additionalStacks);
    }

    public int getHammerUsage() {
        return hammerUsage;
    }

    public int getTongUsage() {
        return tongUsage;
    }

    /**
     * Gets all the recipe's ingredients by filling out an instance of {@link IIngredients}.
     *
     * @param ingredients
     * @since JEI 3.11.0
     */
    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(ItemStack.class, getInputs());
        ingredients.setOutputs(ItemStack.class, getOutputs());
    }
}
