package com.ldtteam.armory.common.tileentity.guimanagers;

import com.ldtteam.armory.common.tileentity.TileEntityForgeBase;
import com.ldtteam.armory.common.tileentity.state.IForgeFuelDataContainer;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.ldtteam.smithscore.client.gui.management.TileStorageBasedGUIManager;

import java.text.DecimalFormat;

/**
 * Author Orion (Created on: 23.06.2016)
 */
public class TileEntityForgeBaseGuiManager<T extends TileEntityForgeBase> extends TileStorageBasedGUIManager {

    protected static final DecimalFormat laf = new DecimalFormat("###.##");

    private final T tileEntity;

    public TileEntityForgeBaseGuiManager(T tileEntity) {
        this.tileEntity = tileEntity;
    }

    public T getTileEntity() {
        return tileEntity;
    }

    @Override
    public float getProgressBarValue(IGUIComponent component) {
        if (!(component instanceof ComponentProgressBar))
            return 0F;

        IForgeFuelDataContainer state = getTileEntity().getFuelData();

        if (component.getID().toLowerCase().contains("flame")) {
            return state.getBurningTicksLeftOnCurrentFuel() / (float) state.getTotalBurningTicksOnCurrentFuel();
        }

        return 0f;
    }
}
