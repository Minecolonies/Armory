package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorPartItemModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;

/**
 * ------------ Class not Documented ------------
 */
public class MultiLayerArmorModel extends ItemLayerModel
{

    private final IMultiComponentArmor                                                 armor;
    private final ImmutableMap<ResourceLocation, MultiLayerArmorPartModel> parts;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiLayerArmorModel(IMultiComponentArmor armor, ImmutableSet<ResourceLocation> defaultTextures, ImmutableMap<ResourceLocation, MultiLayerArmorPartModel> parts, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.copyOf(defaultTextures));
        this.armor = armor;
        this.parts = parts;
        this.transforms = transforms;
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        //Setup the maps that contain the converted baked sub models.
        ImmutableMap.Builder<ResourceLocation, BakedMultiLayeredArmorPartItemModel> modelBuilder = ImmutableMap.builder();

        parts.forEach((ResourceLocation key, MultiLayerArmorPartModel part) -> {
            modelBuilder.put(key, part.bake(state, format, bakedTextureGetter));
        });

        //Bake the model.
        return new BakedMultiLayeredArmorItemModel(base, modelBuilder.build(), transforms);
    }
}
