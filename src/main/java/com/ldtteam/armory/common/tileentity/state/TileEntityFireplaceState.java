package com.ldtteam.armory.common.tileentity.state;

import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.tileentity.TileEntityFireplace;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityFireplaceState extends TileEntityForgeBaseState<TileEntityFireplace>
{

    private float cookingProgress = 0f;
    private float cookingSpeedMultiplier = 1f;

    public TileEntityFireplaceState() {
        this.currentTemp = 20f;
        this.lastChange = 0f;
    }

    @Override
    public void readFromNBTTagCompound(NBTBase stateData) {
        super.readFromNBTTagCompound(stateData);

        NBTTagCompound compound = (NBTTagCompound) stateData;

        cookingProgress = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS);
        cookingSpeedMultiplier = compound.getFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED);
    }

    @Nonnull
    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = (NBTTagCompound) super.writeToNBTTagCompound();

        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGPROGRESS, cookingProgress);
        compound.setFloat(References.NBTTagCompoundData.TE.Fireplace.COOKINGSPEED, cookingSpeedMultiplier);

        return compound;
    }

    public float getCookingProgress() {
        return cookingProgress;
    }

    public void setCookingProgress(float cookingProgress) {
        this.cookingProgress = cookingProgress;
    }

    public float getCookingSpeedMultiplier() {
        return cookingSpeedMultiplier;
    }

    public void setCookingSpeedMultiplier(float cookingSpeedMultiplier) {
        this.cookingSpeedMultiplier = cookingSpeedMultiplier;
    }
}
