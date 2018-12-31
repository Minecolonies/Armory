package com.ldtteam.armory.api.common.heatable;

import com.ldtteam.armory.api.common.material.core.IMaterial;
import net.minecraft.item.ItemStack;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/7/2017.
 */
public interface IHeatedObjectType extends IForgeRegistryEntry<IHeatedObjectType> {

    /**
     * Method to get the amount in millibuckets that this HeatedObjectType produces when it melts.
     * @return The amount in millibuckets produced when an HeatableObject of this type melts.
     */
    @Nonnull
    Integer getMoltenAmount();

    /**
     * Method to generate an instance of this HeatedObjectType for a given material.
     * @param material The Material to generate the Stack for.
     * @return An ItemStack with a HeatableObject of this type.
     * @throws IllegalArgumentException Thrown when this ObjectType is not capable of generating an instance.
     */
    @Nonnull
    ItemStack generateItemStackForMaterial(IMaterial material) throws IllegalArgumentException;

    /**
     * Returns the oredictionary prefix of this type.
     * @return The oredictionary prefix.
     */
    @Nonnull
    String getOreDictionaryPrefix();
}
