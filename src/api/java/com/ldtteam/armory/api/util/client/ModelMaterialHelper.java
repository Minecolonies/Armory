package com.ldtteam.armory.api.util.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.smithscore.client.model.unbaked.ItemLayerModel;
import com.ldtteam.smithscore.util.client.ResourceHelper;
import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.SimpleBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.PerspectiveMapWrapper;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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
      final @Nonnull ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
      final @Nonnull IBakedModel original
      )
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
            ImmutableMap.Builder<EnumFacing, List<BakedQuad>> facedQuadsBuilder = ImmutableMap.builder();
            ImmutableList.Builder<BakedQuad> generalQuadsBuilder = ImmutableList.builder();

            for(final EnumFacing facing : EnumFacing.values())
            {
                final List<BakedQuad> quads = Lists.newArrayList();

                for (BakedQuad quad : original.getQuads(null, facing, 0))
                {
                    quads.add(com.ldtteam.smithscore.util.client.ModelHelper.colorQuad(color, quad));
                }

                if (!quads.isEmpty())
                {
                    facedQuadsBuilder.put(facing, quads);
                }
            }

            for (BakedQuad quad : original.getQuads(null, null, 0))
            {
                generalQuadsBuilder.add(com.ldtteam.smithscore.util.client.ModelHelper.colorQuad(color, quad));
            }

            final Map<EnumFacing, List<BakedQuad>> facedQuads = facedQuadsBuilder.build();
            return new ItemLayerModel.BakedItemModel(generalQuadsBuilder.build(), original.getParticleTexture(), transforms, original.getOverrides(), null)
            {
                @Override
                public List<BakedQuad> getQuads(@Nullable final IBlockState state, @Nullable final EnumFacing side, final long rand)
                {
                    if (side != null && facedQuads.containsKey(side))
                    {
                        return facedQuads.get(side);
                    }

                    return super.getQuads(state, side, rand);
                }
            };
        }
        return original;
    }
}