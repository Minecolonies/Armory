package com.ldtteam.armory.common.heatable;

import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/26/2017.
 */
public class HeatedObjectType extends IHeatedObjectType.Impl<IHeatedObjectType> implements IHeatedObjectType {

    private final Integer moltenAmount;
    private final String oreDicPrefix;

    public HeatedObjectType(Integer moltenAmount, String oreDicPrefix) {
        this.moltenAmount = moltenAmount;
        this.oreDicPrefix = oreDicPrefix;
    }

    /**
     * Method to get the amount in millibuckets that this HeatedObjectType produces when it melts.
     *
     * @return The amount in millibuckets produced when an HeatableObject of this type melts.
     */
    @Nonnull
    @Override
    public Integer getMoltenAmount() {
        return moltenAmount;
    }

    /**
     * Method to generate an instance of this HeatedObjectType for a given material.
     *
     * @param material The Material to generate the Stack for.
     * @return An ItemStack with a HeatableObject of this type.
     * @throws IllegalArgumentException Thrown when this ObjectType is not capable of generating an instance.
     */
    @Nonnull
    @Override
    public ItemStack generateItemStackForMaterial(IMaterial material) throws IllegalArgumentException {
        NonNullList<ItemStack> matches = OreDictionary.getOres(oreDicPrefix + material.getOreDictionaryIdentifier());

        if (matches.isEmpty())
            return ItemStack.EMPTY;

        return matches.get(0).copy();
    }

    /**
     * Returns the oredictionary prefix of this type.
     *
     * @return The oredictionary prefix.
     */
    @Nonnull
    @Override
    public String getOreDictionaryPrefix()
    {
        return this.oreDicPrefix;
    }
}
