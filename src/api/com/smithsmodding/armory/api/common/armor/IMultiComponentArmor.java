package com.smithsmodding.armory.api.common.armor;

import com.smithsmodding.armory.api.client.armor.IInWorldRenderableArmorComponent;
import com.smithsmodding.armory.api.util.client.ITranslateable;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by marcf on 1/2/2017.
 */
public interface IMultiComponentArmor extends IForgeRegistryEntry<IMultiComponentArmor>, ITranslateable, ICapabilityProvider, IInWorldRenderableArmorComponent<IMultiComponentArmor>
{

    /**
     * Method to get all possible extension positions for this Armor.
     * @return The positions on the armor where an extension can be installed.
     */
    @Nonnull
    List<IMultiComponentArmorExtensionPosition> getPossibleExtensionPositions();

    /**
     * Method to get all possible extensions for this armor.
     * @return The extensions that can be installed on this armor.
     */
    @Nonnull
    List<IMultiComponentArmorExtension> getPossibleExtensions();

    /**
     * Method to get the Item for the Armor.
     * @return The Item for the Armor.
     */
    Item getItem();

    /**
     * The index of the Equipmentslot.
     * 0-5 Are reserved for the normal Vanilla equipment slots.
     *
     * @return The equipmentslot.
     */
    EntityEquipmentSlot getEquipmentSlot();
}
