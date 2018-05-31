package com.smithsmodding.armory.common.item;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Author Marc (Created on: 11.06.2016)
 */
public class ItemSmithingsGuide extends Item {

    public ItemSmithingsGuide() {
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_GUIDE);
        this.setRegistryName(References.General.MOD_ID.toLowerCase(), References.InternalNames.Items.IN_GUIDE);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World worldIn, final EntityPlayer playerIn, final EnumHand handIn)
    {
        if (!worldIn.isRemote)
        {
            playerIn.openGui(Armory.instance, References.GuiIDs.SMITHINGSGUIDE, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
        }

        return ActionResult.newResult(EnumActionResult.SUCCESS, playerIn.getHeldItem(handIn));
    }
}
