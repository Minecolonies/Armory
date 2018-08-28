package com.ldtteam.armory.common.logic.initialization;

import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import com.ldtteam.armory.common.structure.conduit.StructureFactoryConduit;
import com.ldtteam.armory.common.structure.forge.StructureFactoryForge;
import com.ldtteam.smithscore.common.structures.StructureRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonStructureInitializer extends IInitializationComponent.Impl {

    private static final CommonStructureInitializer INSTANCE = new CommonStructureInitializer();

    public static CommonStructureInitializer getInstance() {
        return INSTANCE;
    }

    private CommonStructureInitializer() {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        StructureRegistry.getServerInstance().registerStructureFactory(new StructureFactoryForge());
        StructureRegistry.getServerInstance().registerStructureFactory(new StructureFactoryConduit());
    }
}
