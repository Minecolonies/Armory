package com.ldtteam.armory.client.gui.implementations.fireplace;

import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.tileentity.TileEntityFireplace;
import com.ldtteam.smithscore.client.gui.components.core.ComponentConnectionType;
import com.ldtteam.smithscore.client.gui.components.core.ComponentOrientation;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentBorder;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentPlayerInventory;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentSlot;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.ldtteam.smithscore.client.gui.state.CoreComponentState;
import com.ldtteam.smithscore.client.gui.state.SlotComponentState;
import com.ldtteam.smithscore.client.gui.tabs.implementations.CoreTab;
import com.ldtteam.smithscore.common.inventory.ContainerSmithsCore;
import com.ldtteam.smithscore.util.client.Textures;
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 25.01.2016.
 */
public class TabFireplaceFood extends CoreTab {

    GuiFireplace firePit;

    public TabFireplaceFood(String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
        super(uniqueID, root, new CoreComponentState(), displayStack, tabColor, toolTipString);

        firePit = (GuiFireplace) root;
    }


    /**
     * Function used to register the sub components of this ComponentHost
     *
     * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
     */
    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Forge.BACKGROUND, host, new Coordinate2D(0, 0), GuiFireplace.GUI.getWidth(), 80, com.ldtteam.armory.api.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Forge.INVENTORY, host, new Coordinate2D(0, 76), com.ldtteam.armory.api.util.client.Colors.DEFAULT, ((ContainerSmithsCore) firePit.inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Fireplace.FLAMEONE, host, new CoreComponentState(null), new Coordinate2D(44, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Fireplace.COOKINGPROGRESS, host, new CoreComponentState(null), new Coordinate2D(68, 40), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.ARROWEMPTY, Textures.Gui.Basic.Components.ARROWFULL));

        for (int tSlotIndex = 0; tSlotIndex < (TileEntityFireplace.FOODCOOKINPUTCOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Fireplace.SLOT + slot.getSlotIndex(), new SlotComponentState(null, slot, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, slot, com.ldtteam.armory.api.util.client.Colors.DEFAULT));
        }


        for (int tSlotIndex = 0; tSlotIndex < (TileEntityFireplace.FOODCOOKOUTPUTCOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex + TileEntityFireplace.FOODCOOKINPUTCOUNT);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Fireplace.SLOT + slot.getSlotIndex(), new SlotComponentState(null, slot, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, slot, com.ldtteam.armory.api.util.client.Colors.DEFAULT));
        }

        for (int tSlotIndex = 0; tSlotIndex < (TileEntityFireplace.FUELSLOTCOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex + TileEntityFireplace.FOODCOOKINPUTCOUNT + TileEntityFireplace.FOODCOOKOUTPUTCOUNT);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Fireplace.SLOT + slot.getSlotIndex(), new SlotComponentState(null, slot, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, slot, com.ldtteam.armory.api.util.client.Colors.DEFAULT));
        }

    }
}
