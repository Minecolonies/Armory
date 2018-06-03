package com.smithsmodding.armory.api.common.capability.armor;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

/**
 * Created by marcf on 1/15/2017.
 */
public interface IArmorToughnessCapability extends IValueModifyingCapability<IArmorToughnessCapability, Float>
{

    /**
     * Creates a new {@link IArmorDefenceCapability} instance.
     *
     * @param value The value.
     * @return The instance.
     */
    static IArmorToughnessCapability add(final float value)
    {
        return create(value, ValueModificationType.ADD);
    }

    /**
     * Creates a new {@link IArmorToughnessCapability} instance.
     *
     * @param value The value.
     * @return The instance.
     */
    static IArmorToughnessCapability create(final float value, final ValueModificationType type)
    {
        return new IArmorToughnessCapability.Impl(value, type);
    }

    /**
     * Creates a new {@link IArmorDefenceCapability} instance.
     *
     * @param value The value.
     * @return The instance.
     */
    static IArmorToughnessCapability multiply(final float value)
    {
        return create(value, ValueModificationType.MULTIPLY);
    }

    /**
     * Creates a new {@link IArmorDefenceCapability} instance.
     *
     * @param value The value.
     * @return The instance.
     */
    static IArmorToughnessCapability set(final float value)
    {
        return create(value, ValueModificationType.SET);
    }

    class Storage implements Capability.IStorage<IArmorToughnessCapability>
    {

        /**
         * Serialize the capability instance to a NBTTag.
         * This allows for a central implementation of saving the data.
         * <p>
         * It is important to note that it is up to the API defining
         * the capability what requirements the 'instance' value must have.
         * <p>
         * Due to the possibility of manipulating internal data, some
         * implementations MAY require that the 'instance' be an instance
         * of the 'default' implementation.
         * <p>
         * Review the API docs for more info.
         *
         * @param capability The Capability being stored.
         * @param instance   An instance of that capabilities interface.
         * @param side       The side of the object the instance is associated with.
         * @return a NBT holding the data. Null if no data needs to be stored.
         */
        @Override
        public NBTBase writeNBT(Capability<IArmorToughnessCapability> capability, IArmorToughnessCapability instance, EnumFacing side) {
            final NBTTagCompound compound = new NBTTagCompound();
            compound.setFloat("value", instance.getValue());
            compound.setInteger("type", instance.getType().ordinal());

            return compound;
        }

        /**
         * Read the capability instance from a NBT tag.
         * <p>
         * This allows for a central implementation of saving the data.
         * <p>
         * It is important to note that it is up to the API defining
         * the capability what requirements the 'instance' value must have.
         * <p>
         * Due to the possibility of manipulating internal data, some
         * implementations MAY require that the 'instance' be an instance
         * of the 'default' implementation.
         * <p>
         * Review the API docs for more info.         *
         *
         * @param capability The Capability being stored.
         * @param instance   An instance of that capabilities interface.
         * @param side       The side of the object the instance is associated with.
         * @param nbt        A NBT holding the data. Must not be null, as doesn't make sense to call this function with nothing to read...
         */
        @Override
        public void readNBT(Capability<IArmorToughnessCapability> capability, IArmorToughnessCapability instance, EnumFacing side, NBTBase nbt) {
            final NBTTagCompound compound = (NBTTagCompound) nbt;

            instance.setValue(compound.getFloat("value"));
            instance.setType(ValueModificationType.values()[compound.getInteger("type")]);
        }
    }

    class Impl extends IValueModifyingCapability.Impl<IArmorToughnessCapability, Float> implements IArmorToughnessCapability
    {
        public Impl()
        {
            this(0f, ValueModificationType.ADD);
        }

        public Impl(final float value, final ValueModificationType type)
        {
            setValue(value).setType(type);
        }
    }
}
