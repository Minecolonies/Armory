package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.client.model.item.baked.components.BakedCoreComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.client.textures.creators.CoreTextureCreator;
import com.smithsmodding.armory.common.api.ArmoryAPI;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p>
 * model used to display singular components of the armor.
 * Is in implementation nearly the same as the TinkersConstruct Toolparts.
 */
public class ArmorCoreComponentModel extends ArmorSubComponentModel {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param definition The definition for a given layer.
     */
    public ArmorCoreComponentModel(@Nonnull ArmorModelLayerDefinition definition) {
        super(definition, ModelHelper.DEFAULT_ITEM_TRANSFORMS);
    }

    private ArmorCoreComponentModel(@Nonnull final ResourceLocation textureLocation) {
        super(textureLocation);
    }

    /**
     * Function getCreationRecipe the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to getCreationRecipe the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Nonnull
    @Override
    public IBakedModel bake(@Nonnull final IModelState state, @Nonnull final VertexFormat format, @Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Method to get a model used in the baking process with a single texture.
     * Allows child classes to change baking process.
     *
     * @param texture The texture to get a model for.
     * @return A component model that can be used in the baking process.
     */
    @Override
    public ArmorCoreComponentModel getBakingModelForTexture(@Nonnull final ResourceLocation texture)
    {
        return new ArmorCoreComponentModel(texture);
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to getCreationRecipe the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    @Override
    @Nonnull
    public BakedCoreComponentModel generateBackedComponentModel(@Nonnull IModelState state, VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        ImmutableSet<BakedSubComponentModel> partModels = getBakedPartModels(format, bakedTextureGetter);
        IBakedModel base = getBaseBakedModel(state, format, bakedTextureGetter, partModels);

        // Use it as our base for the BakedComponentModel.
        BakedCoreComponentModel bakedMaterialModel = new BakedCoreComponentModel(base);

        if (!partModels.isEmpty())
        {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(material -> {
                ImmutableList.Builder<BakedQuad> quadBuilder = ImmutableList.builder();
                partModels.forEach((BakedSubComponentModel model) -> quadBuilder.addAll(model.getModelByIdentifier(material.getRegistryName()).getQuads(null, null, 0)));

                IBakedModel materializedLayer = new ItemLayerModel.BakedItemModel(quadBuilder.build(), partModels.iterator().next().getParticleTexture(), transforms, partModels.iterator().next().getOverrides(), null);

                bakedMaterialModel.addMaterialModel(material, materializedLayer);
            });
        }
        else
        {
            //In between the loading of the model from the JSON and the baking the MaterializedTextureCreator was able to
            // generate all the necessary textures for the models.
            //We retrieve those now and register them to the BakedModel later.
            ResourceLocation baseTexture = new ResourceLocation(base.getParticleTexture().getIconName());
            Map<ResourceLocation, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(baseTexture);

            //Construct individual models for each of the sprites.
            for (Map.Entry<ResourceLocation, TextureAtlasSprite> entry : sprites.entrySet()) {
                //Check if the sprite name contains the CoreIdentifier. Skip else.
                if (!entry.getValue().getIconName().contains(CoreTextureCreator.CORETEXTUREIDENTIFIER))
                    continue;

                //We grab the material now, that way we know the material exists before continuing.
                ICoreArmorMaterial material = ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry().getValue(entry.getKey());

                //We retexture this model with the newly colored textured from ther creator and getCreationRecipe a Copy of this model
                //But then colored instead of grayscaled.
                IModel model2 = this.retexture(ImmutableMap.of("layer0", entry.getValue().getIconName()));

                //We bake the new model to getCreationRecipe a ready to use textured and ready to be colored baked model.
                IBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

                // We check if a special texture for that item exists in our textures collection.
                // If not we check if the material needs coloring and color the vertexes individually.
                bakedModel2 = checkForMaterialOverride(state, baseTexture, material, bakedModel2);

                bakedMaterialModel.addMaterialModel(material, bakedModel2);
            }
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedMaterialModel;
    }
}
