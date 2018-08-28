package com.ldtteam.armory.api.util.common.armor;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.ldtteam.armory.api.common.capability.IMultiComponentArmorCapability;
import com.ldtteam.armory.api.common.capability.armor.IValueModifyingCapability;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

/**
 * Helper class used to handle the retrieving of Armor data from Items, Models, etc.
 */
public class ArmorHelper {

    private ArmorHelper()
    {
        throw new IllegalStateException("UtilityClass");
    }

    /**
     * Method to get an {@link IMultiComponentArmor} for a given model.
     * Tries to retrieve the ItemLocation from the given modellocation and then the armor from that.
     * @param modelLocation The model location to retrieve the Armor for.
     * @return The {@link IMultiComponentArmor} for the given model.
     */
    @Nullable
    public static IMultiComponentArmor getArmorForModel(@Nonnull ResourceLocation modelLocation) {
        String[] pathComponents = modelLocation.getResourcePath().split("/");
        String nameComponent = pathComponents[pathComponents.length - 1].substring(0, pathComponents[pathComponents.length - 1].lastIndexOf("."));

        ResourceLocation itemLocation = new ResourceLocation(modelLocation.getResourceDomain(), nameComponent);

        return getArmorForItemName(itemLocation);
    }

    /**
     * Method used to get the {@link IMultiComponentArmor} for a given Item
     * @param itemIn The {@link Item} you wish to retrieve the armor for.
     * @return The {@link IMultiComponentArmor} registered with a given Item.
     */
    @Nullable
    public static IMultiComponentArmor getArmorForItem(@Nonnull Item itemIn) {
        return getArmorForItemName(itemIn.getRegistryName());
    }

    /**
     * Method used to get the {@link IMultiComponentArmor} for a given ItemName
     * @param itemName The name of the Item you wish to retrieve the armor for.
     * @return The {@link IMultiComponentArmor} that is registered with an Item with the given name.
     */
    @Nullable
    public static IMultiComponentArmor getArmorForItemName(@Nonnull ResourceLocation itemName) {
        for (IMultiComponentArmor armor : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry()) {
            if (armor.getItem().getRegistryName().equals(itemName)) {
                return armor;
            }
        }

        return null;
    }

    @Nonnull
    public static <V extends Number, C extends IValueModifyingCapability<C, V>> Number getModifyableCapabilityValue(
      @Nonnull final ItemStack stack,
      @Nonnull final Capability<C> capability)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Stack is not a armor");
        }

        final IMultiComponentArmorCapability stackCapability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        return getModifyableCapabilityValue(stackCapability.getArmorType(), stackCapability.getMaterial(), stackCapability.getInstalledExtensions(), capability);
    }

    @Nonnull
    public static <V extends Number, C extends IValueModifyingCapability<C, V>> Number getModifyableCapabilityValue(
      @Nonnull final IMultiComponentArmor armor,
      @Nonnull final ICoreArmorMaterial material,
      @Nonnull final List<IMultiComponentArmorExtensionInformation> extensionInformationList,
      @Nonnull final Capability<C> capability)
    {
        Number value = 0d;

        if (armor.hasCapability(capability, null))
        {
            value = armor.getCapability(capability, null).getValue();

            if (material.hasCapability(capability, null))
            {
                value = material.getCapability(capability, null).apply(value);
            }
        }

        for (IMultiComponentArmorExtensionInformation extensionInformation : extensionInformationList)
        {
            final IMultiComponentArmorExtension extension = extensionInformation.getExtension();

            Function<Number, Number> extensionValueProcessor = Function.identity();
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
            {
                final IMaterialDependantMultiComponentArmorExtension iMaterialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                final IAddonArmorMaterial addonArmorMaterial = iMaterialDependantMultiComponentArmorExtension.getMaterial();

                if (addonArmorMaterial.hasCapability(capability, null))
                {
                    extensionValueProcessor = addonArmorMaterial.getCapability(capability, null)::apply;
                }
            }

            if (extension.hasCapability(capability, null))
            {
                final C extensionCapabilityInstance = extension.getCapability(capability, null);
                value = extensionCapabilityInstance.getType().apply(value, extensionValueProcessor.apply(extensionCapabilityInstance.getValue()));
            }
        }

        return value;
    }
}
