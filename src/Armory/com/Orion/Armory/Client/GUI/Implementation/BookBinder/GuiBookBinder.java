/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.Orion.Armory.Client.GUI.Implementation.BookBinder;

import com.Orion.Armory.Client.GUI.ArmoryBaseTabbedGui;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class GuiBookBinder extends ArmoryBaseTabbedGui {

    public GuiBookBinder(Container pTargetedContainer) {
        super(pTargetedContainer);
    }

    @Override
    public int getXOrigin() {
        return guiLeft;
    }

    @Override
    public int getYOrigin() {
        return guiTop;
    }

    @Override
    protected void initializeTabManager() {
        getTabManager().clearTabs();

        getTabManager().registerTab(new TabBookBinder(References.InternalNames.GUIComponents.BookBinder.TabBookBinder, new ItemStack(GeneralRegistry.Items.iSmithingsGuide, 1), StatCollector.translateToLocal(TranslationKeys.GUI.BookBinder.ToolTipTabBookBinder)));
    }


}

