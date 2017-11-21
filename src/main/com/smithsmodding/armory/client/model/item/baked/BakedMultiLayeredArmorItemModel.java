package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.util.common.armor.ArmorNBTHelper;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import com.smithsmodding.smithscore.client.model.unbaked.DummyModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * ------------ Class not Documented ------------
 */
@SideOnly(Side.CLIENT)
public class BakedMultiLayeredArmorItemModel extends BakedWrappedModel.PerspectiveAware {

    @Nonnull
    private static final List<List<BakedQuad>> empty_face_quads;
    @Nonnull
    private static final List<BakedQuad>       empty_list;

    static {
        empty_list = Collections.emptyList();
        empty_face_quads = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            empty_face_quads.add(empty_list);
        }
    }

    @Nonnull
    protected final BakedMultiLayeredArmorItemModel.Overrides                        overrides;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected final ImmutableMap<ResourceLocation, BakedMultiLayeredArmorPartItemModel> parts;
    protected final ImmutableMap<ResourceLocation, BakedMultiLayeredArmorPartItemModel> untranslatedParts;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry in
     * the array simply is null.
     */
    public BakedMultiLayeredArmorItemModel(
                                            IBakedModel parent,
                                            ImmutableMap<ResourceLocation, BakedMultiLayeredArmorPartItemModel> parts,
                                            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                            final ImmutableMap<ResourceLocation, BakedMultiLayeredArmorPartItemModel> untranslatedParts) {
        super(parent, transforms);

        this.parts = parts;
        this.untranslatedParts = untranslatedParts;
        overrides = new BakedMultiLayeredArmorItemModel.Overrides(this);
        this.transforms = transforms;
    }

    @Nullable
    public IBakedModel getUntranslatedModel(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity, ModelPart modelPart) {
        IMultiComponentArmorCapability capability = ArmorNBTHelper.getArmorDataFromStack(stack);
        if (capability == null)
            return DummyModel.BAKED_MODEL;

        // get the texture for each part
        ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

        for(BakedMultiLayeredArmorPartItemModel part : untranslatedParts.values())
        {
            if (modelPart == part.getModelPart())
            {
                quads.addAll(part.getOverrides().handleItemState(originalModel, stack, world, entity).getQuads(null, null, 1));
            }
        }

        IBakedModel model = new ItemLayerModel.BakedItemModel(quads.build(), getParticleTexture(), transforms, getOverrides(), null);

        return model;
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static final class Overrides extends ItemOverrideList {

        private final BakedMultiLayeredArmorItemModel parent;

        public Overrides(BakedMultiLayeredArmorItemModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Nullable
        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            IMultiComponentArmorCapability capability = ArmorNBTHelper.getArmorDataFromStack(stack);
            if (capability == null)
                return DummyModel.BAKED_MODEL;

            // get the texture for each part
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            for(BakedMultiLayeredArmorPartItemModel part : parent.parts.values())
            {
                quads.addAll(part.getOverrides().handleItemState(originalModel, stack, world, entity).getQuads(null, null, 1));
            }

            IBakedModel model = new ItemLayerModel.BakedItemModel(quads.build(), parent.getParticleTexture(), parent.transforms, parent.getOverrides(), null);

            return model;
        }
    }
}