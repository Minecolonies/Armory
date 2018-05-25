package com.smithsmodding.armory.client.gui.implementations.smithingsguide;

import com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.implementations.IntroductionPage;
import com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.implementations.MedievalPage;
import com.smithsmodding.smithscore.client.gui.GuiContainerSmithsCore;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedLedgerHost;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.smithsmodding.smithscore.common.inventory.ContainerSmithsCore;

import javax.annotation.Nonnull;

public class GuiSmithingsGuide extends GuiContainerSmithsCore
{

    public GuiSmithingsGuide(@Nonnull final ContainerSmithsCore container)
    {
        super(container);
    }

    @Override
    public void registerLedgers(@Nonnull final IGUIBasedLedgerHost parent)
    {
        //Noop.
    }

    @Override
    public void registerTabs(@Nonnull final IGUIBasedTabHost host)
    {
        host.registerNewTab(new IntroductionPage(host));
        host.registerNewTab(new MedievalPage(host));
    }
}
