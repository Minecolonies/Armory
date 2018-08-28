package com.ldtteam.armory.common.logic.initialization;

import com.ldtteam.armory.Armory;
import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import com.ldtteam.armory.common.handlers.EntityPickupTargetSlotEventHandler;
import com.ldtteam.armory.common.handlers.GuiHandler;
import com.ldtteam.smithscore.SmithsCore;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonEventHandlerInitializer extends IInitializationComponent.Impl {

    private static final CommonEventHandlerInitializer INSTANCE = new CommonEventHandlerInitializer();

    public static CommonEventHandlerInitializer getInstance() {
        return INSTANCE;
    }

    private CommonEventHandlerInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        NetworkRegistry.INSTANCE.registerGuiHandler(Armory.instance, new GuiHandler());

        MinecraftForge.EVENT_BUS.register(Armory.instance);

        SmithsCore.getRegistry().getCommonBus().register(new EntityPickupTargetSlotEventHandler());
    }
}
