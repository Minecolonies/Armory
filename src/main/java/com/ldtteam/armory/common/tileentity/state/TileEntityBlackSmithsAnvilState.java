package com.ldtteam.armory.common.tileentity.state;

import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.util.references.ModMaterials;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.api.ArmoryAPI;
import com.ldtteam.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.ldtteam.smithscore.common.tileentity.IWatchableTileEntity;
import com.ldtteam.smithscore.common.tileentity.TileEntitySmithsCore;
import com.ldtteam.smithscore.common.tileentity.state.ITileEntityState;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvilState implements ITileEntityState {

    private float craftingprogress;
    private IAnvilMaterial material;
    private TileEntityBlackSmithsAnvil anvil;
    @Nullable
    private IAnvilRecipe recipe = null;

    private String itemName = "";
    private boolean processingCraftingResult = false;

    private int remainingUses = 0;

    /**
     * Method called when this state get attached to a TE. Allows it to store a reference or modify values of the TE.
     *
     * @param tileEntitySmithsCore The TE this state got attached to.
     */
    @Override
    public void onStateCreated(TileEntitySmithsCore tileEntitySmithsCore) {
        this.anvil = (TileEntityBlackSmithsAnvil) tileEntitySmithsCore;
    }

    /**
     * Called to indicate this TE that some of its values may have been updated. Use it to perform additional
     * calculation on this data.
     */
    @Override
    public void onStateUpdated() {

    }

    /**
     * Method called by the Attached TE to indicate that it is being detached and discarded by its TE. Allows you to
     * handle the disconnect from the state gracefully.
     */
    @Override
    public void onStateDestroyed() {

    }

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets
     * written to disk.
     *
     * @return True when the state needs storing, false when not.
     */
    @Override
    public boolean requiresNBTStorage() {
        return true;
    }

    /**
     * Method that allows this state to read its data from Disk, when the attached TE gets loaded.
     *
     * @param stateData The stored data of this state.
     */
    @Override
    public void readFromNBTTagCompound(@Nonnull NBTBase stateData) {
        try {
            boolean updateModel = false;
            if (material == null) {
                updateModel = true;
            } else if (!((NBTTagCompound) stateData).getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL).equals(material.getRegistryName().toString())) {
                updateModel = true;
            }

            this.material = ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValue(new ResourceLocation(((NBTTagCompound) stateData).getString(References.NBTTagCompoundData.TE.Anvil.MATERIAL)));
            this.craftingprogress = ((NBTTagCompound) stateData).getFloat(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS);
            this.itemName = ((NBTTagCompound) stateData).getString(References.NBTTagCompoundData.TE.Anvil.ITEMNAME);
            this.processingCraftingResult = ((NBTTagCompound) stateData).getBoolean(References.NBTTagCompoundData.TE.Anvil.PROCESSING);

            this.remainingUses = ((NBTTagCompound) stateData).getInteger(References.NBTTagCompoundData.TE.Anvil.REMAININGUSES);

            if (this.remainingUses > this.getMaterial().getDurability())
            {
                this.remainingUses = this.getMaterial().getDurability();
            }

            if (this.remainingUses < 0)
            {
                this.remainingUses = 0;
            }

            if (updateModel && anvil.getWorld() != null)
                anvil.getWorld().markChunkDirty(anvil.getPos(), anvil);
        } catch (Exception ex) {
            this.material = ModMaterials.Anvil.IRON;
            this.craftingprogress = 0F;
            this.itemName = "";
            this.processingCraftingResult = false;
            this.remainingUses = 0;
        }
    }

    /**
     * Method that allows this state to writes its data to Disk, when the attached TE writes its data to disk.
     *
     * @return A NBTBase that describes this state.
     */
    @Nonnull
    @Override
    public NBTBase writeToNBTTagCompound() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, material.getRegistryName().toString());
        compound.setFloat(References.NBTTagCompoundData.TE.Anvil.CRAFTINGPROGRESS, craftingprogress);
        compound.setString(References.NBTTagCompoundData.TE.Anvil.ITEMNAME, itemName);
        compound.setBoolean(References.NBTTagCompoundData.TE.Anvil.PROCESSING, processingCraftingResult);
        compound.setInteger(References.NBTTagCompoundData.TE.Anvil.REMAININGUSES, remainingUses);

        return compound;
    }

    /**
     * Method to let the attached TE know that this state needs to store data in the TE's NBTTagCompound that gets used
     * to synchronise the TE.
     *
     * @return True when the state needs storing, false when not.
     */
    @Override
    public boolean requiresSynchronization() {
        return true;
    }

    public IWatchableTileEntity getAnvil() {
        return anvil;
    }

    /**
     * Method that allows this state to read its data from the network, when the attached TE gets synchronized.
     *
     * @param stateData The stored data of this state.
     */
    @Override
    public void readFromSynchronizationCompound(@Nonnull NBTBase stateData) {
        readFromNBTTagCompound(stateData);
    }

    /**
     * Method that allows this state to writes its data to the network, when the attached TE gets synchronized.
     *
     * @return A NBTBase that describes this state.
     */
    @Nonnull
    @Override
    public NBTBase writeToSynchronizationCompound() {
        return writeToNBTTagCompound();
    }

    public IAnvilMaterial getMaterial() {
        return material;
    }

    public void setMaterial(IAnvilMaterial material) {
        this.material = material;
        this.remainingUses = material.getDurability();
    }

    public float getCraftingprogress() {
        return craftingprogress;
    }

    public void setCraftingprogress(float craftingprogress) {
        this.craftingprogress = craftingprogress;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
        anvil.findValidRecipe();
    }

    public boolean isProcessingCraftingResult() {
        return processingCraftingResult;
    }

    public void setProcessingCraftingResult(boolean processingCraftingResult) {
        this.processingCraftingResult = processingCraftingResult;
    }

    @Nullable
    public IAnvilRecipe getRecipe() {
        return recipe;
    }

    public void setRecipe(IAnvilRecipe recipe) {
        this.recipe = recipe;
    }

    public int getRemainingUses()
    {
        return remainingUses;
    }

    public void setRemainingUses(final int remainingUses)
    {
        this.remainingUses = remainingUses;
    }

    public void decrementRemainingUses(final int delta)
    {
        this.remainingUses -= delta;
    }
}
