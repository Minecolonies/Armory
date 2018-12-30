package com.ldtteam.armory.client.model.loaders;

import com.ldtteam.armory.api.client.model.deserializers.MaterializedItemModelDeserializer;
import com.ldtteam.armory.api.client.model.deserializers.definition.MaterializedItemModelDefinition;
import com.ldtteam.armory.api.util.references.ModLogger;
import com.ldtteam.armory.client.model.item.unbaked.MaterializedItemModel;
import com.ldtteam.armory.client.textures.MaterializedTextureCreator;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import javax.annotation.Nonnull;
import java.io.File;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".mim-armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        modelLocation = ModelHelper.getModelLocation(modelLocation);

        try {
            MaterializedItemModelDefinition definition = MaterializedItemModelDeserializer.INSTANCE.deserialize(modelLocation);

            MaterializedTextureCreator.registerBaseTexture(definition.getCoreTexture());

            return new MaterializedItemModel(definition.getCoreTexture(), definition.getTransforms());
        } catch (Exception ex) {
            ModLogger.getInstance().error(String.format("Could not load %s as MaterializedModel", modelLocation.toString()));
        }

        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
