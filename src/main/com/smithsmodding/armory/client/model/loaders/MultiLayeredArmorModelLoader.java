package com.smithsmodding.armory.client.model.loaders;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.client.model.deserializers.ArmorModelLayerDeserializer;
import com.smithsmodding.armory.api.client.model.deserializers.MultiLayeredArmorModelDeserializer;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelPartDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.MultiLayeredArmorModelDefinition;
import com.smithsmodding.armory.api.common.armor.IMaterializableMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.events.client.model.item.MultiLayeredArmorModelTextureLoadEvent;
import com.smithsmodding.armory.api.util.common.armor.ArmorHelper;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.item.unbaked.MultiLayerArmorModel;
import com.smithsmodding.armory.client.model.item.unbaked.MultiLayerArmorPartModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorAddonComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorCoreComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.LoaderState;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.BiConsumer;

/**
 * Created by Marc on 06.12.2015.
 */
public class MultiLayeredArmorModelLoader implements ICustomModelLoader {
    public static final String EXTENSION = ".mla-armory";

    @Override
    public boolean accepts(@Nonnull ResourceLocation modelLocation) {
        return modelLocation.getResourcePath().endsWith(EXTENSION); // MLA armory extension. Foo.MLA-armory.json
    }

    @Override
    public IModel loadModel(@Nonnull ResourceLocation modelLocation) throws IOException {
        if (!Loader.instance().hasReachedState(LoaderState.POSTINITIALIZATION)) {
            return DummyModel.INSTANCE;
        }

        try {
            //Retrieve the Name of the armor.
            //The file name without the extension has to be equal to the Name used in Armories registry.
            IMultiComponentArmor armor = ArmorHelper.getArmorForModel(modelLocation);

            //If none is registered return missing model and print out an error.
            if (armor == null) {
                ModLogger.getInstance().error("The given model: " + modelLocation.toString() + " Is not registered to any armor known to armory.");
                return ModelLoaderRegistry.getMissingModel();
            }

            MultiLayeredArmorModelDefinition definition = processDefinition(armor, modelLocation);

            //Create the final texture list builder.
            ImmutableSet.Builder<ResourceLocation> textureBuilder = ImmutableSet.builder();

            //Create the builder for the part models.
            ImmutableMap.Builder<ResourceLocation, MultiLayerArmorPartModel> armorPartModelBuilder = ImmutableMap.builder();

            definition.getParts().forEach((ResourceLocation key, ArmorModelPartDefinition partDefinition) -> {
                ImmutableSet.Builder<ResourceLocation> partTextures = ImmutableSet.builder();

                partTextures.addAll(partDefinition.getBase().getTextures());
                partDefinition.getLayers().values().forEach(layerDefinition -> partTextures.addAll(layerDefinition.getTextures()));
                partDefinition.getBroken().values().forEach(layerDefinition -> partTextures.addAll(layerDefinition.getTextures()));

                ArmorCoreComponentModel baseModel = new ArmorCoreComponentModel(partDefinition.getBase());

                textureBuilder.addAll(partDefinition.getBase().getTextures());
                ImmutableMap.Builder<IMultiComponentArmorExtension, ArmorSubComponentModel> layerModelBuilder = ImmutableMap.builder();
                ImmutableMap.Builder<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenModelBuilder = ImmutableMap.builder();

                partDefinition.getLayers().forEach(new ModelMappingProcessingConsumer(modelLocation, layerModelBuilder, textureBuilder));
                partDefinition.getBroken().forEach(new ModelMappingProcessingConsumer(modelLocation, brokenModelBuilder, textureBuilder));

                armorPartModelBuilder.put(key, new MultiLayerArmorPartModel(armor, partTextures.build(), partDefinition.getPart(), baseModel, layerModelBuilder.build(), brokenModelBuilder.build(), definition.getTransforms()));
            });

            //Construct the new unbaked model from the collected data.
            IModel output = new MultiLayerArmorModel(armor, textureBuilder.build(), armorPartModelBuilder.build(), ImmutableMap.copyOf(definition.getTransforms()));

            // Load all textures we need in to the creator.
            MaterializedTextureCreator.registerBaseTexture(textureBuilder.build());

            return output;
        } catch (IOException e) {
            ModLogger.getInstance().error(String.format("Could not load multimodel {}", modelLocation.toString()));
            ModLogger.getInstance().error(e);
        }

        //If all fails return a Missing model.
        return ModelLoaderRegistry.getMissingModel();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        ArmorModelLayerDeserializer.instance.cache.invalidateAll();
        ArmorModelLayerDeserializer.instance.cache.cleanUp();
    }

    private class ModelMappingProcessingConsumer implements BiConsumer<ResourceLocation, ArmorModelLayerDefinition> {

        @Nonnull
        private final ResourceLocation modelLocation;

        @Nonnull
        private final ImmutableMap.Builder<IMultiComponentArmorExtension, ArmorSubComponentModel> modelMap;

        @Nonnull
        private final ImmutableSet.Builder<ResourceLocation> builder;

        public ModelMappingProcessingConsumer(@Nonnull ResourceLocation modelLocation, @Nonnull ImmutableMap.Builder<IMultiComponentArmorExtension, ArmorSubComponentModel> modeMap, @Nonnull ImmutableSet.Builder<ResourceLocation> builder) {
            this.modelLocation = modelLocation;
            this.modelMap = modeMap;
            this.builder = builder;
        }

        /**
         * Performs this operation on the given arguments.
         *
         * @param extensionName  the first input argument
         * @param layerDefinition the second input argument
         */
        @Override
        public void accept(@Nonnull ResourceLocation extensionName, @Nonnull ArmorModelLayerDefinition layerDefinition) {
            IMultiComponentArmorExtension extension = ArmoryAPI.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(extensionName);
            if (extension == null) {
                ModLogger.getInstance().warn(String.format("Attempted to load model: {0}, with a layer: {1}, that has no registered Extension!", modelLocation, extensionName));
                return;
            }

            ArmorSubComponentModel componentModel;
            if (extension instanceof IMaterializableMultiComponentArmorExtension) {
                componentModel = new ArmorAddonComponentModel(layerDefinition);
            } else {
                componentModel = new ArmorSubComponentModel(layerDefinition);
            }

            modelMap.put(extension, componentModel);
            builder.addAll(layerDefinition.getTextures());
        }
    }

    public MultiLayeredArmorModelDefinition processDefinition (IMultiComponentArmor armor, ResourceLocation modelLocation) throws IOException
    {
        //Load the default definition of the model as defined by the registrar first.
        MultiLayeredArmorModelDefinition internalDefinition = MultiLayeredArmorModelDeserializer.instance.deserialize(modelLocation);

        //Fire the TextureLoadEvent to allow third parties to add additional layers to the model if necessary
        MultiLayeredArmorModelTextureLoadEvent textureLoadEvent = new MultiLayeredArmorModelTextureLoadEvent(armor);
        textureLoadEvent.PostClient();

        ImmutableMap.Builder<ResourceLocation, ArmorModelPartDefinition> partDefinitionBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<ItemCameraTransforms.TransformType, TRSRTransformation> transformationBuilder = ImmutableMap.builder();

        partDefinitionBuilder.putAll(internalDefinition.getParts());
        transformationBuilder.putAll(internalDefinition.getTransforms());

        textureLoadEvent.getAdditionalTextureLayers().forEach(externalDefinition -> partDefinitionBuilder.putAll(externalDefinition.getParts()));
        textureLoadEvent.getAdditionalTextureLayers().forEach(externalDefinition -> transformationBuilder.putAll(externalDefinition.getTransforms()));

        return new MultiLayeredArmorModelDefinition(partDefinitionBuilder.build(), transformationBuilder.build());
    }

}
