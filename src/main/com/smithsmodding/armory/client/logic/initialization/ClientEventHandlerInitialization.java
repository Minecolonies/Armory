package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.util.client.Textures;
import com.smithsmodding.armory.client.handler.CancelableLayerCustomHeadHandler;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.SmithsCore;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientEventHandlerInitialization extends IInitializationComponent.Impl {

    private static final ClientEventHandlerInitialization INSTANCE = new ClientEventHandlerInitialization();

    public static ClientEventHandlerInitialization getInstance () {
        return INSTANCE;
    }

    private ClientEventHandlerInitialization () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        MaterializedTextureCreator materializedTextureCreator = new MaterializedTextureCreator();
        SmithsCore.getRegistry().getClientBus().register(materializedTextureCreator);
        SmithsCore.getRegistry().getClientBus().register(new CancelableLayerCustomHeadHandler());
        MinecraftForge.EVENT_BUS.register(materializedTextureCreator);
        ((IReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener(materializedTextureCreator);

        Textures textureConstants = new Textures();
        SmithsCore.getRegistry().getClientBus().register(textureConstants);
        MinecraftForge.EVENT_BUS.register(textureConstants);
    }
}
