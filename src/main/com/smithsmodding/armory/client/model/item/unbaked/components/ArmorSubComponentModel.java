package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerDefinition;
import com.smithsmodding.armory.api.client.model.deserializers.definition.ArmorModelLayerPartDefinition;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;

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
     * Function getCreationRecipe the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to getCreationRecipe the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Nonnull
    @Override
    public IBakedModel bake(@Nonnull final IModelState state,@Nonnull final  VertexFormat format,@Nonnull final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to getCreationRecipe the grayscale texture location of this model faster.
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
     * Function to getCreationRecipe a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to getCreationRecipe the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    @Nonnull
    public BakedSubComponentModel generateBackedComponentModel(@Nonnull IModelState state, VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
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
        if (getLayerDefinition() == null)
        {
            return ImmutableSet.of();
        }

        @Nonnull ImmutableSet.Builder<BakedSubComponentModel> builder = ImmutableSet.builder();

        for(ArmorModelLayerPartDefinition part : getLayerDefinition().getParts())
        {
            builder.add(getBakingModelForTexture(part.getTextureLocation()).generateBackedComponentModel(TRSRTransformation.blockCenterToCorner(part.getTransformation()), format, bakedTextureGetter));
        }

        return builder.build();
    }

    /**
     * Method used to check if a given material has a override texture available.
     * @param state The model state to check into.
     * @param baseTexture The base texture to check for.
     * @param material The material to check for.
     * @param original The original model to retexture if need be.
     * @return Either a retextured variant of the original or the original model, if no override was found.
     */
    protected IBakedModel checkForMaterialOverride(
                                                    final @Nonnull IModelState state,
                                                    final @Nonnull ResourceLocation baseTexture,
                                                    final @Nonnull IMaterial material,
                                                    final @Nonnull IBakedModel original)
    {
        if (material.getRenderInfo() == null)
        {
            return original;
        }

        if (material.getRenderInfo().useVertexColoring() && !ResourceHelper.exists(baseTexture + "_" + material.getTextureOverrideIdentifier())) {
            //We getCreationRecipe the color for the material.
            MinecraftColor color = (material.getRenderInfo()).getVertexColor();

            //We create a new list of Quads.
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            //We color all the quads in the GeneralQuads (As the ItemLayeredModel.BakedModel only uses the General Quads)
            for (BakedQuad quad : original.getQuads(null, null, 0)) {
                quads.add(ModelHelper.colorQuad(color, quad));
            }

            return new BakedItemModel(quads.build(), original.getParticleTexture(), IPerspectiveAwareModel.MapWrapper.getTransforms(state), original.getOverrides(), null);
        }
        return original;
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
