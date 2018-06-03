package com.smithsmodding.armory.client.gui.implementations.blacksmithsanvil;

import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.ModInventories;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.gui.components.ComponentBlackSmithsAnvilCraftingGrid;
import com.smithsmodding.armory.client.gui.components.ComponentExperienceLabel;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.components.core.ComponentConnectionType;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.legders.core.LedgerConnectionSide;
import com.smithsmodding.smithscore.client.gui.legders.implementations.CoreLedger;
import com.smithsmodding.smithscore.client.gui.legders.implementations.InformationLedger;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.LedgerComponentState;
import com.smithsmodding.smithscore.client.gui.state.SlotComponentState;
import com.smithsmodding.smithscore.client.gui.state.TextboxComponentState;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;
import com.smithsmodding.smithscore.util.client.CustomResource;
import com.smithsmodding.smithscore.util.client.color.Colors;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Author Marc (Created on: 16.06.2016)
 */
public class GuiBlacksmithsAnvil extends GuiContainerSmithsCore {

    public GuiBlacksmithsAnvil(@Nonnull ContainerSmithsCore container) {
        super(container);
    }

    @Override
    public void registerLedgers(IGUIBasedLedgerHost parent) {
        ArrayList<String> information = new ArrayList<String>();

        information.add(I18n.format(TranslationKeys.Gui.Anvil.InfoLine1));
        information.add(I18n.format(TranslationKeys.Gui.Anvil.InfoLine2));

        registerNewLedger(new InformationLedger(getID() + ".Ledgers.Information", this, LedgerConnectionSide.LEFT, I18n.format(TranslationKeys.Gui.InformationTitel), new MinecraftColor(MinecraftColor.YELLOW), information));
        registerNewLedger(new MaterialLedger(getID() + ".Ledgers.Material", this, LedgerConnectionSide.LEFT, Textures.Gui.Anvil.LOGO_SMALL, I18n.format(TranslationKeys.Gui.Anvil.MaterialTitel), new MinecraftColor(MinecraftColor.BLUE)));
    }

    @Override
    public void registerComponents(@Nonnull IGUIBasedComponentHost host) {
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.BACKGROUND, host, new Coordinate2D(0, 0), 215, 175, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentPlayerInventory(References.InternalNames.GUIComponents.Anvil.PLAYERINVENTORY, host, new Coordinate2D(20, 172), Colors.DEFAULT, ((ContainerSmithsCore) inventorySlots).getPlayerInventory(), ComponentConnectionType.BELOWSMALLER));
        host.registerNewComponent(new ComponentBlackSmithsAnvilCraftingGrid(References.InternalNames.GUIComponents.Anvil.EXTENDEDCRAFTING, host, new CoreComponentState(), new Coordinate2D(10, 51), 0, ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS, (ContainerSmithsCore) inventorySlots));
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.TEXTBOXBORDER, host, new Coordinate2D(61, 7), 111, 30, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentBorder(References.InternalNames.GUIComponents.Anvil.TOOLSLOTBORDER, host, new Coordinate2D(178, 103), 30, 52, Colors.DEFAULT, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Anvil.HAMMERSLOT, new SlotComponentState(null, inventorySlots.getSlot(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS), ((ContainerSmithsCore) inventorySlots).getContainerInventory(), Textures.Gui.Anvil.HOLOWHAMMER.getIcon()), host, inventorySlots.getSlot(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS), Colors.DEFAULT));
        host.registerNewComponent(new ComponentSlot(References.InternalNames.GUIComponents.Anvil.TONGSLOT, new SlotComponentState(null, inventorySlots.getSlot(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_HAMMERSLOTS), ((ContainerSmithsCore) inventorySlots).getContainerInventory(), Textures.Gui.Anvil.HOLOWTONGS.getIcon()), host, inventorySlots.getSlot(ModInventories.TileEntityBlackSmithsAnvil.MAX_CRAFTINGSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_OUTPUTSLOTS + ModInventories.TileEntityBlackSmithsAnvil.MAX_TONGSLOTS), Colors.DEFAULT));
        host.registerNewComponent(new ComponentImage(References.InternalNames.GUIComponents.Anvil.LOGO, new CoreComponentState(), host, new Coordinate2D(17, 7), Textures.Gui.Anvil.LOGO_BIG));
        host.registerNewComponent(new ComponentExperienceLabel(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL, host, new CoreComponentState(), new Coordinate2D(115, 78)));
        host.registerNewComponent(new ComponentTextbox(References.InternalNames.GUIComponents.Anvil.TEXTBOX, new TextboxComponentState(null), host, new Coordinate2D(65, 11), 102, 22));
    }

    public class MaterialLedger extends CoreLedger
    {

        Plane maxArea;
        @Nonnull
        private String currentDurabilityLabel = "";
        @Nonnull
        private String maximalDurabilityLabel = "";
        @Nonnull
        private String materialLabel          = "";

        public MaterialLedger(String uniqueID, IGUIBasedLedgerHost root, LedgerConnectionSide side, @Nonnull CustomResource ledgerIcon, String translatedLedgerHeader, MinecraftColor color) {
            super(uniqueID, new LedgerComponentState(), root, side, ledgerIcon, translatedLedgerHeader, color);
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon width that describes the maximum width of the Ledger when expanded.
         */
        @Override
        public int getMaxWidth() {
            return maxArea.getWidth();
        }

        /**
         * Method used by the rendering and animation system to determine the max size of the Ledger.
         *
         * @return An int bigger then 16 plus the icon height that describes the maximum height of the Ledger when expanded.
         */
        @Override
        public int getMaxHeight() {
            return maxArea.getHeigth();
        }

        /**
         * Function used to register the sub components of this ComponentHost
         *
         * @param host This ComponentHosts host. For the Root GUIObject a reference to itself will be passed in..
         */
        @Override
        public void registerComponents(IGUIBasedComponentHost host) {
            super.registerComponents(host);

            currentDurabilityLabel = I18n.format(TranslationKeys.Gui.Anvil.MaterialCurrent) + ": 0";
            maximalDurabilityLabel = I18n.format(TranslationKeys.Gui.Anvil.MaterialMax) + ": 0";
            materialLabel = I18n.format(TranslationKeys.Gui.Anvil.Material) + ": 0";

            registerNewComponent(new ComponentLabel(getID() + ".material",
              host,
              new CoreComponentState(),
              new Coordinate2D(10, 26),
              new MinecraftColor(MinecraftColor.WHITE),
              Minecraft.getMinecraft().fontRenderer,
              materialLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Anvil.Material) + ": " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".maxDurability",
              host,
              new CoreComponentState(),
              new Coordinate2D(10, 26 + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3),
              new MinecraftColor(MinecraftColor.WHITE),
              Minecraft.getMinecraft().fontRenderer,
              maximalDurabilityLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Anvil.MaterialMax) + ": " + getManager().getLabelContents(this);
                }
            });

            registerNewComponent(new ComponentLabel(getID() + ".currentDurability",
              host,
              new CoreComponentState(),
              new Coordinate2D(10, 26 + 2 * (Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 3)),
              new MinecraftColor(MinecraftColor.WHITE),
              Minecraft
                .getMinecraft().fontRenderer,
              currentDurabilityLabel) {
                /**
                 * Method gets called before the component gets rendered, allows for animations to calculate through.
                 *
                 * @param mouseX          The X-Coordinate of the mouse.
                 * @param mouseY          The Y-Coordinate of the mouse.
                 * @param partialTickTime The partial tick time, used to calculate fluent animations.
                 */
                @Override
                public void update(int mouseX, int mouseY, float partialTickTime) {
                    this.displayedText = I18n.format(TranslationKeys.Gui.Anvil.MaterialCurrent) + ": " + getManager().getLabelContents(this);
                }
            });

            Plane area = new Plane(0, 0, 0, 0);

            for (IGUIComponent component : getAllComponents().values()) {
                area.IncludeCoordinate(new Plane(component.getLocalCoordinate(), component.getSize().getWidth(), component.getSize().getHeigth()));
            }

            maxArea = new Plane(new Coordinate2D(0, 0), area.getWidth() + 10, area.getHeigth() + 10);
        }
    }
}
