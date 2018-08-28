package com.ldtteam.armory.common.structure.conduit;

import com.ldtteam.armory.common.tileentity.TileEntityConduit;
import com.ldtteam.smithscore.common.structures.IStructureController;
import net.minecraft.util.EnumFacing;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 2/7/2017.
 */
public class StructureControllerConduitNetwork implements IStructureController<StructureConduitNetwork, TileEntityConduit> {

    @Nonnull
    @Override
    public EnumFacing[] getPossibleConnectionSides() {
        return EnumFacing.VALUES;
    }
}
