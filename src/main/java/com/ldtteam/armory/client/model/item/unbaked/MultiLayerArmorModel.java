package com.ldtteam.armory.client.model.item.unbaked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.ldtteam.armory.client.model.item.baked.BakedMultiLayeredArmorPartItemModel;
import com.ldtteam.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * Class that represents an Unbaked model for the armor.
 */
@SideOnly(Side.CLIENT)
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
        ImmutableMap.Builder<ResourceLocation, BakedMultiLayeredArmorPartItemModel> translatedDefaultParts = ImmutableMap.builder();
        ImmutableMap.Builder<ResourceLocation, BakedMultiLayeredArmorPartItemModel> untranslatedParts = ImmutableMap.builder();

        parts.forEach((ResourceLocation key, MultiLayerArmorPartModel part) -> {
            translatedDefaultParts.put(key, part.bake(state, format, bakedTextureGetter));
            untranslatedParts.put(key, part.generateUnoffsetedBakedItemModel(state, format, bakedTextureGetter));
        });

        //Bake the model.
        return new BakedMultiLayeredArmorItemModel(base, translatedDefaultParts.build(), transforms, untranslatedParts.build());
    }
}
