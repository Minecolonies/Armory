package com.ldtteam.armory.compatibility;

import com.google.common.collect.Lists;
import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.util.references.ModBlocks;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.api.util.references.ModItems;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.client.gui.implementations.blacksmithsanvil.GuiBlacksmithsAnvil;
import com.ldtteam.armory.client.gui.implementations.fireplace.GuiFireplace;
import com.ldtteam.armory.client.gui.implementations.forge.GuiForge;
import com.ldtteam.armory.common.api.ArmoryAPI;
import com.ldtteam.armory.compatibility.recipes.anvil.BlackSmithsAnvilRecipeWrapper;
import com.ldtteam.armory.compatibility.recipes.anvil.BlacksmithsAnvilRecipeCategory;
import com.ldtteam.armory.compatibility.recipes.heated.HeatedItemRecipe;
import com.ldtteam.armory.compatibility.recipes.heated.HeatedItemRecipeCategory;
import com.ldtteam.armory.compatibility.recipes.heated.HeatedItemRecipeMaker;
import com.ldtteam.armory.compatibility.recipes.heated.HeatedItemRecipeWrapper;
import mezz.jei.api.*;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.ldtteam.armory.api.util.references.References.NBTTagCompoundData.TE.Anvil.MATERIAL;

/**
 * Author Orion (Created on: 21.06.2016)
 */
@JEIPlugin
public class JEICompatMod implements IModPlugin {

    private static IJeiHelpers HELPERS;

    public static IJeiHelpers getJeiHelpers() {
        return HELPERS;
    }

    @Override
    public void registerItemSubtypes(final ISubtypeRegistry subtypeRegistry)
    {
        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_HEATEDITEM, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_RING, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_CHAIN, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_NUGGET, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_PLATE, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_INGOT, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(ModItems.IT_COMPONENT, new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (!itemStack.isEmpty() && itemStack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
                {
                    return itemStack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null).toString();
                }

                return null;
            }
        });

        subtypeRegistry.registerSubtypeInterpreter(Item.getItemFromBlock(ModBlocks.BL_ANVIL), new ISubtypeRegistry.ISubtypeInterpreter()
        {
            @Nullable
            @Override
            public String apply(final ItemStack itemStack)
            {
                if (itemStack.isEmpty() || !itemStack.hasTagCompound() || !itemStack.getTagCompound().hasKey(MATERIAL))
                {
                    return null;
                }

                final IAnvilMaterial material =
                  ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(itemStack.getTagCompound().getString(MATERIAL)));
                return material.toString();
            }
        });
    }

    /**
     * Register the categories handled by this plugin.
     * These are registered before recipes so they can be checked for validity.
     *
     * @since JEI 4.5.0
     */
    @Override
    public void registerCategories(final IRecipeCategoryRegistration registry)
    {
        HELPERS = registry.getJeiHelpers();
        registry.addRecipeCategories(new BlacksmithsAnvilRecipeCategory());
        registry.addRecipeCategories(new HeatedItemRecipeCategory());
    }

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.handleRecipes(IAnvilRecipe.class, BlackSmithsAnvilRecipeWrapper::new, References.Compatibility.JEI.RecipeTypes.ANVIL);
        registry.addRecipes(Lists.newArrayList(IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry()), References.Compatibility.JEI.RecipeTypes.ANVIL);
        registry.addRecipeClickArea(GuiBlacksmithsAnvil.class, 17, 7, 30, 30, References.Compatibility.JEI.RecipeTypes.ANVIL);

        registry.handleRecipes(HeatedItemRecipe.class, HeatedItemRecipeWrapper::new, References.Compatibility.JEI.RecipeTypes.HEATEDITEM);
        registry.addRecipes(HeatedItemRecipeMaker.generateRecipes(), References.Compatibility.JEI.RecipeTypes.HEATEDITEM);
        registry.addRecipeClickArea(GuiForge.class, 80,44, 22,16, References.Compatibility.JEI.RecipeTypes.HEATEDITEM);

        NonNullList<ItemStack> anvils = NonNullList.create();
        ModBlocks.BL_ANVIL.getSubBlocks(ModBlocks.BL_ANVIL.getCreativeTabToDisplayOn(), anvils);
        for (ItemStack stack : anvils)
        {
            registry.addRecipeCatalyst(stack, References.Compatibility.JEI.RecipeTypes.ANVIL);
        }

        registry.addRecipeCatalyst(new ItemStack(ModBlocks.BL_FORGE), References.Compatibility.JEI.RecipeTypes.HEATEDITEM);
        registry.addRecipeCatalyst(new ItemStack(ModBlocks.BL_FIREPLACE), References.Compatibility.JEI.RecipeTypes.HEATEDITEM);
    }
}
