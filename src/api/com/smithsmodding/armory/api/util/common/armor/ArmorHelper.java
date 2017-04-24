package com.smithsmodding.armory.api.util.common.armor;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
}
