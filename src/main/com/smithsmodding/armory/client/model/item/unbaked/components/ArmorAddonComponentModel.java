package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.client.model.item.baked.components.BakedAddonComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Function;

/**
 * A class used to represent an unbaked component model.
 */
@SideOnly(Side.CLIENT)
public class ArmorAddonComponentModel extends ArmorSubComponentModel {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked model.
     */
    public ArmorAddonComponentModel(@Nonnull ArmorModelLayerDefinition textures) {
        super(textures, ModelHelper.DEFAULT_ITEM_TRANSFORMS);
    }

    public ArmorAddonComponentModel(final ResourceLocation texture)
    {
        super(texture);
    }

    @Override
    ArmorSubComponentModel getBakingModelForTexture(@Nonnull final ResourceLocation texture)
    {
        return new ArmorAddonComponentModel(texture);
    }

    @Nonnull
    @Override
    public BakedSubComponentModel generateBackedComponentModel(
                                                                @Nonnull final IModelState state,
                                                                final VertexFormat format,
                                                                @Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
                                                                final TRSRTransformation internalTransformation)
    {
        ImmutableSet<BakedSubComponentModel> partModels = getBakedPartModels(format, bakedTextureGetter, internalTransformation);
        IBakedModel base = getBaseBakedModel(state, format, bakedTextureGetter, partModels);

        // Use it as our base for the BakedComponentModel.
        BakedAddonComponentModel bakedMaterialModel = new BakedAddonComponentModel(base);

        if (!partModels.isEmpty())
        {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(material -> {
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
            if (!baseTexture.equals(new ResourceLocation("minecraft:missingno")))
            {
                Map<String, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(baseTexture);

                //Construct individual models for each of the sprites.
                for (Map.Entry<String, TextureAtlasSprite> entry : sprites.entrySet())
                {
                    //We grab the material now, that way we know the material exists before continuing.
                    IAddonArmorMaterial material = IArmoryAPI.Holder.getInstance().getHelpers()
                                                     .getRegistryHelpers()
                                                     .findAddonMaterialUsingPredicate(c -> c.getOreDictionaryIdentifier().equalsIgnoreCase(entry.getKey()))
                                                     .orElse(null);

                    if (material == null)
                    {
                        continue;
                    }

                    //We retexture this model with the newly colored textured from ther creator and get a Copy of this model
                    //But then colored instead of grayscaled.
                    IModel model2 = this.retexture(ImmutableMap.of("layer0", entry.getValue().getIconName()));

                    //We bake the new model to get a ready to use textured and ready to be colored baked model.
                    IBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

                    // We check if a special texture for that item exists in our textures collection.
                    // If not we check if the material needs coloring and color the vertexes individually.
                    bakedModel2 = checkForMaterialOverride(state, baseTexture, material, bakedModel2);

                    bakedMaterialModel.addMaterialModel(material, bakedModel2);
                }
            }
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedMaterialModel;
    }
}
