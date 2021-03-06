package com.ldtteam.armory.common.crafting.blacksmiths.recipe;

import com.google.common.collect.Lists;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.ldtteam.armory.api.common.capability.IArmorComponentStackCapability;
import com.ldtteam.armory.api.common.capability.IMultiComponentArmorCapability;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.IAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.api.util.references.ModInventories;
import com.ldtteam.armory.api.util.references.ModItems;
import com.ldtteam.armory.common.factories.ArmorFactory;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Orion
 * Created on 28.05.2015
 * 21:24
 * <p>
 * Copyrighted according to Project specific license
 */
public class ArmorUpgradeAnvilRecipe extends AnvilRecipe {
    private IMultiComponentArmor armor;
    private ICoreArmorMaterial coreArmorMaterial;

    @Nonnull
    private ArrayList<Integer> upgradeComponents = new ArrayList<Integer>(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS);

    public ArmorUpgradeAnvilRecipe(IMultiComponentArmor armor, ICoreArmorMaterial coreArmorMaterial) {
        this.armor = armor;
        this.coreArmorMaterial = coreArmorMaterial;
    }

    @Override
    public boolean matchesRecipe(@Nonnull ItemStack[] craftingSlotContents, @Nonnull ItemStack[] additionalSlotContents, int hammerUsagesLeft, int tongsUsagesLeft) {
        ItemStack armorStack = craftingSlotContents[12];

        if (!armorStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            return false;

        IMultiComponentArmorCapability capability = armorStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        if (!capability.getArmorType().equals(armor))
            return false;

        if (!capability.getMaterial().equals(coreArmorMaterial))
            return false;

        if (hammerUsagesLeft == 0)
            hammerUsagesLeft = 150;

        if (tongsUsagesLeft == 0)
            tongsUsagesLeft = 150;

        if ((getUsesHammer()) && (hammerUsagesLeft) < getHammerUsage())
            return false;

        if ((getUsesTongs()) && (tongsUsagesLeft < getTongsUsage()))
            return false;

        if (craftingSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return false;
        }

        if (additionalSlotContents.length > ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS) {
            return false;
        }

        for (int slot = 0; slot < ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS; slot++) {
            if (slot == 12)
                continue;

            ItemStack slotContent = craftingSlotContents[slot];

            if (slotContent != null && !slotContent.isEmpty()) {
                if (getComponents()[slot] == null) {
                    return false;
                } else if (!getComponent(slot).isValidComponentForSlot(slotContent)) {
                    return false;
                }
            } else if (getComponents()[slot] != null) {
                return false;
            }
        }

        for (int slot = 0; slot < ModInventories.TileEntityBlackSmithsAnvil.MAX_ADDITIONALSLOTS; slot++) {
            ItemStack slotContent = additionalSlotContents[slot];

            if (slotContent != null && !slotContent.isEmpty()) {
                if (getAdditionalComponents()[slot] == null) {
                    return false;
                } else if (!getAdditionalComponents()[slot].isValidComponentForSlot(slotContent)) {
                    return false;
                }
            } else if (getAdditionalComponents()[slot] != null) {
                return false;
            }
        }

        try {
            ItemStack newArmorStack = buildItemStack(craftingSlotContents, additionalSlotContents);
            return newArmorStack != null;
        } catch (IllegalArgumentException argEx) {
            return false;
        }
    }

    @Nullable
    @Override
    public IAnvilRecipeComponent getComponent(int componentIndex) {
        if (componentIndex == 12) {
            return new StandardAnvilRecipeComponent(new ItemStack(ModItems.IT_RING)) {
                @Nullable
                @Override
                public ItemStack getComponentTargetStack() {
                    return ArmorFactory.getInstance().buildNewMLAArmor(armor, coreArmorMaterial, Lists.newArrayList());
                }

                @Override
                public int getResultingStackSizeForComponent(ItemStack pComponentStack) {
                    return 0;
                }
            };
        }

        if (componentIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        return getComponents()[componentIndex];
    }

    @Nullable
    @Override
    public ArmorUpgradeAnvilRecipe setCraftingSlotContent(int slotIndex, IAnvilRecipeComponent component) {
        if (slotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (slotIndex == 12)
            return null;

        getComponents()[slotIndex] = component;

        return this;
    }

    @Nullable
    public ArmorUpgradeAnvilRecipe setUpgradeCraftingSlotComponent(int pSlotIndex, IAnvilRecipeComponent pComponent) {
        if (pSlotIndex >= ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS) {
            return null;
        }

        if (pSlotIndex == 12)
            return null;

        if (!upgradeComponents.contains(pSlotIndex))
            upgradeComponents.add(pSlotIndex);

        return setCraftingSlotContent(pSlotIndex, pComponent);
    }

    @Nullable
    @Override
    public ItemStack getResult(ItemStack[] craftingSlotContents, ItemStack[] additionalSlotContents) {
        return buildItemStack(craftingSlotContents, additionalSlotContents);
    }

    private ItemStack buildItemStack(ItemStack[] craftingSlotContents, ItemStack[] additionalSlotContents) {
        ItemStack armorStack = craftingSlotContents[12];

        if (!armorStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            throw new IllegalArgumentException("ArmorStack is not Armor");

        List<IMultiComponentArmorExtensionInformation> newExtensionInformationData = new ArrayList<>();

        for (Integer index : upgradeComponents) {
            ItemStack upgradeStack = craftingSlotContents[index];

            if (!upgradeStack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
                throw new IllegalArgumentException("ADDONS not a Addon");

            IArmorComponentStackCapability upgradeCapability = upgradeStack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null);
            newExtensionInformationData.add(new IMultiComponentArmorExtensionInformation.Impl().setExtension(upgradeCapability.getExtension())
                    .setPosition(upgradeCapability.getExtension().getPosition())
                    .setCount(upgradeStack.getCount()));
        }

        return ArmorFactory.getInstance().buildMLAArmor(armorStack, newExtensionInformationData);
    }
}
