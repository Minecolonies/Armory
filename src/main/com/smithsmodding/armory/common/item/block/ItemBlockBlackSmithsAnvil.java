package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 23.02.2016.
 */
public class ItemBlockBlackSmithsAnvil extends ItemBlock {

    public ItemBlockBlackSmithsAnvil(@Nonnull Block block) {
        super(block);
        this.setRegistryName(block.getRegistryName());
    }

    /**
     * Returns the font renderer used to render tooltips and overlays for this item.
     * Returning null will use the standard font renderer.
     *
     * @param stack The current item stack
     * @return A instance of FontRenderer or null to use default
     */
    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    //Using full imports in this method to prevent ClassNotFound sidedness issues.
    public net.minecraft.client.gui.FontRenderer getFontRenderer(final ItemStack stack)
    {
        return com.smithsmodding.smithscore.client.proxy.CoreClientProxy.getMultiColoredFontRenderer();
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
