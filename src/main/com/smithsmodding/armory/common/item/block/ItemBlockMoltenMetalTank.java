package com.smithsmodding.armory.common.item.block;

import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.tileentity.TileEntityMoltenMetalTank;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by marcf on 3/9/2017.
 */
public class ItemBlockMoltenMetalTank extends ItemBlock {

    public ItemBlockMoltenMetalTank(Block block) {
        super(block);
    }

    /**
     * Called to actually place the block, after the location is determined
     * and all permission checks have been made.
     *
     * @param stack    The item stack that was used to place the block. This can be changed inside the method.
     * @param player   The player who is placing the block. Can be null if the block is not being placed by a player.
     * @param world
     * @param pos
     * @param side     The side the player (or machine) right-clicked on.
     * @param hitX
     * @param hitY
     * @param hitZ
     * @param newState
     */
    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState) {
        if (!super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, newState)) return false;

        if (!stack.hasTagCompound() || !stack.getTagCompound().hasKey(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS))
            return true;

        TileEntityMoltenMetalTank tank = (TileEntityMoltenMetalTank) world.getTileEntity(pos);
        IFluidTank fluidTank = tank.getTankForSide(null);

        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS));

        fluidTank.fill(fluidStack, true);

        return true;
    }

    @Override
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS)) {
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(stack.getTagCompound().getCompoundTag(References.NBTTagCompoundData.TE.MoltenMetalTank.CONTENTS));

            if (fluidStack == null)
            {
                return;
            }

            tooltip.add(I18n.format(TranslationKeys.Items.MoltenMetalTank.TK_CONTENTS));
            tooltip.add(" -  " + fluidStack.getLocalizedName() + ": " + fluidStack.amount + " mB.");
        }
    }
}
