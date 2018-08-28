package com.ldtteam.armory.common.logic;

import com.ldtteam.armory.Armory;
import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import net.minecraftforge.fml.common.event.*;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ArmoryInitializer
{
    private static ArmoryInitializer              ourInstance = new ArmoryInitializer();
    private        List<IInitializationComponent> components  = new ArrayList<>();

    private ArmoryInitializer()
    {
    }

    public static ArmoryInitializer getInstance()
    {
        return ourInstance;
    }

    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent)
    {
        Armory.proxy.registerInitializationComponents(components);
        components.forEach(c -> c.onPreInit(preInitializationEvent));
    }

    public void onInit(@Nonnull FMLInitializationEvent initializationEvent)
    {
        components.forEach(c -> c.onInit(initializationEvent));
    }

    public void onPostInit(@Nonnull FMLPostInitializationEvent postInitializationEvent)
    {
        components.forEach(c -> c.onPostInit(postInitializationEvent));
    }

    public void onServerStarting(@Nonnull FMLServerStartingEvent serverStartingEvent)
    {
        components.forEach(c -> c.onServerStarting(serverStartingEvent));
    }

    public void onServerStarted(@Nonnull FMLServerStartedEvent serverStartedEvent)
    {
        components.forEach(c -> c.onServerStarted(serverStartedEvent));
    }

    public void onServerStopping(@Nonnull FMLServerStoppingEvent serverStoppingEvent)
    {
        components.forEach(c -> c.onServerStopping(serverStoppingEvent));
    }

    public void onServerStopped(@Nonnull FMLServerStoppedEvent serverStoppedEvent)
    {
        components.forEach(c -> c.onServerStopped(serverStoppedEvent));
    }

    public void onLoadCompleted(@Nonnull FMLLoadCompleteEvent event)
    {
        components.forEach(c -> c.onLoadCompleted(event));
    }
}
