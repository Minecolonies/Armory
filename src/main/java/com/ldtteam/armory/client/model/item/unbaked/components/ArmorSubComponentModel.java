package com.ldtteam.armory.client.model.item.unbaked.components;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.ldtteam.armory.api.client.model.deserializers.definition.ArmorModelLayerPartDefinition;
import com.ldtteam.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.ldtteam.smithscore.client.model.unbaked.ItemLayerModel;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.function.Function;

/**
 * A class describing a layer of a Model in its most basic form.
 */
public class ArmorSubComponentModel extends ItemLayerModel implements IModel {
    @Nullable protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    @Nullable private final             ArmorModelLayerDefinition                                            layerDefinition;

    /**
     * Constructor used to create a new Layer if the armor model.
     * @param layerDefinition The definition of the layer to be constructed.
     */
    public ArmorSubComponentModel(@Nonnull ArmorModelLayerDefinition layerDefinition) {
        this(layerDefinition, ModelHelper.DEFAULT_ITEM_TRANSFORMS);
    }

    /**
     * Constructor used to create a new Layer of the armor model, with given transformations,
     * @param layerDefinition THe definition of the layer to be constructed.
     * @param transforms The transformations that need to be applied in a specific scenario.
     */
    ArmorSubComponentModel(@Nonnull ArmorModelLayerDefinition layerDefinition, @Nonnull ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(layerDefinition.getTextures());
        this.transforms = transforms;
        this.layerDefinition = layerDefinition;
    }

    /**
     * Private constructor used only during the baking of the layer parts (as they are treated as a layer each).
     * @param textureLocation The texture of the part that is about to be baked.
     */
    ArmorSubComponentModel(@Nonnull ResourceLocation textureLocation) {
        super(ImmutableList.of(textureLocation));
        transforms = null;
        layerDefinition = null;
    }

    /**
     * Function get the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to get the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Nonnull
    @Override
    public IBakedModel bake(@Nonnull final IModelState state,@Nonnull final  VertexFormat format,@Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to get the grayscale texture location of this model faster.
     *
     * @return The location of the grayscale texture.
     */
    @Nullable
    public ResourceLocation getTexture() {
        ArrayList<ResourceLocation> textures = new ArrayList<>();
        textures.addAll(getTextures());

        if (textures.size() == 0)
            return null;

        return textures.get(0);
    }

    /**
     * Method to get the actual definition of the model.
     * @return The actual definition of the model.
     */
    @Nullable
    private ArmorModelLayerDefinition getLayerDefinition()
    {
        return layerDefinition;
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to get the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    @Nonnull
    public BakedSubComponentModel generateBackedComponentModel(@Nonnull IModelState state, VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return this.generateBackedComponentModel(state, format, bakedTextureGetter, TRSRTransformation.identity());
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to get the baked textures.
     * @param internalTransformation The transformation applied before parts (if they exist) are being baked.
     * @return A baked model containing all individual possible textures this model can have.
     */
    @Nonnull
    public BakedSubComponentModel generateBackedComponentModel(@Nonnull IModelState state, VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, TRSRTransformation internalTransformation) {
        return new BakedSubComponentModel(super.bake(state, format, bakedTextureGetter), transforms);
    }


    /**
     * Method to get a model used in the baking process with a single texture.
     * Allows child classes to change baking process.
     * @param texture The texture to get a model for.
     * @return A component model that can be used in the baking process.
     */
    ArmorSubComponentModel getBakingModelForTexture(@Nonnull final ResourceLocation texture) {
        return new ArmorSubComponentModel(texture);
    }

    /**
     * Method to get a set of baked part models.
     * @param format The format to bake into.
     * @param bakedTextureGetter A function to get a texture from a given resource location.
     * @return A set of component models that hold all the parts of this layer.
     */
    @Nonnull
    ImmutableSet<BakedSubComponentModel> getBakedPartModels(@Nonnull final VertexFormat format, @Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return this.getBakedPartModels(format, bakedTextureGetter, TRSRTransformation.identity());
    }

    /**
     * Method to get a set of baked part models.
     * @param format The format to bake into.
     * @param bakedTextureGetter A function to get a texture from a given resource location.
     * @param initialTransformation The transformation applied to parts (if this model has any) before their own.
     * @return A set of component models that hold all the parts of this layer.
     */
    @Nonnull
    ImmutableSet<BakedSubComponentModel> getBakedPartModels(@Nonnull final VertexFormat format, @Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, TRSRTransformation initialTransformation) {
        if (getLayerDefinition() == null)
        {
            return ImmutableSet.of();
        }

        @Nonnull ImmutableSet.Builder<BakedSubComponentModel> builder = ImmutableSet.builder();

        for(ArmorModelLayerPartDefinition part : getLayerDefinition().getParts())
        {
            builder.add(getBakingModelForTexture(part.getTextureLocation()).generateBackedComponentModel(TRSRTransformation.blockCenterToCorner(initialTransformation.compose(part.getTransformation())), format, bakedTextureGetter));
        }

        return builder.build();
    }

    /**
     * Method used during baking to construct the base model to be used.
     * @param state The state of the model to bake.
     * @param format The format of the model to bake into.
     * @param bakedTextureGetter A Function to get a texture from a resource location.
     * @param partModels The parts of the model, or an empty list if it has none.
     * @return The base model used during bake.
     */
    @Nonnull
    protected IBakedModel getBaseBakedModel(
                                             final @Nonnull IModelState state,
                                             final VertexFormat format,
                                             final @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter,
                                             final ImmutableSet<BakedSubComponentModel> partModels)
    {
        final IBakedModel base;
        if (!partModels.isEmpty())
        {
            ImmutableList.Builder<BakedQuad> quadBuilder = ImmutableList.builder();

            partModels.forEach((BakedSubComponentModel model) -> quadBuilder.addAll(model.getQuads(null, null, 0)));

            // Get ourselfs a normal model to use.
            base = new BakedItemModel(quadBuilder.build(), partModels.iterator().next().getParticleTexture(), transforms, partModels.iterator().next().getOverrides(), null);
        }
        else
        {
            base = super.bake(state, format, bakedTextureGetter);
        }
        return base;
    }
}
