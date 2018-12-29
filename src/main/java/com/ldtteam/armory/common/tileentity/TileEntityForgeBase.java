package com.ldtteam.armory.common.tileentity;

import com.ldtteam.armory.api.common.capability.IHeatedObjectCapability;
import com.ldtteam.armory.api.util.references.ModCapabilities;
import com.ldtteam.armory.common.factories.HeatedItemFactory;
import com.ldtteam.armory.common.item.ItemHeatedItem;
import com.ldtteam.armory.common.tileentity.guimanagers.TileEntityForgeBaseGuiManager;
import com.ldtteam.armory.common.tileentity.state.IForgeFuelDataContainer;
import com.ldtteam.armory.common.tileentity.state.TileEntityForgeBaseState;
import com.ldtteam.smithscore.common.inventory.IItemStorage;
import com.ldtteam.smithscore.common.tileentity.TileEntitySmithsCore;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.ITickable;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public abstract class TileEntityForgeBase<S extends TileEntityForgeBaseState, G extends TileEntityForgeBaseGuiManager> extends TileEntitySmithsCore<S, G> implements IItemStorage, ITickable {

    @Override
    public void update() {
        if (isRemote())
            return;

        IForgeFuelDataContainer fuelData = getFuelData();
        S localData = getState();

        if (fuelData == null)
            return;

        localData.setLastTemp(localData.getCurrentTemp());
        fuelData.setBurning(false);

        heatFurnace(fuelData, localData);

        fuelData.setBurning(fuelData.getBurningTicksLeftOnCurrentFuel() >= 1F);

        if (!heatIngots(fuelData, localData)) {
            if (localData.getCurrentTemp() >= 19F) {
                if (getFuelData().getBurningTicksLeftOnCurrentFuel()>0)
                {
                    localData.setCurrentTemp(localData.getCurrentTemp() + (localData.getLastPositiveTerm() * (1 - localData.getHeatedPercentage())));
                }

                localData.setCurrentTemp(localData.getCurrentTemp() + (localData.getLastNegativeTerm() * localData.getHeatedPercentage()));
            }
        }

        localData.setLastChange(localData.getCurrentTemp() - localData.getLastTemp());

        if (!getWorld().isRemote) {
            this.markDirty();
        }
    }

    @Nullable
    public abstract IForgeFuelDataContainer getFuelData();

    public void heatFurnace(@Nonnull IForgeFuelDataContainer fuelData, @Nonnull S localData) {
        calculateHeatTerms(localData);

        localData.setLastChange(0F);

        if (fuelData.getBurningTicksLeftOnCurrentFuel() >= 1F) {
            fuelData.changeBurningTicksLeftOnCurrentFuel(-1);
            localData.addLastPositiveHeatTermToChange();
        }

        if (fuelData.getBurningTicksLeftOnCurrentFuel() < 1F) {
            fuelData.setTotalBurningTicksOnCurrentFuel(0);

            for (int fuelStackIndex = 0; fuelStackIndex < getFuelStackAmount(); fuelStackIndex++) {

                if (getFuelStack(fuelStackIndex).isEmpty()) {
                    continue;
                }

                ItemStack fuelStack = getFuelStack(fuelStackIndex);

                //Check if the stack is a valid Fuel in the Furnace
                if ((!fuelStack.isEmpty()) && (TileEntityFurnace.isItemFuel(fuelStack))) {

                    fuelData.changeTotalBurningTicksOnCurrentFuel(TileEntityFurnace.getItemBurnTime(fuelStack));

                    fuelStack.shrink(1);

                    if (fuelStack.getCount() == 0 || fuelStack.isEmpty()) {
                        setFuelStack(fuelStackIndex, fuelStack.getItem().getContainerItem(fuelStack));
                    }

                }

            }

            fuelData.resetBurningTicksLeftOnCurrentFuel();
        }

        localData.setLastChange(localData.getLastChange() * (1 - localData.getHeatedPercentage()));
    }

    public boolean heatIngots(IForgeFuelDataContainer fuelData, @Nonnull S localData) {
        if ((localData.getLastChange() == 0F) && (localData.getCurrentTemp() <= 20F) || (getInsertedIngotAmount() == 0)) {
            return false;
        }

        localData.addLastChangeToCurrentTemp();

        if (localData.getCurrentTemp() > 20F) {
            localData.setCurrentTemp(localData.getCurrentTemp() + (localData.getLastNegativeTerm() * localData.getHeatedPercentage()));
        }

        for (int ingotStackIndex = 0; ingotStackIndex < getTotalPossibleIngotAmount(); ingotStackIndex++) {
            if (getIngotStack(ingotStackIndex).isEmpty()) {
                continue;
            }

            if ((localData.getCurrentTemp() > 20F) && (!(((getIngotStack(ingotStackIndex).getItem() instanceof ItemHeatedItem) && getIngotStack(ingotStackIndex).hasCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null))) || getIngotStack(ingotStackIndex).hasCapability(ModCapabilities.MOD_HEATABLEOBJECT_CAPABILITY, null))) {
                setIngotStack(ingotStackIndex, HeatedItemFactory.getInstance().convertToHeatedIngot(getIngotStack(ingotStackIndex)));
            }

            ItemStack stack = getIngotStack(ingotStackIndex);
            IHeatedObjectCapability capability = stack.getCapability(ModCapabilities.MOD_HEATEDOBJECT_CAPABILITY, null);

            if (capability == null)
                continue;

            float currentStackTemp = capability.getTemperature();
            float currentStackCoefficient = capability.getMaterial().getHeatCoefficient();

            float sourceDifference = (localData.getLastNegativeTerm() / getSourceEfficiencyIndex()) - currentStackCoefficient;
            float targetDifference = currentStackCoefficient;


            if (currentStackTemp < 20F) {
                setIngotStack(ingotStackIndex, HeatedItemFactory.getInstance().convertToCooledIngot(stack));
            } else if (currentStackTemp <= localData.getCurrentTemp()) {
                localData.setCurrentTemp(localData.getCurrentTemp() + sourceDifference);
                capability.setTemperature(capability.getTemperature() + targetDifference);
            } else if (capability.getTemperature() > localData.getCurrentTemp()) {
                localData.setCurrentTemp(localData.getCurrentTemp() + targetDifference);
                capability.setTemperature(capability.getTemperature() + sourceDifference);
            }

        }

        return true;
    }

    protected int getInsertedIngotAmount() {
        int amount = 0;

        for (int index = 0; index < getTotalPossibleIngotAmount(); index++) {
            if (getIngotStack(index).isEmpty()) {
                continue;
            }

            amount++;
        }

        return amount;
    }

    protected abstract void calculateHeatTerms(S localData);

    protected abstract int getFuelStackAmount();

    protected abstract ItemStack getFuelStack(int fuelStackIndex);

    protected abstract void setFuelStack(int fuelStackIndex, ItemStack fuelStack);

    protected abstract int getTotalPossibleIngotAmount();

    protected abstract ItemStack getIngotStack(int ingotStackIndex);

    protected abstract void setIngotStack(int ingotStackIndex, ItemStack ingotStack);

    protected abstract float getSourceEfficiencyIndex();

    protected void onFuelFound() {
    }

    protected void onFuelLost() {
    }

    @Override
    public ItemStack decrStackSize(int index, int amount) {
        ItemStack stack = getStackInSlot(index);
        if (stack.isEmpty()) {
            return stack;
        }

        if (stack.getCount() < amount) {
            setInventorySlotContents(index, ItemStack.EMPTY);
        } else {
            ItemStack returnStack = stack.splitStack(amount);

            if (stack.getCount() == 0 || stack.isEmpty()) {
                setInventorySlotContents(index, ItemStack.EMPTY);
            }

            return returnStack;
        }

        return stack;
    }
}
