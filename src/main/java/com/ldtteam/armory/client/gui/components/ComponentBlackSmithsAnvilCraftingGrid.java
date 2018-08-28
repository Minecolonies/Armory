package com.ldtteam.armory.client.gui.components;

import com.ldtteam.smithscore.client.gui.components.core.ComponentOrientation;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentBorder;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentProgressBar;
import com.ldtteam.smithscore.client.gui.components.implementations.ComponentSlot;
import com.ldtteam.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.ldtteam.smithscore.client.gui.state.CoreComponentState;
import com.ldtteam.smithscore.client.gui.state.IGUIComponentState;
import com.ldtteam.smithscore.client.gui.state.SlotComponentState;
import com.ldtteam.smithscore.common.inventory.ContainerSmithsCore;
import com.ldtteam.smithscore.util.client.Textures;
import com.ldtteam.smithscore.util.client.color.Colors;
import com.ldtteam.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.Slot;

/**
 * Author Marc (Created on: 16.06.2016)
 */
public class ComponentBlackSmithsAnvilCraftingGrid extends Component5X5CraftingGrid {
    public ComponentBlackSmithsAnvilCraftingGrid(String uniqueID, IGUIBasedComponentHost parent, IGUIComponentState state, Coordinate2D rootAnchorPixel, int startSlotIndexCraftingGrid, int endSlotIndexCraftingGrid, int craftingProductionSlotIndex, ContainerSmithsCore crafter) {
        super(uniqueID, parent, state, rootAnchorPixel, startSlotIndexCraftingGrid, endSlotIndexCraftingGrid, craftingProductionSlotIndex, crafter);
    }

    @Override
    public void registerComponents(IGUIBasedComponentHost host) {
        registerNewComponent(new ComponentBorder(getID() + ".Border", host, new Coordinate2D(0, 0), 162, 104, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));

        for (int slotIndex = startSlotIndexCraftingGrid; slotIndex < endSlotIndexCraftingGrid; slotIndex++) {
            TextureAtlasSprite holoSprite = null;

            if (slotIndex - startSlotIndexCraftingGrid == 11) {
                holoSprite = com.ldtteam.armory.api.util.client.Textures.Gui.Anvil.HOLOWPICKAXE.getIcon();
            } else if (slotIndex - startSlotIndexCraftingGrid == 13) {
                holoSprite = com.ldtteam.armory.api.util.client.Textures.Gui.Anvil.HOLOWBOOK.getIcon();
            }

            Slot slot = crafter.getSlot(slotIndex);
            Coordinate2D slotLocation = new Coordinate2D(slot.xPos - 1, slot.yPos - 1).getTranslatedCoordinate(getLocalCoordinate().getInvertedCoordinate());

            registerNewComponent(new ComponentSlot(getID() + ".Grid.Slot." + (slotIndex - startSlotIndexCraftingGrid), new SlotComponentState(null, slot, crafter.getContainerInventory(), holoSprite), host, slotLocation, Colors.DEFAULT));
        }

        registerNewComponent(new ComponentProgressBar(getID() + ".Progress", host, new CoreComponentState(), new Coordinate2D(105, 45), ComponentOrientation.HORIZONTALLEFTTORIGHT, Textures.Gui.Basic.Components.ARROWEMPTY, Textures.Gui.Basic.Components.ARROWFULL));

        Slot slot = crafter.getSlot(craftingProductionSlotIndex);
        Coordinate2D slotLocation = new Coordinate2D(slot.xPos - 1, slot.yPos - 1).getTranslatedCoordinate(getLocalCoordinate().getInvertedCoordinate());
        registerNewComponent(new ComponentSlot(getID() + ".Out", new SlotComponentState(null, slot, crafter.getContainerInventory(), null), host, slotLocation, Colors.DEFAULT));
    }

    @Override
    public IGUIBasedComponentHost getRootGuiObject() {
        return super.getRootGuiObject();
    }
}
