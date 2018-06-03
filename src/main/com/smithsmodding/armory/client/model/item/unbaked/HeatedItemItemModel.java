package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.collect.ImmutableList;
import com.smithsmodding.armory.client.model.item.baked.components.BakedTemperatureBarModel;
import com.smithsmodding.armory.client.model.item.baked.heateditem.BakedHeatedItemModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.TemperatureBarComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.util.Collection;
import java.util.Optional;
import java.util.function.Function;

/**
 * Created by Marc on 08.12.2015.
 */
public class HeatedItemItemModel extends ItemLayerModel {

    //Determined during runtime. See git history on how to retrieve from block.
    private static final TRSRTransformation CONST_BLOCK_INVERSION_TRANSFORM = new TRSRTransformation(new Matrix4f(new float[]{
      -1.1313709f, -0.5656854f, 0.97979575f, 2.14f,
      -7.152556E-8f, 1.3856407f, 0.79999983f, 0.44f,
      -1.1313705f, 0.5656854f, -0.97979605f, 0.0f,
      0.0f, 0.0f, 0.0f, 1.0f
    }));
    
    TemperatureBarComponentModel gaugeDisplay;

    public HeatedItemItemModel(ImmutableList<ResourceLocation> defaultTextures) {
        super(defaultTextures);

        this.gaugeDisplay = new TemperatureBarComponentModel(defaultTextures);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of();
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        BakedTemperatureBarModel unrotatedModel = gaugeDisplay.generateBackedComponentModel(state, format, bakedTextureGetter);
        BakedTemperatureBarModel rotatedModel =
          gaugeDisplay.generateBackedComponentModel(state.apply(Optional.empty()).get().compose(CONST_BLOCK_INVERSION_TRANSFORM), format, bakedTextureGetter);

        //Bake the model.
        return new BakedHeatedItemModel(base, unrotatedModel, rotatedModel);
    }
}
