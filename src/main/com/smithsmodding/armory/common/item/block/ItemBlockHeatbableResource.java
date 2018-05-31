package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.common.capability.IMaterializedStackCapability;
import com.smithsmodding.armory.api.common.heatable.IHeatableObjectWrapper;
import com.smithsmodding.armory.api.common.heatable.IHeatedObjectType;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModHeatedObjectTypes;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

/**
 * Created by marcf on 2/2/2017.
 */
public class ItemBlockHeatbableResource extends ItemBlock implements IHeatableObjectWrapper
{

    public ItemBlockHeatbableResource(Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null))
        {
            return super.getItemStackDisplayName(stack);
        }

        IMaterializedStackCapability capability = stack.getCapability(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY, null);
        return I18n.translateToLocal(this.getUnlocalizedName() + ".name") + " " + capability.getMaterial().getTextFormatting() + I18n.translateToLocal(capability.getMaterial()
                                                                                                                                                         .getTranslationKey())
                 + TextFormatting.RESET;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack)
    {
        return CoreClientProxy.getMultiColoredFontRenderer();
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
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY);
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_MATERIALIZEDSSTACK_CAPABIITY);

        if (nbt != null)
        {
            NBTTagCompound parentCompound =
              nbt.getCompoundTag(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT).toString());
            internalParentDispatcher.deserializeNBT(parentCompound);
        }

        return internalParentDispatcher;
    }

    @Override
    public IHeatedObjectType getHeatableObjectType()
    {
        return ModHeatedObjectTypes.BLOCK;
    }
}
