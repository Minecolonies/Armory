package com.ldtteam.armory.compatibility.recipes.heated;

import net.minecraft.item.ItemStack;

public class HeatedItemRecipe
{

    private final ItemStack heatableItemStack;

    public HeatedItemRecipe(final ItemStack heatableItemStack) {this.heatableItemStack = heatableItemStack;}

    public ItemStack getHeatableItemStack()
    {
        return heatableItemStack;
    }
}
