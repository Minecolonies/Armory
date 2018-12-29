package com.ldtteam.armory.common.tileentity;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.util.common.ItemStackHelper;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.tileentity.guimanagers.TileEntityFireplaceGuiManager;
import com.ldtteam.armory.common.tileentity.state.TileEntityFireplaceState;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntityFurnace;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 27.02.2016.
 */
public class TileEntityFireplace extends TileEntityForgeBase<TileEntityFireplaceState, TileEntityFireplaceGuiManager> {

    public static final int INGOTSLOTCOUNT = 3;
    public static final int FOODCOOKINPUTCOUNT = 1;
    public static final int FUELSLOTCOUNT = 1;
    public static final int STARTCOOKINGTEMP = 110;
    public static final int MAXCOOKINGTEMP = 635;
    public static final int CHARREDCOOKINGTEMP = MAXCOOKINGTEMP + 180;
    public static final int DESTROYEDCOOKINGTEMP = CHARREDCOOKINGTEMP + 180;
    public static final int BASESPEED = 450;
    public static final int MAXSPEED = 50;
    public static final float BASEMULTIPLIER = 1f;
    public static final float MAXMULTIPLIER = 2.5f; //BASEMULTIPLIER * (BASESPEED / MAXSPEED);
    public static final float MULTIPLIERPERDEGREE = (MAXMULTIPLIER - BASEMULTIPLIER) / (MAXCOOKINGTEMP - STARTCOOKINGTEMP);
    private static final int FOODBOOSTSTACKSIZE = 2;
    public static final int FOODCOOKOUTPUTCOUNT = FOODCOOKINPUTCOUNT * FOODBOOSTSTACKSIZE;
    public static float POSITIVEHEAT = 1.11F;
    public static float NEGATIVEHEAT = -0.15F;
    private boolean cookingShouldUpdateHeat = false;

    @Nonnull
    private ItemStack[] ingotStacks = new ItemStack[INGOTSLOTCOUNT];
    @Nonnull
    private ItemStack[] foodInputStacks = new ItemStack[FOODCOOKINPUTCOUNT];
    @Nonnull
    private ItemStack[] foodOutputStacks = new ItemStack[FOODCOOKOUTPUTCOUNT];
    @Nonnull
    private ItemStack[] fuelStacks = new ItemStack[FUELSLOTCOUNT];

    private float heatedProcentage;

    public TileEntityFireplace() {
        clearInventory();
    }

    @Nonnull
    @Override
    protected TileEntityFireplaceGuiManager getInitialGuiManager() {
        return new TileEntityFireplaceGuiManager(this);
    }

    @Nonnull
    @Override
    protected TileEntityFireplaceState getInitialState() {
        return new TileEntityFireplaceState();
    }

    /**
     * Getter for the Containers ID. Used to identify the container over the network. If this relates to TileEntities,
     * it should contain a ID and a location based ID so that multiple instances of this container matched up to
     * different TileEntities can be separated.
     *
     * @return The ID of this Container Instance.
     */
    @Nonnull
    @Override
    public String getContainerID() {
        return References.InternalNames.TileEntities.FireplaceContainer + "-" + getLocation().toString();
    }

    /**
     * Returns true if the Inventory is Empty.
     */
    @Override
    public boolean isEmpty() {
        for(int i = 0; i < getSizeInventory(); i++) {
            if (!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return INGOTSLOTCOUNT + FOODCOOKINPUTCOUNT + FOODCOOKOUTPUTCOUNT + FUELSLOTCOUNT;
    }

    /**
     * Returns the stack in the given slot.
     *
     * @param index
     */
    @Nullable
    @Override
    public ItemStack getStackInSlot(int index) {
        if (index < INGOTSLOTCOUNT) {
            return ingotStacks[index];
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            return foodInputStacks[index];
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            return foodOutputStacks[index];
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            return fuelStacks[index];
        }

        //index -= FUELSLOTCOUNT;

        return ItemStack.EMPTY;
    }

    @Override
    public void clearInventory() {
        ItemStackHelper.InitializeItemStackArray(ingotStacks);
        ItemStackHelper.InitializeItemStackArray(foodInputStacks);
        ItemStackHelper.InitializeItemStackArray(foodOutputStacks);
        ItemStackHelper.InitializeItemStackArray(fuelStacks);
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     *
     * @param index
     * @param stack
     */
    @Override
    public void setInventorySlotContents(int index, @Nonnull ItemStack stack) {
        if (stack == null)
            stack = ItemStack.EMPTY;

        if (index < INGOTSLOTCOUNT) {
            ingotStacks[index] = stack;
            return;
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            foodInputStacks[index] = stack;

            if (stack.isEmpty())
                (getState()).setCookingProgress(0);

            return;
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            foodOutputStacks[index] = stack;
            return;
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            fuelStacks[index] = stack;
            return;
        }

        //index -= FUELSLOTCOUNT;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markInventoryDirty() {
        this.markDirty();
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     *
     * @param index
     * @param stack
     */
    @Override
    public boolean isItemValidForSlot(int index, @Nonnull ItemStack stack) {
        if (index < INGOTSLOTCOUNT) {
            for (int i = 0; i < FOODCOOKINPUTCOUNT; i++) {
                if (!getStackInSlot(i + INGOTSLOTCOUNT).isEmpty())
                    return false;
            }

            return IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().isHeatable(stack);
        }

        index -= INGOTSLOTCOUNT;

        if (index < FOODCOOKINPUTCOUNT) {
            for (int i = 0; i < INGOTSLOTCOUNT; i++) {
                if (!getStackInSlot(i).isEmpty())
                    return false;
            }

            return FurnaceRecipes.instance().getSmeltingResult(stack).getItem() instanceof ItemFood;
        }

        index -= FOODCOOKINPUTCOUNT;

        if (index < FOODCOOKOUTPUTCOUNT) {
            return false;
        }

        index -= FOODCOOKOUTPUTCOUNT;

        if (index < FUELSLOTCOUNT) {
            return TileEntityFurnace.isItemFuel(stack);
        }

        //index -= FUELSLOTCOUNT;

        return false;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    @Override
    public void update() {
        if (getWorld().isRemote)
            return;

        TileEntityFireplaceState localData = getState();

        localData.setLastTemp(localData.getCurrentTemp());
        localData.setBurning(false);

        heatFurnace(localData, localData);

        localData.setBurning(localData.getBurningTicksLeftOnCurrentFuel() >= 1F);

        if (!heatIngots(localData, localData))
            cookingShouldUpdateHeat = true;

        cookFood();

        cookingShouldUpdateHeat = false;

        localData.setLastChange(localData.getCurrentTemp() - localData.getLastTemp());

        if (!getWorld().isRemote) {
            this.markDirty();
        }
    }

    @Override
    public TileEntityFireplaceState getFuelData() {
        return getState();
    }

    @Override
    protected void calculateHeatTerms(@Nonnull TileEntityFireplaceState localData) {
        localData.setMaxTemp(2150f);
        localData.setLastNegativeTerm(NEGATIVEHEAT);
        localData.setLastPositiveTerm(POSITIVEHEAT);
    }

    @Override
    protected int getFuelStackAmount() {
        return FUELSLOTCOUNT;
    }

    @Override
    protected ItemStack getFuelStack(int fuelStackIndex) {
        return fuelStacks[fuelStackIndex];
    }

    @Override
    protected void setFuelStack(int fuelStackIndex, ItemStack fuelStack) {
        fuelStacks[fuelStackIndex] = fuelStack;
    }

    @Override
    protected int getTotalPossibleIngotAmount() {
        return INGOTSLOTCOUNT;
    }

    @Override
    protected ItemStack getIngotStack(int ingotStackIndex) {
        return ingotStacks[ingotStackIndex];
    }

    @Override
    protected void setIngotStack(int ingotStackIndex, ItemStack ingotStack) {
        ingotStacks[ingotStackIndex] = ingotStack;
    }

    @Override
    protected float getSourceEfficiencyIndex() {
        return 10;
    }

    public boolean cookFood() {
        if (cookingShouldUpdateHeat) {
            getState().addLastChangeToCurrentTemp();

            if (getState().getCurrentTemp() > 20F) {
                getState().setCurrentTemp(getState().getCurrentTemp() + (getState().getLastNegativeTerm() * getState().getHeatedPercentage()));
            }
        }

        if ((getState().getLastChange() == 0F) && (getState().getCurrentTemp() <= 20F) && (getFoodAmount() == 0)) {
            return cookingShouldUpdateHeat;
        }

        if (getState().getCurrentTemp() < STARTCOOKINGTEMP) {
            getState().setCookingSpeedMultiplier(0);
            return cookingShouldUpdateHeat;
        }

        getState().setCookingSpeedMultiplier((getState().getCurrentTemp() - STARTCOOKINGTEMP) * MULTIPLIERPERDEGREE + 1);

        if (getState().getCookingSpeedMultiplier() < BASEMULTIPLIER)
            return cookingShouldUpdateHeat;

        if (getState().getCookingSpeedMultiplier() > MAXMULTIPLIER)
            getState().setCookingSpeedMultiplier(MAXMULTIPLIER);


        for (int i = 0; i < INGOTSLOTCOUNT; i++) {
            if (!getStackInSlot(i).isEmpty())
                return cookingShouldUpdateHeat;
        }

        int targetSlot = canCook();
        if (targetSlot == -1) {
            return cookingShouldUpdateHeat;
        }

        getState().setCookingProgress(getState().getCookingProgress() + (1f / 200f) * getState().getCookingSpeedMultiplier());

        if (getState().getCurrentTemp() > 20F) {
            getState().setCurrentTemp(getState().getCurrentTemp() + (getState().getLastNegativeTerm() * getState().getHeatedPercentage()));
        }

        if (getState().getCookingProgress() < 1f)
            return cookingShouldUpdateHeat;

        //Add the items twice.
        for (int i = 0; i < 2; i++) {
            targetSlot = canCook();
            if (targetSlot == -1) {
                break;
            }

            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.foodInputStacks[0]);

            if (this.foodOutputStacks[targetSlot].isEmpty()) {
                this.foodOutputStacks[targetSlot] = itemstack.copy();
            } else if (this.foodOutputStacks[targetSlot].getItem() == itemstack.getItem()) {
                this.foodOutputStacks[targetSlot].grow(itemstack.getCount()); // Forge BugFix: Results may have multiple items
            }
        }

        this.foodInputStacks[0].shrink(1);

        if (this.foodInputStacks[0].getCount() <= 0 || this.foodInputStacks[0].isEmpty()) {
            this.foodInputStacks[0] = ItemStack.EMPTY;
        }

        getState().setCookingProgress(0);

        return cookingShouldUpdateHeat;
    }

    private int canCook() {
        if (this.foodInputStacks[0].isEmpty()) {
            return -1;
        } else {
            ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult(this.foodInputStacks[0]);
            if (itemstack.isEmpty()) return -1;
            if (!(itemstack.getItem() instanceof ItemFood)) return -1;
            if (this.foodOutputStacks[0].isEmpty()) return 0;
            if (!this.foodOutputStacks[0].isItemEqual(itemstack)) {
                if (this.foodOutputStacks[1].isEmpty()) return 1;
                if (!this.foodOutputStacks[1].isItemEqual(itemstack)) return -1;
                int result = foodOutputStacks[1].getCount() + itemstack.getCount();
                if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                    return -1;
                }

                return 1;
            }
            int result = foodOutputStacks[0].getCount() + itemstack.getCount();
            if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                if (this.foodOutputStacks[1].isEmpty()) return 1;
                if (!this.foodOutputStacks[1].isItemEqual(itemstack)) return -1;
                result = foodOutputStacks[1].getCount() + itemstack.getCount();
                if (!(result <= getInventoryStackLimit() && result <= this.foodOutputStacks[0].getMaxStackSize())) {
                    return -1;
                }

                return 1;
            }

            return 0;
        }
    }

    public int getFoodAmount() {
        int foodAmount = 0;

        for (int foodIndex = 0; foodIndex < FOODCOOKINPUTCOUNT; foodIndex++) {
            if (foodInputStacks[foodIndex].isEmpty()) {
                continue;
            }

            foodAmount++;
        }

        return foodAmount;
    }
}
