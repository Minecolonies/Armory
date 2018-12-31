package com.ldtteam.armory.compatibility.recipes.heated;

import com.google.common.collect.Lists;
import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.common.CapabilityHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class HeatedItemRecipeWrapper implements IRecipeWrapper
{

    private final ItemStack originalStack;
    private final List<ItemStack> heatedStacks;

    public HeatedItemRecipeWrapper(final HeatedItemRecipe recipe) {
        this.originalStack = recipe.getHeatableItemStack();
        this.heatedStacks = HeatedItemRecipeWrapper.generateHeatedStacks(this.originalStack);
    }

    /**
     * Gets all the recipe's ingredients by filling out an instance of {@link IIngredients}.
     *
     * @since JEI 3.11.0
     */
    @Override
    public void getIngredients(final IIngredients ingredients)
    {
        ingredients.setInput(ItemStack.class, originalStack);
        ingredients.setOutputs(ItemStack.class, heatedStacks);
    }

    public ItemStack getOriginalStack()
    {
        return originalStack;
    }

    public List<ItemStack> getHeatedStacks()
    {
        return heatedStacks;
    }

    private static List<ItemStack> generateHeatedStacks(@NotNull final ItemStack originalStack)
    {
        try {
            IMaterial material = IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().getStackData(originalStack).getThrid();

            final List<ItemStack> heatedStacks = Lists.newArrayList();

            for (int temp = 20; temp < material.getMeltingPoint(); temp++)
            {
                heatedStacks.add(IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().convertToHeatedIngot(originalStack, temp));
            }

            return heatedStacks;
        }
        catch (IllegalArgumentException ex)
        {
            return Lists.newArrayList();
        }
    }
}
