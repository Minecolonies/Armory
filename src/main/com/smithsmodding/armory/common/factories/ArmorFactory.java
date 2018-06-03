package com.smithsmodding.armory.common.factories;
/*
/  StandardMLAFactory
/  Created by : Orion
/  Created on : 04/07/2014
*/

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.factories.IMLAFactory;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.util.common.armor.ArmorHelper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ArmorFactory implements IMLAFactory {
    private static ArmorFactory iInstance;

    public static ArmorFactory getInstance() {
        if (iInstance == null) iInstance = new ArmorFactory();
        return iInstance;
    }

    /**
     * Function used to modify an existing MLA.
     * The function will add the addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factories can take different environmental values in effect.
     *
     * @param baseStack          The already existing ItemStack on to which addons should be added
     * @param newAddons          The new addons stored in a HashMap, with as key the new addons and as Value the new installed amount.
     * @return An Itemstack containing your now modified armor.
     */
    @Nonnull
    @Override
    public ItemStack buildMLAArmor(ItemStack baseStack, List<IMultiComponentArmorExtensionInformation> newAddons)
    {
        if (!baseStack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            throw new IllegalArgumentException("The given armor base stack is not a Armor!");

        final IMultiComponentArmorCapability capability = baseStack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        final IMultiComponentArmor armor = capability.getArmorType();
        final ICoreArmorMaterial coreMaterial = capability.getMaterial();
        final List<IMultiComponentArmorExtensionInformation> existingExtensions = capability.getInstalledExtensions();
        final int currentMaximalDurability =
          ArmorHelper.getModifyableCapabilityValue(armor, coreMaterial, existingExtensions, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).intValue();
        final int currentUsedDurability = currentMaximalDurability - capability.getCurrentDurability();
        final String currentCustomName = getArmorCustomName(baseStack);

        if (!validateOldAgainstNewAddons(existingExtensions, newAddons))
            throw new IllegalArgumentException("ADDONS not compatible");

        final List<IMultiComponentArmorExtensionInformation> combinedExtensions = compressInformation(existingExtensions, newAddons);

        final int newMaximalDurability =
          ArmorHelper.getModifyableCapabilityValue(armor, coreMaterial, combinedExtensions, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).intValue();
        if (newMaximalDurability <= 0)
        {
            throw new IllegalArgumentException(String.format("Combination of armor, material and extensions results in a <= 0 durability: %s,%s, %s",
              armor,
              coreMaterial,
              combinedExtensions));
        }

        final int newCurrentDurability = newMaximalDurability - currentUsedDurability;

        ItemStack combinedArmor = buildNewMLAArmor(armor, coreMaterial, combinedExtensions);
        IMultiComponentArmorCapability newCapability = combinedArmor.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        newCapability.setCurrentDurability(newCurrentDurability);
        setArmorCustomName(baseStack, currentCustomName);

        return combinedArmor;
    }

    /**
     * Function used to create a new armor ItemStack
     *
     * @param armor The base armor used to create the new armor.
     * @param coreMaterial The core material of the armor.
     * @param addons    The new addons stored in a ArrayList
     * @return A new ItemStack with full durability.
     * @throws IllegalArgumentException When the Addons are not compatible, or when the durability is smaller or equal to 0
     */
    @Nonnull
    @Override
    public ItemStack buildNewMLAArmor(IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial, List<IMultiComponentArmorExtensionInformation> addons)
      throws IllegalArgumentException
    {
        if (!validateNewAgainstNewAddons(addons))
            throw new IllegalArgumentException("ADDONS not compatible");

        final int totalDurability = ArmorHelper.getModifyableCapabilityValue(armor, coreMaterial, addons, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).intValue();
        if (totalDurability <= 0)
        {
            throw new IllegalArgumentException(String.format("Combination of armor, material and extensions results in a <= 0 durability: %s,%s, %s", armor, coreMaterial, addons));
        }

        ItemStack armorStack =  new ItemStack(armor.getItem(), 1);
        IMultiComponentArmorCapability capability = new IMultiComponentArmorCapability.Impl()
                .setArmorType(armor)
                .setMaterial(coreMaterial)
                .setInstalledExtensions(addons)
                .setCurrentDurability(totalDurability);

        armorStack.getCapability(SmithsCoreCapabilityDispatcher.INSTANCE_CAPABILITY, null).getDispatcher().registerCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, capability);

        return armorStack;
    }

    /**
     * Gets the custom name of the Armor.
     *
     * @param stack The stack to get the custom name from.
     * @return THe custom name or null if not set.
     */
    @Nullable
    @Override
    public String getArmorCustomName(@Nonnull final ItemStack stack)
    {
        if (!stack.hasTagCompound())
        {
            return null;
        }

        if (!stack.getTagCompound().hasKey("customname"))
        {
            return null;
        }

        return stack.getTagCompound().getString("customname");
    }

    /**
     * Sets the custom name of the armor.
     *
     * @param stack      The stack to set the custom name on.
     * @param customName The custom name, null to remove.
     */
    @Override
    public void setArmorCustomName(@Nonnull final ItemStack stack, @Nullable final String customName)
    {
        if (customName == null)
        {
            if (stack.hasTagCompound())
            {
                stack.getTagCompound().removeTag("customname");
            }
        }
        else
        {
            if (!stack.hasTagCompound())
            {
                stack.setTagCompound(new NBTTagCompound());
            }

            stack.getTagCompound().setString("customname", customName);
        }
    }

    private boolean validateOldAgainstNewAddons(
      @Nonnull List<IMultiComponentArmorExtensionInformation> oldAddons,
      @Nonnull List<IMultiComponentArmorExtensionInformation> newAddons)
    {
        boolean continueCrafting = true;
        Iterator<IMultiComponentArmorExtensionInformation> installedIterator = oldAddons.iterator();

        while (continueCrafting && installedIterator.hasNext()) {
            IMultiComponentArmorExtensionInformation information = installedIterator.next();
            IMultiComponentArmorExtension extension = information.getExtension();

            Iterator<IMultiComponentArmorExtensionInformation> additionIterator = newAddons.iterator();
            while (continueCrafting && additionIterator.hasNext()) {
                IMultiComponentArmorExtensionInformation newInformation = additionIterator.next();
                IMultiComponentArmorExtension newExtension = newInformation.getExtension();

                if (extension == newExtension) {
                    if (information.getCount() + newInformation.getCount() > extension.getMaximalInstallationCount()) {
                        return false;
                    }
                } else {
                    continueCrafting = (extension.validateCrafting(newExtension, true) && newExtension.validateCrafting(extension, false));
                }
            }
        }

        return continueCrafting;
    }

    private List<IMultiComponentArmorExtensionInformation> compressInformation(
      List<IMultiComponentArmorExtensionInformation> oldExtensions,
      List<IMultiComponentArmorExtensionInformation> newExtensions)
    {
        HashMap<IMultiComponentArmorExtension, Integer> countMap = new HashMap<>();

        oldExtensions.forEach((i)-> {
            if (!countMap.containsKey(i.getExtension()))
                countMap.put(i.getExtension(), i.getCount());
        });

        newExtensions.forEach((i)-> {
            if (!countMap.containsKey(i.getExtension()))
                countMap.put(i.getExtension(), i.getCount());
            else
                countMap.put(i.getExtension(), countMap.get(i.getExtension()) + i.getCount());
        });

        ArrayList<IMultiComponentArmorExtensionInformation> compressedList = new ArrayList<>();

        countMap.forEach((e, c) -> {
            compressedList.add(new IMultiComponentArmorExtensionInformation.Impl()
                    .setExtension(e)
                    .setPosition(e.getPosition())
                    .setCount(c));
        });

        return compressedList;
    }

    private boolean validateNewAgainstNewAddons(@Nonnull List<IMultiComponentArmorExtensionInformation> newAddons)
    {
        boolean continueCrafting = true;
        Iterator<IMultiComponentArmorExtensionInformation> externalIterator = newAddons.iterator();

        while (continueCrafting && externalIterator.hasNext())
        {
            Iterator<IMultiComponentArmorExtensionInformation> internalIterator = newAddons.iterator();
            IMultiComponentArmorExtension externalExtension = externalIterator.next().getExtension();

            while (continueCrafting && internalIterator.hasNext())
            {
                IMultiComponentArmorExtension internalExtension = internalIterator.next().getExtension();
                continueCrafting = externalExtension.validateCrafting(internalExtension, false);
            }
        }

        return continueCrafting;
    }

}
