package com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.core;

import com.smithsmodding.armory.api.util.client.Colors;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentContentArea;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentScrollableArea;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedComponentHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.gui.state.IGUIComponentState;
import com.smithsmodding.smithscore.client.gui.tabs.implementations.CoreTab;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import com.smithsmodding.smithscore.util.common.positioning.Plane;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

public abstract class AbstractPage extends CoreTab
{
    public AbstractPage(
      @Nonnull final String uniqueID,
      @Nonnull final IGUIBasedTabHost root,
      @Nonnull final ItemStack displayStack,
      @Nonnull final String toolTipString)
    {
        super(root.getID() + ".pages." + uniqueID, root, new CoreComponentState(), displayStack, Colors.Guide.PAPYRUS, toolTipString);
    }

    @Nonnull
    @Override
    public Plane getSize()
    {
        return new Plane(0,0, 204, 224);
    }

    @Override
    public void registerComponents(@Nonnull final IGUIBasedComponentHost host)
    {
        host.registerNewComponent(new ComponentBorder(host.getID() + "border", host, new Coordinate2D(0,0), getSize().getWidth(), getSize().getHeigth(), Colors.Guide.PAPYRUS, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards, ComponentBorder.CornerTypes.Inwards));
        host.registerNewComponent(new Contents(getID() + ".contents", host, new CoreComponentState(), new Coordinate2D(7,7), 190, 210, this));
    }

    public abstract void registerPageContents(@Nonnull final ComponentContentArea host);

    private static class Contents extends ComponentScrollableArea
    {
        private final AbstractPage page;

        public Contents(
          @Nonnull final String uniqueID,
          @Nonnull final IGUIBasedComponentHost parent,
          @Nonnull final IGUIComponentState state,
          @Nonnull final Coordinate2D rootAnchorPixel,
          final int width, final int height, final AbstractPage page)
        {
            super(uniqueID, parent, state, rootAnchorPixel, width, height);
            this.page = page;
        }

        @Override
        public void registerContentComponents(@Nonnull final ComponentContentArea host)
        {
            page.registerPageContents(host);
        }
    }
}
