package com.ldtteam.armory.common.structure.forge;

import com.ldtteam.armory.common.tileentity.TileEntityForge;
import com.ldtteam.smithscore.common.structures.IStructureController;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureControllerForge implements IStructureController<StructureForge, TileEntityForge> {
    @Nonnull
    @Override
    public EnumFacing[] getPossibleConnectionSides() {
        return new EnumFacing[]{EnumFacing.EAST, EnumFacing.WEST, EnumFacing.NORTH, EnumFacing.SOUTH};
    }
}
