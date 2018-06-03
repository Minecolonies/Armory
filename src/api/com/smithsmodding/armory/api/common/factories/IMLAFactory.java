package com.smithsmodding.armory.api.common.factories;
/*
/  IMLAFactory
/  Created by : Orion
/  Created on : 03/07/2014
*/

import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public interface IMLAFactory {
    /**
     * Function used to modify an existing MLA.
     * The function will add the addons given in the ArrayList pAddons.
     * Using pData you can pass in extra data so that your factories can take different environmental values in effect.
     *
     * @param baseStack The already existing ItemStack on to which addons should be added
     * @param newAddons     The new addons stored in a HashMap, with as key the new addons and as Value the new installed amount.
     * @return An Itemstack containing your now modified armor.
     */
    @Nonnull
    ItemStack buildMLAArmor(ItemStack baseStack, List<IMultiComponentArmorExtensionInformation> newAddons);

    /**
     * Function used to create a new armor ItemStack
     *
     * @param armor The base armor used to create the new armor.
     * @param coreMaterial The core material of the armor.
     * @param addons    The new addons stored in a ArrayList
     * @return A new ItemStack with full durability.
     * @throws IllegalArgumentException When the Addons are not compatible, or the durability <= 0
     */
    @Nonnull
    ItemStack buildNewMLAArmor(IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial, List<IMultiComponentArmorExtensionInformation> addons) throws IllegalArgumentException;

    /**
     * Gets the custom name of the Armor.
     *
     * @param stack The stack to get the custom name from.
     * @return THe custom name or null if not set.
     */
    @Nullable
    String getArmorCustomName(@Nonnull final ItemStack stack);

    /**
     * Sets the custom name of the armor.
     *
     * @param stack      The stack to set the custom name on.
     * @param customName The custom name, null to remove.
     */
    void setArmorCustomName(@Nonnull final ItemStack stack, @Nullable final String customName);
}
