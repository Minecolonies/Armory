package com.ldtteam.armory.client.model.loaders;

import com.google.common.collect.ImmutableList;
import com.ldtteam.armory.api.util.references.ModLogger;
import com.ldtteam.armory.client.model.item.unbaked.HeatedItemItemModel;
import com.ldtteam.armory.client.model.item.unbaked.components.TemperatureBarComponentModel;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 */
public class HeatedItemModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".hi-armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // HeatedItem armory extension. Foo.HI-armory.json
    }

    @Override
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws IOException {
        try {
            if(!modelLocation.getResourcePath().startsWith("models" + File.separator + "item" + File.separator))
            {
                modelLocation = new ResourceLocation(modelLocation.getResourceDomain(), "models" + File.separator + "item" + File.separator + modelLocation.getResourcePath());
            }

            //Load the default definition of the model as defined by the registrar first.
            Map<String, String> textures = ModelHelper.loadTexturesFromJson(modelLocation);

            //Create the final list builder.
            ImmutableList.Builder<ResourceLocation> builder = ImmutableList.builder();

            //Iterate over all entries to define what they are
            //At least required is a layer if type base for the model to load succesfully.
            //Possible layer types:
            //    * Temp (Component texture used when the armor is not broken)
            for (Map.Entry<String, String> entry : textures.entrySet()) {
                String name = entry.getKey();

                ResourceLocation location = null;
                TemperatureBarComponentModel partModel = null;

                try {
                    if (name.startsWith("temp")) {
                        //Standard Layer
                        location = new ResourceLocation(entry.getValue());
                    } else {
                        //Unknown layer, warning and skipping.
                        ModLogger.getInstance().warn(String.format("HeatedItemModel {} has invalid texture entry {}; Skipping layer.", modelLocation, name));
                        continue;
                    }
                    //If the texture was added to any layer, add it to the list of used textures.
                    if (location != null) {
                        builder.add(location);
                    }
                } catch (NumberFormatException e) {
                    ModLogger.getInstance().error(String.format("HeatedItemModel{} has invalid texture entry {}; Skipping layer.", modelLocation, name));
                }
            }

            if (builder.build().size() == 0) {
                ModLogger.getInstance().error("A given model definition for the HeatedItems did not contain any gauges.");
                return ModelLoaderRegistry.getMissingModel();
            }

            //Construct the new unbaked model from the collected data.
            IModel output = new HeatedItemItemModel(builder.build());

            return output;
        } catch (IOException e) {
            ModLogger.getInstance().error(String.format("Could not load HeatedItemModel {}", modelLocation.toString()));
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
