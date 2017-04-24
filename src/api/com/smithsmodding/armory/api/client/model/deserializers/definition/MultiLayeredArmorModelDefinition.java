package com.smithsmodding.armory.api.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.common.registries.IRegistryManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Definition of a Armor model.
 */
public class MultiLayeredArmorModelDefinition {

    @Nullable private final ArmorModelLayerDefinition                                   baseLayer;
    @Nonnull private final Map<ResourceLocation, ArmorModelLayerDefinition>            layerDefinition;
    @Nonnull private final Map<ResourceLocation, ArmorModelLayerDefinition>            brokenDefinition;
    @Nonnull private final Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    /**
     * Constructor for a new armor model definition.
     * @param baseLayer The {@link ArmorModelLayerDefinition} used as base layer.
     * @param layerDefinition A Map containing the {@link ArmorModelLayerDefinition} for each addon with their key.
     * @param brokenLocations A Map containing the {@link ArmorModelLayerDefinition} for each addon with their key, used when they are broken.
     * @param transforms The model transformers applied to the global model.
     * @throws IllegalArgumentException when a attempt is made to construct a definition without supplying any data.
     */
    public MultiLayeredArmorModelDefinition(@Nullable final ArmorModelLayerDefinition baseLayer, @Nonnull final Map<ResourceLocation, ArmorModelLayerDefinition> layerDefinition, @Nonnull final Map<ResourceLocation, ArmorModelLayerDefinition> brokenLocations, @Nonnull final Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) throws IllegalArgumentException {
        this.baseLayer = baseLayer;
        this.layerDefinition = layerDefinition;
        this.brokenDefinition = brokenLocations;
        this.transforms = transforms;

        if (layerDefinition.isEmpty() && brokenDefinition.isEmpty() && baseLayer == null && transforms.isEmpty())
            throw new IllegalArgumentException("Cannot create a new armor model definition if no layers, broken parts, base layer, or transformers are provided.");
    }

    /**
     * The base {@link ArmorModelLayerDefinition} of the Armor.
     * Always rendered, even when broken.
     * @return The base {@link ArmorModelLayerDefinition} of the armor.
     */
    @Nullable
    public ArmorModelLayerDefinition getBaseLayer() {
        return baseLayer;
    }

    /**
     * The {@link ArmorModelLayerDefinition} for addons if they are applied.
     * As key there name in the {@link IRegistryManager#getMultiComponentArmorExtensionRegistry()}.
     * Used when the armor is not broken or no broken definition is found for the given key.
     * @return The {@link ArmorModelLayerDefinition} for addons if they are applied.
     */
    @Nonnull
    public ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> getLayerDefinition() {
        return ImmutableMap.copyOf(layerDefinition);
    }

    /**
     * The {@link ArmorModelLayerDefinition} for addons if they are applied.
     * As key there name in the {@link IRegistryManager#getMultiComponentArmorExtensionRegistry()}.
     * Used when the armor is broken.
     * @return The {@link ArmorModelLayerDefinition} for addons if they are applied.
     */
    @Nonnull
    public ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> getBrokenDefinition() {
        return ImmutableMap.copyOf(brokenDefinition);
    }

    /**
     * The global render transforms for the model.
     * @return The {@link TRSRTransformation} for the model.
     */
    @Nonnull
    public Map<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
