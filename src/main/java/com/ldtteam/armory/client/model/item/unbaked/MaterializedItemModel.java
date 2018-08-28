package com.ldtteam.armory.client.model.item.unbaked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.client.ModelMaterialHelper;
import com.ldtteam.armory.client.model.item.baked.BakedMaterializedModel;
import com.ldtteam.armory.client.textures.MaterializedTextureCreator;
import com.ldtteam.armory.common.api.ArmoryAPI;
import com.ldtteam.smithscore.client.model.unbaked.ItemLayerModel;
import com.ldtteam.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModel extends ItemLayerModel {

    @Nonnull
    private final ResourceLocation coreTexture;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MaterializedItemModel(@Nonnull ResourceLocation coreTexture, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(ImmutableList.of(coreTexture));
        this.coreTexture = coreTexture;
        this.transforms = transforms;
    }

    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        IBakedModel parent = super.bake(state, format, bakedTextureGetter);

        ImmutableMap.Builder<IMaterial, IBakedModel> modelBuilder = new ImmutableMap.Builder<>();

        Map<String, TextureAtlasSprite> materializedTextures = MaterializedTextureCreator.getBuildSprites().get(new ResourceLocation(parent.getParticleTexture().getIconName()));

        for (ICoreArmorMaterial material : ArmoryAPI.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getOreDictionaryIdentifier()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        for (IAddonArmorMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAddonArmorMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getOreDictionaryIdentifier()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        for (IAnvilMaterial material : ArmoryAPI.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
            modelBuilder.put(material, this.retexture(ImmutableMap.of("layer0", materializedTextures.get(material.getOreDictionaryIdentifier()).getIconName())).bake(state, format, bakedTextureGetter));
        }

        final Map<IMaterial, IBakedModel> materialBakedModels = modelBuilder.build();
        final Map<IMaterial, IBakedModel> materialReprocessedModels = materialBakedModels.entrySet().stream().collect(Collectors.toMap(
          Map.Entry::getKey,
          entry -> ModelMaterialHelper.checkForMaterialOverride(state, coreTexture, entry.getKey(), entry.getValue())
        ));

        return new BakedMaterializedModel(parent, ImmutableMap.copyOf(materialReprocessedModels), transforms);
    }

    @Override
    public IModelState getDefaultState() {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
