package com.smithsmodding.armory.common.block;

import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 24.07.2016)
 */
public class BlockArmory extends Block {

    public BlockArmory(@Nonnull String blockName, @Nonnull Material blockMaterial) {
        super(blockMaterial);
        setUnlocalizedName(blockName);
        setHardness(5F);
        setResistance(10F);
        setRegistryName(References.General.MOD_ID.toLowerCase(), blockName);
    }

    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    @Override
    public final void getSubBlocks(final CreativeTabs itemIn, final NonNullList<ItemStack> items)
    {
        if (itemIn != getCreativeTabToDisplayOn())
        {
            return;
        }

        getSubBlocksWithItem(Item.getItemFromBlock(this), items);
    }

    public void getSubBlocksWithItem(final Item item, final NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }
}
