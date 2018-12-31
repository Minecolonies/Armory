package com.ldtteam.armory.common.heatable;

import com.ldtteam.armory.api.common.capability.IHeatableObjectCapability;
import com.ldtteam.armory.api.common.capability.IHeatedObjectCapability;
import com.ldtteam.armory.api.common.heatable.IHeatableObject;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectOverrideManager;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.common.Triple;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.smithscore.util.common.helper.ItemStackHelper;
import gnu.trove.map.hash.TCustomHashMap;
import gnu.trove.strategy.HashingStrategy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/31/2017.
 */
public class HeatedObjectOverrideManager implements IHeatedObjectOverrideManager {

    private final static HeatedObjectOverrideManager INSTANCE = new HeatedObjectOverrideManager();

    public static HeatedObjectOverrideManager getInstance() {
        return INSTANCE;
    }

    private HeatedObjectOverrideManager() {}

    private final HashMap<Triple<IHeatableObject, IHeatedObjectType, IMaterial>, Object> overrideMap = new HashMap<>();
    private final TCustomHashMap<Object, Triple<IHeatableObject, IHeatedObjectType, IMaterial>> inverseOverrideMap = new TCustomHashMap<>(new HeatableObjectHashingStrategy());
    @Nonnull
    @Override
    public <T> T getHeatedOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull IMaterial material) {
        return (T) overrideMap.get(new Triple<>(object, type, material));
    }

    @Override
    public <T> void registerHeatedOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type, @Nonnull IMaterial material, @Nonnull T heated) {
        inverseOverrideMap.put(heated, new Triple<>(object, type, material));
        overrideMap.putIfAbsent(new Triple<>(object, type, material), heated);
    }

    @Nonnull
    @Override
    public Triple<IHeatableObject, IHeatedObjectType, IMaterial> getStackData(@Nonnull ItemStack stack) throws IllegalArgumentException {
        if (!isHeatable(stack))
            throw new IllegalArgumentException("Not a Heatable stack!");

        if (stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null)) {
            IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
            return new Triple<>(capability.getObject(), capability.getType(), capability.getMaterial());
        } else if (stack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null)) {
            IHeatableObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null);
            return new Triple<>(capability.getObject(), capability.getType(), capability.getMaterial());
        } else if (isOverride(stack)) {
            return inverseOverrideMap.get(stack);
        }

        throw new IllegalArgumentException("Not a Heatable stack!");
    }

    @Override
    public boolean hasOverride(@Nonnull IHeatableObject object, @Nonnull IHeatedObjectType type,  @Nonnull IMaterial material) {
        return overrideMap.containsKey(new Triple<>(object, type, material));
    }

    @Override
    public boolean isOverride(@Nonnull ItemStack stack) {
        return inverseOverrideMap.containsKey(stack);
    }

    @Override
    public boolean isHeatable(@Nonnull ItemStack stack) {
        return stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null) || stack.hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null) || isOverride(stack);
    }

    private class HeatableObjectHashingStrategy implements HashingStrategy<Object> {

        public int computeHashCode (Object object) {
            if (object instanceof ItemStack)
            {
                final ItemStack stack = (ItemStack) object;
                final ItemStack workingStack = stack.copy();
                workingStack.setCount(1);
                if (workingStack.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    workingStack.setItemDamage(0);
                }

                return workingStack.writeToNBT(new NBTTagCompound()).toString().hashCode();
            }

            return object.hashCode();
        }


        public boolean equals (Object o1, Object o2) {
            if (o1 instanceof ItemStack && o2 instanceof ItemStack)
            {
                final ItemStack s1 = (ItemStack) o1;
                final ItemStack s2 = (ItemStack) o2;

                final ItemStack c1 = s1.copy();
                final ItemStack c2 = s2.copy();

                c1.setCount(1);
                c2.setCount(1);

                if(c1.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    c1.setItemDamage(0);
                }
                if(c2.getItemDamage() == OreDictionary.WILDCARD_VALUE)
                {
                    c2.setItemDamage(0);
                }

                return c1.writeToNBT(new NBTTagCompound()).toString().equals(c2.writeToNBT(new NBTTagCompound()).toString());
            }


            return o1.equals(o2);
        }
    }

    private static class HeatableItemStackHashingStrategy implements HashingStrategy<ItemStack> {

        public static final HeatableItemStackHashingStrategy INSTANCE = new HeatableItemStackHashingStrategy();

        public int computeHashCode (ItemStack object) {
            int hash = object.getItem().hashCode() ^ object.getMetadata();

            if (object.getTagCompound() != null)
                hash ^= object.getTagCompound().hashCode();

            return hash;
        }


        public boolean equals (ItemStack o1, ItemStack o2) {
            return ItemStackHelper.equalsIgnoreStackSize(o1, o2);
        }
    }
}
