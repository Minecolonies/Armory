package com.smithsmodding.armory.client.model.item.unbaked;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.api.common.armor.IMaterialDependantMultiComponentArmorExtension;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtension;
import com.smithsmodding.armory.api.util.references.ModLogger;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorPartItemModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorCoreComponentModel;
import com.smithsmodding.armory.client.model.item.unbaked.components.ArmorSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
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
import java.util.HashMap;

/**
 * A unbaked model for a part of the Armor.
 *
 * Holds a the base layer model, and collections of the broken and whole layers of addons.
 */
@SideOnly(Side.CLIENT)
public class MultiLayerArmorPartModel extends ItemLayerModel {

    private final IMultiComponentArmor armor;
    private final ModelPart part;
    private final ArmorCoreComponentModel baseLayer;
    private final ImmutableMap<IMultiComponentArmorExtension, ArmorSubComponentModel> parts;
    private final ImmutableMap<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenParts;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
    private final TRSRTransformation partTransformation;

    public MultiLayerArmorPartModel(
                                     IMultiComponentArmor armor,
                                     ImmutableSet<ResourceLocation> defaultTextures,
                                     final ModelPart part,
                                     ArmorCoreComponentModel baseLayer,
                                     ImmutableMap<IMultiComponentArmorExtension, ArmorSubComponentModel> parts,
                                     ImmutableMap<IMultiComponentArmorExtension, ArmorSubComponentModel> brokenPartBlocks,
                                     ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms,
                                     final TRSRTransformation partTransformation) {
        super(ImmutableList.copyOf(defaultTextures));
        this.armor = armor;
        this.part = part;
        this.baseLayer = baseLayer;
        this.parts = parts;
        this.brokenParts = brokenPartBlocks;
        this.transforms = transforms;
        this.partTransformation = partTransformation;
    }

    @Nonnull
    @Override
    public BakedMultiLayeredArmorPartItemModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBakedItemModel(state, format, bakedTextureGetter, partTransformation);
    }

    /**
     * Method used to "bake" a ItemModel without the transformation defined in its JSON.
     * This method is used to create model that holds true to its origin allowing it to be used as a 3D on entity model.
     *
     * @param state The state of the model to bake.
     * @param format The format to bake the model into.
     * @param bakedTextureGetter The getter for the Textures.
     * @return A baked model that has the transformation not applied.
     */
    public BakedMultiLayeredArmorPartItemModel generateUnoffsetedBakedItemModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter)
    {
        return generateBakedItemModel(state, format, bakedTextureGetter, TRSRTransformation.identity());
    }

    /**
     * Method used to "bake" a ItemModel with a specific transformation.
     *
     * @param state The state of the model to bake.
     * @param format The format to bake the model into.
     * @param bakedTextureGetter The getter for the Textures.
     * @param internalTransform The transformation to apply.
     *
     * @return A baked model that has the transformation applied.
     */
    public BakedMultiLayeredArmorPartItemModel generateBakedItemModel(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter, TRSRTransformation internalTransform) {
        //Get ourselfs the base model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        //Setup the maps that contain the converted baked sub models.
        BakedSubComponentModel mappedBaseLayer;
        HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> mappedParts = new HashMap<>();
        HashMap<IMultiComponentArmorExtension, BakedSubComponentModel> mappedBrokenParts = new HashMap<>();

        //Map the baselayer:
        mappedBaseLayer = baseLayer.generateBackedComponentModel(state, format, bakedTextureGetter, internalTransform);

        //Check every possible addon for a texture and register it accordingly
        for (IMultiComponentArmorExtension extension : armor.getPossibleExtensions()) {
            if (extension instanceof IMaterialDependantMultiComponentArmorExtension)
                extension = ((IMaterialDependantMultiComponentArmorExtension) extension).getMaterialIndependentExtension();

            if (parts.containsKey(extension)) {
                mappedParts.put(extension, parts.get(extension).generateBackedComponentModel(state, format, bakedTextureGetter));

                //If a part was found, also check for its broken counterpart.
                if (brokenParts.containsKey(extension)) {
                    mappedBrokenParts.put(extension, parts.get(extension).generateBackedComponentModel(state, format, bakedTextureGetter));
                }
            } else {
                //For a given MLAAddon on the armor was no texture found.
                ModLogger.getInstance().error("A given armor: " + armor.getRegistryName().toString() + " has a MLAAddon: " + extension.getRegistryName().toString() + " that has no texture registered in the model. It is being skipped.");
            }
        }

        //Bake the model.
        return new BakedMultiLayeredArmorPartItemModel(base, mappedBaseLayer, mappedParts, mappedBrokenParts, part, transforms);
    }
}
