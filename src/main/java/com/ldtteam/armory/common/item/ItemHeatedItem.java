package com.ldtteam.armory.common.item;
/*
/  ItemHeatedItem
/  Created by : Orion
/  Created on : 03/10/2014
*/

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.capability.IHeatedObjectCapability;
import com.ldtteam.armory.api.common.heatable.IHeatableObject;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.client.TranslationKeys;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.api.util.references.ModCreativeTabs;
import com.ldtteam.armory.api.util.references.ModHeatableObjects;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.config.ArmoryConfig;
import com.ldtteam.armory.common.entity.EntityItemHeatable;
import com.ldtteam.armory.common.factories.HeatedItemFactory;
import com.ldtteam.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.ldtteam.smithscore.util.CoreReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class ItemHeatedItem extends Item {

    public ItemHeatedItem() {
        setMaxStackSize(1);
        setCreativeTab(ModCreativeTabs.HEATEDITEM);
        setUnlocalizedName(References.InternalNames.Items.IN_HEATEDINGOT);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.IN_HEATEDINGOT);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return false;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return 0d;

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        return (capability.getTemperature() / capability.getMaterial().getMeltingPoint());
    }

    @Override
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return;

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

        String temperatureLine = I18n.format(TranslationKeys.Items.HeatedIngot.TK_TAG_TEMPERATURE);
        temperatureLine = temperatureLine + ": " + Math.round(capability.getTemperature());

        tooltip.add(temperatureLine);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tabs, NonNullList<ItemStack> list)
    {
        if (tabs != getCreativeTab())
            return;

        HashMap<String, ItemStack> heatedItems = new HashMap<>();

        for (IHeatedObjectType type : IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry()) {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().forEach(new MaterialItemStackConstructionConsumer(type, heatedItems));
        }

        Set<String> emptyIds = heatedItems.keySet()
          .stream()
          .filter(id -> heatedItems.get(id).isEmpty())
          .collect(Collectors.toSet());

        emptyIds
          .forEach(heatedItems::remove);

        list.addAll(heatedItems.values());
    }

    @Override
    public boolean getHasSubtypes()
    {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(@Nonnull ItemStack stack)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
        {
            return super.getFontRenderer(stack);
        }

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        if (capability.getOriginalStack() == null)
        {
            return Minecraft.getMinecraft().fontRenderer;
        }

        return capability.getOriginalStack().getItem().getFontRenderer(capability.getOriginalStack());
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))
            return "";

        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        if (capability.getOriginalStack() == null)
        {
            return "NO ORIGINAL STACK FOUND ERROR!";
        }

        return capability.getOriginalStack().getItem().getItemStackDisplayName(capability.getOriginalStack());
    }

    @Override
    public boolean hasCustomEntity(final ItemStack stack)
    {
        return true;
    }

    @Nullable
    @Override
    public Entity createEntity(final World world, final Entity location, final ItemStack itemstack)
    {
        return new EntityItemHeatable(world, location.getPosition().getX(), location.getPosition().getY(), location.getPosition().getZ(), itemstack);
    }

    @Override
    public void onUpdate(@Nonnull ItemStack stack, World worldObj, Entity entity, int slotIndex, boolean selected) {

        if (!(entity instanceof EntityPlayer))
            return;

        if (!ArmoryConfig.enableTemperatureDecay)
            return;

        EntityPlayer player = (EntityPlayer) entity;
        IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);
        IHeatableObject object = capability.getObject();

        capability.increaseTemperatur(object.getChangeFactorForEntity(player, capability));

        if (capability.getTemperature() < 20F) {
            player.inventory.mainInventory.set(slotIndex, HeatedItemFactory.getInstance().convertToCooledIngot(stack));
        } else {
            for (ItemStack inventoryStack : player.inventory.mainInventory) {
                if (inventoryStack.isEmpty())
                    continue;

                if (inventoryStack.getItem() instanceof ItemTongs)
                    return;
            }

            player.setFire(1);
        }
    }

    /**
     * Determine if the player switching between these two item stacks
     *
     * @param oldStack    The old stack that was equipped
     * @param newStack    The new stack
     * @param slotChanged If the current equipped slot was changed,
     *                    Vanilla does not play the animation if you switch between two
     *                    slots that hold the exact same item.
     * @return True to play the item change animation
     */
    @Override
    public boolean shouldCauseReequipAnimation(final ItemStack oldStack, final ItemStack newStack, final boolean slotChanged)
    {
        return oldStack.getItem() != this || newStack.getItem() != this || slotChanged;
    }

    private class MaterialItemStackConstructionConsumer implements Consumer<IMaterial> {

        private final IHeatedObjectType type;
        private final HashMap<String, ItemStack> heatedStacks;

        private MaterialItemStackConstructionConsumer(IHeatedObjectType type, HashMap<String, ItemStack> heatedStacks) {
            this.type = type;
            this.heatedStacks = heatedStacks;
        }

        /**
         * Performs this operation on the given argument.
         *
         * @param material the input argument
         */
        @Override
        public void accept(IMaterial material) {
            if (!heatedStacks.containsKey(material.getOreDictionaryIdentifier() + "-" + type.getRegistryName().toString() + "-Low")) {
                heatedStacks.put(material.getOreDictionaryIdentifier() + "-" + type.getRegistryName().toString() + "-Low", IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, ModHeatableObjects.ITEMSTACK, type, material.getMeltingPoint() / 3));
                heatedStacks.put(material.getOreDictionaryIdentifier() + "-" + type.getRegistryName().toString() + "-High", IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getHeatedItemFactory().generateHeatedItemFromMaterial(material, ModHeatableObjects.ITEMSTACK, type, material.getMeltingPoint() - 1));
            }
        }
    }

    /**
     * Called from ItemStack.setItem, will hold extra data for the life of this ItemStack.
     * Can be retrieved from stack.getCapabilities()
     * The NBT can be null if this is not called from readNBT or if the item the stack is
     * changing FROM is different then this item, or the previous item had no capabilities.
     * <p>
     * This is called BEFORE the stacks item is set so you can use stack.getItem() to see the OLD item.
     * Remember that getItem CAN return null.
     *
     * @param stack The ItemStack
     * @param nbt   NBT of this item serialized, or null.
     * @return A holder instance associated with this ItemStack where you can hold capabilities for the life of this item.
     */
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (stack.getItem() == null)
            return null;

        SmithsCoreCapabilityDispatcher internalParentDispatcher = new SmithsCoreCapabilityDispatcher();
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY);

        if (nbt != null)
        {
            NBTTagCompound parentCompound =
              nbt.getCompoundTag(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT).toString());
            internalParentDispatcher.deserializeNBT(parentCompound);
        }

        return internalParentDispatcher;
    }
}

