package com.ldtteam.armory.api.util.client;

import com.google.common.collect.ImmutableList;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.smithscore.client.model.unbaked.ItemLayerModel;
import com.ldtteam.smithscore.util.client.ResourceHelper;
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;

import javax.annotation.Nonnull;

public final class ModelMaterialHelper
{

    private ModelMaterialHelper()
    {
        throw new IllegalArgumentException("Utility Class");
    }

    /**
     * Method used to check if a given material has a override texture available.
     *
     * @param state       The model state to check into.
     * @param baseTexture The base texture to check for.
     * @param material    The material to check for.
     * @param original    The original model to retexture if need be.
     * @return Either a retextured variant of the original or the original model, if no override was found.
     */
    public static IBakedModel checkForMaterialOverride(
      final @Nonnull IModelState state,
      final @Nonnull ResourceLocation baseTexture,
      final @Nonnull IMaterial material,
      final @Nonnull IBakedModel original)
    {
        if (material.getRenderInfo() == null)
        {
            return original;
        }

        if (material.getRenderInfo().useVertexColoring() && !ResourceHelper.exists(baseTexture + "_" + material.getTextureOverrideIdentifier()))
        {
            //We get the color for the material.
            MinecraftColor color = (material.getRenderInfo()).getVertexColor();

            //We create a new list of Quads.
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            //We color all the quads in the GeneralQuads (As the ItemLayeredModel.BakedModel only uses the General Quads)
            for (BakedQuad quad : original.getQuads(null, null, 0))
            {
                quads.add(com.ldtteam.smithscore.util.client.ModelHelper.colorQuad(color, quad));
            }

            return new ItemLayerModel.BakedItemModel(quads.build(), original.getParticleTexture(), PerspectiveMapWrapper.getTransforms(state), original.getOverrides(), null);
        }
        return original;
    }
}