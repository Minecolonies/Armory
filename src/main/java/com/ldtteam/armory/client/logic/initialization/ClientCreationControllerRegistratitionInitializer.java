package com.ldtteam.armory.client.logic.initialization;

import com.ldtteam.armory.api.client.textures.creation.ICreationController;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.client.textures.creators.AddonTextureCreator;
import com.ldtteam.armory.client.textures.creators.AnvilTextureCreator;
import com.ldtteam.armory.client.textures.creators.CoreTextureCreator;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/29/2017.
 */
@Mod.EventBusSubscriber(modid = References.General.MOD_ID, value = Side.CLIENT)
public class ClientCreationControllerRegistratitionInitializer {

    @SubscribeEvent
    public static void onTextureCreationRegistration(@Nonnull RegistryEvent.Register<ICreationController> creationControllerRegisterEvent) {
        creationControllerRegisterEvent.getRegistry().register(new CoreTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_CORE));
        creationControllerRegisterEvent.getRegistry().register(new AddonTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_ADDON));
        creationControllerRegisterEvent.getRegistry().register(new AnvilTextureCreator().setRegistryName(References.InternalNames.TextureCreation.TCN_ANVIL));
    }
}
