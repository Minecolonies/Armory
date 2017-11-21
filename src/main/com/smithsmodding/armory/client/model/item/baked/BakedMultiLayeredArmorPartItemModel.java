package com.smithsmodding.armory.client.model.item.baked;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.util.common.armor.ArmorNBTHelper;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
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
import java.util.*;

/**
 * A baked model for a part of the armor.
 */
@SideOnly(Side.CLIENT)
public class BakedMultiLayeredArmorPartItemModel extends BakedWrappedModel.PerspectiveAware {

    @Nonnull
    private static final List<List<BakedQuad>> empty_face_quads;
    @Nonnull
    private static final List<BakedQuad> empty_list;

    static {
        empty_list = Collections.emptyList();
        empty_face_quads = Lists.newArrayList();
        for (int i = 0; i < 6; i++) {
            empty_face_quads.add(empty_list);
        }
    }

    @Nonnull
    protected final Overrides overrides;
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    protected BakedSubComponentModel baseLayer;
    protected HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> parts;
    protected HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> brokenParts;
    protected final ModelPart modelPart;

    /**
     * The length of brokenParts has to match the length of parts. If a part does not have a broken texture, the entry in
     * the array simply is null.
     */
    public BakedMultiLayeredArmorPartItemModel(IBakedModel parent, BakedSubComponentModel baseLayer, HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> parts, HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> brokenParts, ModelPart modelPart, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(parent, transforms);

        this.parts = parts;
        this.baseLayer = baseLayer;
        this.brokenParts = brokenParts;
        overrides = new Overrides(this);
        this.transforms = transforms;
        this.modelPart = modelPart;
    }

    public ModelPart getModelPart()
    {
        return modelPart;
    }

    @Nonnull
    @Override
    public ItemOverrideList getOverrides() {
        return overrides;
    }

    private static final class Overrides extends ItemOverrideList {

        private final BakedMultiLayeredArmorPartItemModel parent;

        public Overrides(BakedMultiLayeredArmorPartItemModel parent) {
            super(new ArrayList<>());
            this.parent = parent;
        }

        @Nullable
        @Override
        public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
            IMultiComponentArmorCapability capability = ArmorNBTHelper.getArmorDataFromStack(stack);
            if (capability == null)
                return DummyModel.BAKED_MODEL;

            ICoreArmorMaterial coreArmorMaterial = capability.getMaterial();

            ArrayList<IMultiComponentArmorExtensionInformation> installedExtensions = capability.getInstalledExtensions();

            //Sort the list based on priority.
            Collections.sort(installedExtensions, new Comparator<IMultiComponentArmorExtensionInformation>() {
                @Override
                public int compare(@Nonnull IMultiComponentArmorExtensionInformation o1, @Nonnull IMultiComponentArmorExtensionInformation o2) {
                    return Integer.compare(o1.getPosition().getArmorLayer(), o2.getPosition().getArmorLayer());
                }
            });

            // get the texture for each part
            ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

            boolean broken = ArmorNBTHelper.checkIfStackIsBroken(stack);

            quads.addAll(parent.baseLayer.getModelByIdentifier(coreArmorMaterial.getRegistryName()).getQuads(null, null, 0));

            for (IMultiComponentArmorExtensionInformation extensionInformation : installedExtensions) {
                IMultiComponentArmorExtension extension = extensionInformation.getExtension();

                IBakedModel partModel;
                ResourceLocation addonArmorMaterialName = null;
                if (extension instanceof IMaterialDependantMultiComponentArmorExtension) {
                    addonArmorMaterialName = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterial().getRegistryName();
                    extension = ((IMaterialDependantMultiComponentArmorExtension)extension).getMaterialIndependentExtension();
                }

                if (broken && parent.brokenParts.containsKey(extension) && parent.brokenParts.get(extension) != null) {
                    partModel = parent.brokenParts.get(extension).getModelByIdentifier(addonArmorMaterialName);
                } else if (parent.parts.containsKey(extension) && parent.parts.get(extension) != null) {
                    partModel = parent.parts.get(extension).getModelByIdentifier(addonArmorMaterialName);
                } else {
                    continue;
                }

                quads.addAll(partModel.getQuads(null, null, 0));
            }

            IBakedModel model = new ItemLayerModel.BakedItemModel(quads.build(), parent.getParticleTexture(), parent.transforms, parent.getOverrides(), null);

            return model;
        }
    }
}
