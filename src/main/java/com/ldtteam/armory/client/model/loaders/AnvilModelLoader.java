package com.ldtteam.armory.client.model.loaders;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.armory.api.client.model.deserializers.definition.AnvilModelDefinition;
import com.ldtteam.armory.api.common.events.client.model.block.BlackSmithsAnvilModelTextureLoadEvent;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.util.references.ModLogger;
import com.ldtteam.armory.client.model.block.unbaked.BlackSmithsAnvilModel;
import com.ldtteam.armory.common.api.ArmoryAPI;
import com.ldtteam.smithscore.client.model.unbaked.SmithsCoreOBJModel;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.obj.OBJModel;
import org.jetbrains.annotations.NotNull;

import javax.vecmath.Vector4f;
import java.io.IOException;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilModelLoader implements ICustomModelLoader {

    public static final String EXTENSION = ".anvil-armory";

    @Override
    public boolean accepts(@NotNull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // Anvil armory extension. Foo.Anvil-armory.json
    }

    @Override
    public IModel loadModel(@NotNull ResourceLocation modelLocation) throws IOException {
        try {
            modelLocation = ModelHelper.getModelLocation(modelLocation);
            modelLocation = new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath().replace(".json", ""));


            AnvilModelDefinition modelDefinition = AnvilModelDefinition.loadModel(modelLocation);

            IModel objModel = ModelLoaderRegistry.getModel(new ResourceLocation(modelDefinition.getModelPath()));

            BlackSmithsAnvilModelTextureLoadEvent event = new BlackSmithsAnvilModelTextureLoadEvent();
            event.PostClient();

            modelDefinition.getTextureTopPaths().putAll(event.getAdditionalTopTextureLayers());
            modelDefinition.getTextureBottomPaths().putAll(event.getAdditionalBottomTextureLayers());

            BlackSmithsAnvilModel model = new BlackSmithsAnvilModel(objModel);

            for (IAnvilMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
                if (modelDefinition.getTextureTopPaths().containsKey(material.getRegistryName()) || modelDefinition.getTextureBottomPaths().containsKey(material.getRegistryName())) {
                    ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

                    if (modelDefinition.getTextureTopPaths().containsKey(material.getRegistryName()))
                        builder.put("#Anvil", modelDefinition.getTextureTopPaths().get(material.getRegistryName()));

                    if (modelDefinition.getTextureBottomPaths().containsKey(material.getRegistryName()))
                        builder.put("#Bottom", modelDefinition.getTextureBottomPaths().get(material.getRegistryName()));


                    model.registerNewMaterializedModel(material, ((SmithsCoreOBJModel) objModel).retexture(builder.build()));
                } else {
                    OBJModel newModel = (OBJModel) ModelHelper.forceLoadOBJModel(new ResourceLocation(modelDefinition.getModelPath()));

                    OBJModel.Material materialOBJ = newModel.getMatLib().getMaterial("Anvil");

                    Vector4f colorVec = new Vector4f();
                    colorVec.w = 1F;
                    colorVec.x = material.getRenderInfo().getVertexColor().getRedFloat();
                    colorVec.y = material.getRenderInfo().getVertexColor().getGreenFloat();
                    colorVec.z = material.getRenderInfo().getVertexColor().getBlueFloat();

                    materialOBJ.setColor(colorVec);

                    model.registerNewMaterializedModel(material, newModel);
                }
            }

            return model;
        } catch (Exception e) {
            ModLogger.getInstance().error(String.format("Could not load Anvil-Model %s", modelLocation.toString()));
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();

    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }

}
