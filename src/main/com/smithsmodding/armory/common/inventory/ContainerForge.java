package com.smithsmodding.armory.common.inventory;
/*
 *   ContainerFirepit
 *   Created by: Orion
 *   Created on: 16-1-2015
 */


import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.common.inventory.slots.SlotFuelInput;
import com.smithsmodding.armory.common.inventory.slots.SlotHeatable;
import com.smithsmodding.armory.common.tileentity.TileEntityForge;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.common.inventory.slot.SlotSmithsCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;

public class ContainerForge extends ContainerSmithsCore {
    private TileEntityForge tileEntityForge;

    public ContainerForge(@Nonnull EntityPlayer playerMP, TileEntityForge tileEntityForge) {
        super(References.InternalNames.TileEntities.ForgeContainer, tileEntityForge, tileEntityForge, playerMP);

        this.tileEntityForge = tileEntityForge;

        generateStandardInventory();
    }

    public TileEntityForge getTileEntity() {
        return tileEntityForge;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    private void generateStandardInventory() {
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 0, 23, 28, 0));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 1, 51, 14, 1));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 2, 80, 10, 2));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 3, 109, 14, 3));
        this.addSlotToContainer(new SlotHeatable(tileEntityForge, 4, 138, 28, 4));

        for (int fuelStackIndex = 0; fuelStackIndex < TileEntityForge.FUELSTACK_AMOUNT; fuelStackIndex++) {
            this.addSlotToContainer(new SlotFuelInput(tileEntityForge, fuelStackIndex + 5, 44 + fuelStackIndex * 18, 63));
        }

        for (int inventoryRowIndex = 0; inventoryRowIndex < PLAYER_INVENTORY_ROWS; ++inventoryRowIndex) {
            for (int inventoryColumnIndex = 0; inventoryColumnIndex < PLAYER_INVENTORY_COLUMNS; ++inventoryColumnIndex) {
                this.addSlotToContainer(new Slot(getPlayerInventory(), inventoryColumnIndex + inventoryRowIndex * 9 + 9, 8 + inventoryColumnIndex * 18, 88 + inventoryRowIndex * 18));
            }
        }

        for (int actionBarSlotIndex = 0; actionBarSlotIndex < PLAYER_INVENTORY_COLUMNS; ++actionBarSlotIndex) {
            this.addSlotToContainer(new Slot(getPlayerInventory(), actionBarSlotIndex, 8 + actionBarSlotIndex * 18, 147));
        }
    }
}
