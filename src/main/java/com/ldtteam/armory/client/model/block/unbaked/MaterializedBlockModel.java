package com.ldtteam.armory.client.model.block.unbaked;

import com.google.common.collect.ImmutableMap;
import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.common.material.core.RegistryMaterialWrapper;
import com.ldtteam.armory.api.util.client.ModelMaterialHelper;
import com.ldtteam.armory.client.model.block.baked.MaterializedBlockBakedModel;
import com.ldtteam.armory.client.textures.MaterializedTextureCreator;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedBlockModel implements IModel {

    private final IModel                                                               parent;
    private final ResourceLocation                                                     materializableTexture;
    private final Map<ResourceLocation, ResourceLocation>                              materialOverrides;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations;

    public MaterializedBlockModel(
      IModel parent,
      ResourceLocation materializableTexture,
      Map<ResourceLocation, ResourceLocation> materialOverrides,
      ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations)
    {
        this.parent = parent;
        this.materializableTexture = materializableTexture;
        this.materialOverrides = materialOverrides;
        this.transformations = transformations;
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return Collections.singleton(materializableTexture);
    }

    @Override
    public IBakedModel bake(
      final IModelState state, final VertexFormat format, final Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        ImmutableMap.Builder<IMaterial, IBakedModel> subModelBuilder = new ImmutableMap.Builder<>();

        Map<String, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(materializableTexture);

        for(RegistryMaterialWrapper materialWrapper : IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry()) {
            if (!sprites.containsKey(materialWrapper.getWrapped().getOreDictionaryIdentifier()) && !materialOverrides.containsKey(materialWrapper.getRegistryName())) {
                continue;
            }

            TextureAtlasSprite retexturedSprite;
            if (!materialOverrides.containsKey(materialWrapper.getRegistryName())) {
                retexturedSprite = sprites.get(materialWrapper.getWrapped().getOreDictionaryIdentifier());
            } else {
                retexturedSprite = bakedTextureGetter.apply(materialOverrides.get(materialWrapper.getRegistryName()));
            }

            if (retexturedSprite == null)
                retexturedSprite = bakedTextureGetter.apply(TextureMap.LOCATION_MISSING_TEXTURE);

            subModelBuilder.put(materialWrapper.getWrapped(), parent.retexture(getRetextureMap(retexturedSprite.getIconName())).bake(state, format, bakedTextureGetter));
        }

        final Map<IMaterial, IBakedModel> materialBakedModels = subModelBuilder.build();
        final Map<IMaterial, IBakedModel> materialReprocessedModels = materialBakedModels.entrySet().stream().collect(Collectors.toMap(
          Map.Entry::getKey,
          entry -> ModelMaterialHelper.checkForMaterialOverride(state, materializableTexture, entry.getKey(), transformations, entry.getValue())
        ));


        return new MaterializedBlockBakedModel(parent.retexture(getRetextureMap(bakedTextureGetter.apply(materializableTexture).getIconName()))
                                                 .bake(state, format, bakedTextureGetter), transformations, materialReprocessedModels);
    }

    private ImmutableMap<String, String> getRetextureMap(String newTexture) {
        ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<>();

        builder.put("all", newTexture);
        builder.put("up", newTexture);
        builder.put("down", newTexture);
        builder.put("north", newTexture);
        builder.put("south", newTexture);
        builder.put("west", newTexture);
        builder.put("east", newTexture);

        return builder.build();
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_BLOCK_STATE;
    }
}
