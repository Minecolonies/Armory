package com.ldtteam.armory.api.util.client;

import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public final class ModelLocationHelper
{

    private ModelLocationHelper()
    {
        throw new IllegalStateException("Tried to initialize: ModelLocationHelper but this is a Utility class.");
    }
    
    public static ResourceLocation checkForItemPrefix(@NotNull final ResourceLocation location)
    {
        if (!location.getResourcePath().startsWith("item/") && !location.getResourcePath().startsWith("models/item/"))
            return new ResourceLocation(location.getResourceDomain(), "models/item/" + location.getResourcePath());
        
        return location;
    }

    public static ResourceLocation checkForBlockPrefix(@NotNull final ResourceLocation location)
    {
        if (!location.getResourcePath().startsWith("block/") && !location.getResourcePath().startsWith("models/block/"))
            return new ResourceLocation(location.getResourceDomain(), "models/block/" + location.getResourcePath());

        return location;
    }
}
