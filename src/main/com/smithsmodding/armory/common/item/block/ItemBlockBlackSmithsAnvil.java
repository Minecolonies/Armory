package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Marc on 23.02.2016.
 */
public class ItemBlockBlackSmithsAnvil extends ItemBlock {

    public ItemBlockBlackSmithsAnvil(@Nonnull Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
    }

    @Override
    @SideOnly(Side.CLIENT)
    public FontRenderer getFontRenderer(ItemStack stack) {
        return super.getFontRenderer(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public String getItemStackDisplayName(ItemStack pStack) {
        if (pStack.getTagCompound() == null)
            return "";

        if (pStack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            return pStack.getTagCompound().getString(References.NBTTagCompoundData.CustomName);

        IAnvilMaterial tMaterial = ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(pStack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL)));

        return tMaterial.getTextFormatting() + I18n.format(tMaterial.getTranslationKey()) + " " + TextFormatting.RESET + I18n.format(this.getUnlocalizedName() + ".name");
    }

    @Override
    public boolean showDurabilityBar(final ItemStack stack)
    {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(final ItemStack stack)
    {
        String materialID = stack.getTagCompound().getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL);
        IAnvilMaterial material = IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(materialID));

        if (!stack.getTagCompound().hasKey(References.NBTTagCompoundData.TE.Anvil.REMAININGUSES))
            stack.getTagCompound().setInteger(References.NBTTagCompoundData.TE.Anvil.REMAININGUSES, material.getDurability());

        return ((double) (material.getDurability() + 1 - stack.getTagCompound().getInteger(References.NBTTagCompoundData.TE.Anvil.REMAININGUSES))) / material.getDurability();
    }
}
