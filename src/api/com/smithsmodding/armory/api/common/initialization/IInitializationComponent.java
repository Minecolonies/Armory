package com.smithsmodding.armory.api.common.initialization;

import net.minecraftforge.fml.common.event.*;

import javax.annotation.Nonnull;

/**
 * Initialization component injected into Armories loading sequence.
 * All methods of this class are mapped from an FMLState that they represent.
 *
 * As of now no external mods can inject into this mechanic, however future release might allow for injection
 * depending on how MC 1.13+ ModLoader mechanics change.
 */
public interface IInitializationComponent
{

    default Integer getPriority() { return 100; }

    default void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {}

    default void onInit(@Nonnull FMLInitializationEvent initializationEvent) {}

    default void onPostInit(@Nonnull FMLPostInitializationEvent postInitializationEvent) {}

    default void onServerStarting(@Nonnull FMLServerStartingEvent serverStartingEvent) {}

    default void onServerStarted(@Nonnull FMLServerStartedEvent serverStartedEvent) {}

    default void onServerStopping(@Nonnull FMLServerStoppingEvent serverStoppingEvent) {}

    default void onServerStopped(@Nonnull FMLServerStoppedEvent serverStoppedEvent) {}

    default void onLoadCompleted(@Nonnull FMLLoadCompleteEvent event) {}

    abstract class Impl implements IInitializationComponent
    {

    }
}
