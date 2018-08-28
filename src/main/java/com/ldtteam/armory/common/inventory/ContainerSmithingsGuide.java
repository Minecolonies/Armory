package com.ldtteam.armory.common.inventory;

import com.google.common.collect.Lists;
import com.ldtteam.smithscore.client.events.gui.GuiInputEvent;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.client.gui.management.IGUIManager;
import com.ldtteam.smithscore.common.inventory.ContainerSmithsCore;
import com.ldtteam.smithscore.common.inventory.IContainerHost;
import com.ldtteam.smithscore.common.inventory.IItemStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.UUID;

public class ContainerSmithingsGuide extends ContainerSmithsCore
{
    public ContainerSmithingsGuide(
      @Nonnull final String containerID,
      @Nonnull final boolean isRemote,
      @Nonnull final EntityPlayer playerMP)
    {
        super(containerID,
          new IContainerHost()
          {
              @Nonnull
              @Override
              public String getContainerID()
              {
                  return containerID + playerMP.getUniqueID().toString();
              }

              @Override
              public boolean isRemote()
              {
                  return isRemote;
              }

              @Nonnull
              @Override
              public IGUIManager getManager()
              {
                  return new IGUIManager()
                  {
                      @Override
                      public void onGuiOpened(@Nonnull final UUID playerId)
                      {

                      }

                      @Override
                      public void onGUIClosed(@Nonnull final UUID playerID)
                      {

                      }

                      @Nullable
                      @Override
                      public String getCustomToolTipDisplayString(@Nonnull final IGUIComponent component)
                      {
                          return "";
                      }

                      @Override
                      public float getProgressBarValue(@Nonnull final IGUIComponent component)
                      {
                          return 0;
                      }

                      @Nullable
                      @Override
                      public String getLabelContents(@Nonnull final IGUIComponent component)
                      {
                          return "";
                      }

                      @Nullable
                      @Override
                      public ArrayList<FluidStack> getTankContents(@Nonnull final IGUIComponent component)
                      {
                          return Lists.newArrayList();
                      }

                      @Override
                      public int getTotalTankContents(@Nonnull final IGUIComponent component)
                      {
                          return 1;
                      }

                      @Override
                      public void onTabChanged(@Nonnull final String newActiveTabId)
                      {

                      }

                      @Override
                      public void onInput(final GuiInputEvent.InputTypes types, @Nonnull final String componentId, @Nonnull final String input)
                      {

                      }
                  };
              }

              @Override
              public void setManager(@Nonnull final IGUIManager newManager)
              {
                  //Noop
              }
          },
          new IItemStorage() {
              @Override
              public boolean isEmpty()
              {
                  return true;
              }

              @Override
              public int getSizeInventory()
              {
                  return 0;
              }

              @Nonnull
              @Override
              public ItemStack getStackInSlot(final int index)
              {
                  return ItemStack.EMPTY;
              }

              @Nonnull
              @Override
              public ItemStack decrStackSize(final int index, final int count)
              {
                  return ItemStack.EMPTY;
              }

              @Override
              public void clearInventory()
              {

              }

              @Override
              public void setInventorySlotContents(final int index, @Nonnull final ItemStack stack)
              {

              }

              @Override
              public int getInventoryStackLimit()
              {
                  return 0;
              }

              @Override
              public void markInventoryDirty()
              {

              }

              @Override
              public boolean isItemValidForSlot(final int index, @Nonnull final ItemStack stack)
              {
                  return false;
              }

              @Override
              public String getName()
              {
                  return "";
              }

              @Override
              public boolean hasCustomName()
              {
                  return false;
              }

              @Override
              public ITextComponent getDisplayName()
              {
                  return new TextComponentString("");
              }
          },
          playerMP);
    }

}
