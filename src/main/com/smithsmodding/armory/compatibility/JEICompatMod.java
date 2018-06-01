package com.smithsmodding.armory.compatibility;

import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.gui.implementations.blacksmithsanvil.GuiBlacksmithsAnvil;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.armory.compatibility.recipes.anvil.BlackSmithsAnvilRecipeWrapper;
import com.smithsmodding.armory.compatibility.recipes.anvil.BlacksmithsAnvilRecipeCategory;
import mezz.jei.api.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.smithsmodding.armory.api.util.references.References.NBTTagCompoundData.TE.Anvil.MATERIAL;

/**
 * Author Orion (Created on: 21.06.2016)
 */
@JEIPlugin
public class JEICompatMod extends BlankModPlugin {

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

    @Override
    public void register(@Nonnull IModRegistry registry) {
        HELPERS = registry.getJeiHelpers();

        registry.addRecipeCategories(new BlacksmithsAnvilRecipeCategory());
        registry.handleRecipes(IAnvilRecipe.class, BlackSmithsAnvilRecipeWrapper::new, References.Compatibility.JEI.RecipeTypes.ANVIL);
        registry.addRecipes(Lists.newArrayList(IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry()), References.Compatibility.JEI.RecipeTypes.ANVIL);
        registry.addRecipeClickArea(GuiBlacksmithsAnvil.class, 17, 7, 30, 30, References.Compatibility.JEI.RecipeTypes.ANVIL);

        NonNullList<ItemStack> anvils = NonNullList.create();
        ModBlocks.BL_ANVIL.getSubBlocks(null, anvils);

        for (ItemStack stack : anvils)
            registry.addRecipeCategoryCraftingItem(stack, References.Compatibility.JEI.RecipeTypes.ANVIL);

/*        getJeiHelpers().getNbtIgnoreList().ignoreNbtTagNames(References.NBTTagCompoundData.HeatedObject.CURRENTTEMPERATURE);
        getJeiHelpers().getNbtIgnoreList().ignoreNbtTagNames(References.NBTTagCompoundData.HeatedObject.ORIGINALITEM);
        getJeiHelpers().getNbtIgnoreList().ignoreNbtTagNames(CoreReferences.NBT.IItemProperties.TARGET);*/
    }
}
