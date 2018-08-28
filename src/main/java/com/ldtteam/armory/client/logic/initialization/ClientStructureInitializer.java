package com.ldtteam.armory.client.logic.initialization;

import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import com.ldtteam.armory.common.structure.conduit.StructureFactoryConduit;
import com.ldtteam.armory.common.structure.forge.StructureFactoryForge;
import com.ldtteam.smithscore.common.structures.StructureRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientStructureInitializer extends IInitializationComponent.Impl {

    private static final ClientStructureInitializer INSTANCE = new ClientStructureInitializer();

    public static ClientStructureInitializer getInstance() {
        return INSTANCE;
    }

    private ClientStructureInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        StructureRegistry.getClientInstance().registerStructureFactory(new StructureFactoryForge());
        StructureRegistry.getClientInstance().registerStructureFactory(new StructureFactoryConduit());
    }
}
