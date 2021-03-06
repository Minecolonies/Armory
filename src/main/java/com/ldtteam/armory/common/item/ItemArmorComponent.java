package com.ldtteam.armory.common.item;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.capability.IArmorComponentStackCapability;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.common.CapabilityHelper;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.api.util.references.ModCreativeTabs;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.smithscore.client.proxy.CoreClientProxy;
import com.ldtteam.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.ldtteam.smithscore.util.CoreReferences;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Author Marc (Created on: 11.06.2016)
 */
public class ItemArmorComponent extends Item {

    public ItemArmorComponent() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_ARMOR_COMPONENT);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.IN_ARMOR_COMPONENT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return CoreClientProxy.getMultiColoredFontRenderer();
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (!stack.hasCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null))
            return "Stack without the ArmorComponent capability.";

        IArmorComponentStackCapability capability = stack.getCapability(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY, null);
        IMultiComponentArmorExtension extension = capability.getExtension();

        if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
        {
            IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
            IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

            return material.getTextFormatting() + I18n.translateToLocal(material.getTranslationKey()) + " " + extension.getTextFormatting() + I18n.translateToLocal(extension.getTranslationKey());
        }

        return extension.getTextFormatting() + I18n.translateToLocal(extension.getTranslationKey());
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (tab != getCreativeTab())
        {
            return;
        }

        for (IMultiComponentArmorExtension extension : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry()) {
            if (!extension.hasItemStack())
                continue;

            subItems.add(CapabilityHelper.generateArmorComponentStack(this, extension));
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
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_ARMORCOMPONENT_CAPABILITY);

        if (nbt != null)
        {
            NBTTagCompound parentCompound =
              nbt.getCompoundTag(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT).toString());
            internalParentDispatcher.deserializeNBT(parentCompound);
        }

        return internalParentDispatcher;
    }
}
