package com.smithsmodding.armory.common;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.common.logic.initialization.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Created by Orion on 26-4-2014
 * <p>
 * com.Orion.armory.common proxy for armory
 */
public class ArmoryCommonProxy {

    public void registerInitializationComponents(List<IInitializationComponent> registry)
    {
        registry.add(CommonConfigInitializer.getInstance());
        registry.add(CommonEventHandlerInitializer.getInstance());
        registry.add(CommonStructureInitializer.getInstance());
        registry.add(CommonMedievalInitializer.getInstance());
        registry.add(CommonSystemInitializer.getInstance());
        registry.add(CommonCommandInitializer.getInstance());
    }

    public EntityPlayer getPlayer(@Nonnull MessageContext messageContext) {
        return messageContext.getServerHandler().player;
    }

}
