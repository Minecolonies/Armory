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
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;


/**
 * Created by Marc on 24.01.2016.
 */
public class TabFireplaceMeltingMetal extends CoreTab {

    GuiFireplace firePit;

    public TabFireplaceMeltingMetal(String uniqueID, IGUIBasedTabHost root, ItemStack displayStack, MinecraftColor tabColor, String toolTipString) {
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
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Fireplace.BACKGROUND, host, new Coordinate2D(0, 0), GuiFireplace.GUI.getWidth(), GuiFireplace.GUI.getHeigth() - (ComponentPlayerInventory.DEFAULT_HEIGHT - 3), com.ldtteam.armory.api.util.client.Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Fireplace.INVENTORY, host, new Coordinate2D(0, 76), com.ldtteam.armory.api.util.client.Colors.DEFAULT, ((ContainerSmithsCore) firePit.inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWDIRECTCONNECT));

        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Fireplace.FLAMEONE, host, new CoreComponentState(null), new Coordinate2D(62, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Fireplace.FLAMETWO, host, new CoreComponentState(null), new Coordinate2D(80, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));
        host.registerNewComponent(new ComponentProgressBar(References.InternalNames.GUIComponents.Fireplace.FLAMETHREE, host, new CoreComponentState(null), new Coordinate2D(98, 40), ComponentOrientation.VERTICALBOTTOMTOTOP, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEEMPTY, com.ldtteam.smithscore.util.client.Textures.Gui.Basic.Components.FLAMEFULL));

        for (int tSlotIndex = 0; tSlotIndex < (TileEntityFireplace.INGOTSLOTCOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Fireplace.SLOT + tSlotIndex, new SlotComponentState(null, tSlotIndex, ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, slot, com.ldtteam.armory.api.util.client.Colors.DEFAULT));
        }

        for (int tSlotIndex = 0; tSlotIndex < (TileEntityFireplace.FUELSLOTCOUNT); tSlotIndex++) {
            Slot slot = firePit.inventorySlots.inventorySlots.get(tSlotIndex + TileEntityFireplace.INGOTSLOTCOUNT);

            host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Fireplace.SLOT + slot.getSlotIndex(), new SlotComponentState(null, slot.getSlotIndex(), ((ContainerSmithsCore) firePit.inventorySlots).getContainerInventory(), null), host, slot, com.ldtteam.armory.api.util.client.Colors.DEFAULT));
        }
    }
}
