package com.ldtteam.armory.common.structure.forge;

import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.tileentity.TileEntityForge;
import com.ldtteam.smithscore.common.structures.IStructureFactory;
import com.ldtteam.smithscore.util.common.positioning.Coordinate3D;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.LinkedHashSet;

/**
 * Author Orion (Created on: 25.06.2016)
 */
public class StructureFactoryForge implements IStructureFactory<StructureForge, TileEntityForge> {
    @Nonnull
    @Override
    public StructureForge generateNewStructure(@Nonnull TileEntityForge initialPart) {
        return new StructureForge(initialPart);
    }

    @Nonnull
    @Override
    public StructureForge loadStructureFromNBT(@Nonnull NBTTagCompound compound) {
        StructureDataForge dataForge = new StructureDataForge();

        LinkedHashSet<Coordinate3D> parts = new LinkedHashSet<>();
        NBTTagList coordinates = compound.getTagList(References.NBTTagCompoundData.TE.Forge.Structure.PARTS, Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < coordinates.tagCount(); i++) {
            Coordinate3D location = Coordinate3D.fromNBT(coordinates.getCompoundTagAt(i));
            parts.add(location);
        }

        StructureForge forge = new StructureForge(dataForge, parts);
        dataForge.readFromNBT(compound.getCompoundTag(References.NBTTagCompoundData.TE.Forge.Structure.DATA));

        return forge;
    }

    @Nonnull
    @Override
    public NBTTagCompound generateNBTFromStructure(@Nonnull StructureForge structure) {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setTag(References.NBTTagCompoundData.TE.Forge.Structure.DATA, structure.getData().writeToNBT());

        NBTTagList coordinates = new NBTTagList();
        for (Coordinate3D location : structure.getPartLocations()) {
            coordinates.appendTag(location.toCompound());
        }

        compound.setTag(References.NBTTagCompoundData.TE.Forge.Structure.PARTS, coordinates);

        return compound;
    }

    @Nonnull
    @Override
    public Class<StructureForge> getStructureType() {
        return StructureForge.class;
    }
}
