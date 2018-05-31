package com.smithsmodding.armory.client.gui.components;

import com.smithsmodding.smithscore.client.gui.components.core.ComponentOrientation;
import com.smithsmodding.smithscore.client.gui.components.core.IGUIComponent;
import com.smithsmodding.smithscore.client.gui.components.implementations.*;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.management.IGUIManager;
import com.smithsmodding.smithscore.client.gui.management.IRenderManager;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.util.client.Textures;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author Marc (Created on: 16.06.2016)
 */
public class ComponentReadOnly5X5CraftingGrid extends CoreComponent implements IGUIBasedComponentHost
{

    @Nonnull
    protected final LinkedHashMap<String, IGUIComponent> componentHashMap;
    @Nonnull
    protected final List<ItemStack>                      stacks;
    @Nonnull
    protected final MinecraftColor                       color;

    public ComponentReadOnly5X5CraftingGrid(
      String uniqueID,
      IGUIBasedComponentHost parent,
      IGUIComponentState state,
      Coordinate2D rootAnchorPixel,
      List<ItemStack> stacks,
      @Nonnull final MinecraftColor color)
    {
        super(uniqueID, parent, state, rootAnchorPixel, 162, 104);
        this.stacks = stacks;
        this.color = color;


        this.componentHashMap = new LinkedHashMap<>();
    }

    @Override
    public void update(int mouseX, int mouseY, float partialTickTime)
    {
        //NOOP;
    }

    @Override
    public void drawBackground(int mouseX, int mouseY)
    {
        //NOOP; RenderManager does the rendering of ComponentHosts for us.
    }

    @Override
    public void drawForeground(int mouseX, int mouseY)
    {
        //NOOP; RenderManager does the rendering of ComponentHosts for us.
    }

    @Override
    public void registerComponents(IGUIBasedComponentHost host)
    {
        registerNewComponent(new ComponentBorder(getID() + ".Border",
          host,
          new Coordinate2D(0, 0),
          162,
          104,
          color,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards,
          ComponentBorder.CornerTypes.Inwards));

        for (int slotIndex = 0; slotIndex < 25; slotIndex++)
        {
            Coordinate2D slotLocation = new Coordinate2D(7 + (18 * (slotIndex % 5)), 7 + (18 * (slotIndex / 5)));

            registerNewComponent(new ComponentImage(getID() + ".Grid.Slot." + slotIndex + ".Background", new CoreComponentState(), host, slotLocation,
              com.smithsmodding.armory.api.util.client.Textures.Gui.Book.SLOT));
            registerNewComponent(new ComponentItemStackDisplay(getID() + ".Grid.Slot." + slotIndex,
              host,
              new CoreComponentState(),
              slotLocation.getTranslatedCoordinate(new Coordinate2D(1, 1)),
              stacks.get(slotIndex)));
        }

        registerNewComponent(new ComponentProgressBar(getID() + ".Progress",
          host,
          new CoreComponentState(),
          new Coordinate2D(105, 45),
          ComponentOrientation.HORIZONTALLEFTTORIGHT,
          Textures.Gui.Basic.Components.ARROWEMPTY,
          Textures.Gui.Basic.Components.ARROWFULL));

        Coordinate2D slotLocation = new Coordinate2D(137, 43);
        registerNewComponent(new ComponentImage(getID() + ".Out.Background", new CoreComponentState(), host, slotLocation,
          com.smithsmodding.armory.api.util.client.Textures.Gui.Book.SLOT));
        registerNewComponent(new ComponentItemStackDisplay(getID() + ".Out",
          host,
          new CoreComponentState(),
          slotLocation.getTranslatedCoordinate(new Coordinate2D(1, 1)),
          stacks.get(25)));
    }

    @Override
    public void registerNewComponent(@Nonnull IGUIComponent component)
    {
        componentHashMap.put(component.getID(), component);

        if (component instanceof IGUIBasedComponentHost)
        {
            ((IGUIBasedComponentHost) component).registerComponents((IGUIBasedComponentHost) component);
        }
    }

    @Override
    public IGUIBasedComponentHost getRootGuiObject()
    {
        return parent.getRootGuiObject();
    }

    @Override
    public IGUIManager getRootManager()
    {
        return parent.getRootManager();
    }

    @Nonnull
    @Override
    public LinkedHashMap<String, IGUIComponent> getAllComponents()
    {
        return componentHashMap;
    }

    @Override
    public IGUIComponent getComponentByID(String uniqueUIID)
    {
        return componentHashMap.get(uniqueUIID);
    }

    @Override
    public void drawHoveringText(List<String> textLines, int x, int y, FontRenderer font)
    {
        getComponentHost().drawHoveringText(textLines, x, y, font);
    }

    @Override
    public IRenderManager getRenderManager()
    {
        return getComponentHost().getRenderManager();
    }

    @Override
    public int getDefaultDisplayVerticalOffset()
    {
        return getComponentHost().getDefaultDisplayVerticalOffset();
    }

    @Override
    public IGUIManager getManager()
    {
        return parent.getManager();
    }

    @Override
    public void setManager(IGUIManager newManager)
    {
        parent.setManager(newManager);
    }
}
