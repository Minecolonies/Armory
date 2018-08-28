package com.ldtteam.armory.api.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;

/**
 * Definition of a Armor model.
 */
public class MultiLayeredArmorModelDefinition {

    @Nonnull private final ImmutableMap<ResourceLocation, ArmorModelPartDefinition> parts;
    @Nonnull private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    /**
     * Constructor for a new armor model definition.
     * @param parts The parts of the model.
     * @param transforms The model transformers applied to the global model.  @throws IllegalArgumentException when a attempt is made to construct a definition without supplying any data.
     */
    public MultiLayeredArmorModelDefinition(
                                             @Nonnull final ImmutableMap<ResourceLocation, ArmorModelPartDefinition> parts,
                                             @Nonnull final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) throws IllegalArgumentException {
        this.parts = parts;
        this.transforms = transforms;

        if (this.parts.isEmpty())
            throw new IllegalArgumentException("Cannot create a new armor model definition if no layers, broken parts, base layer, or transformers are provided.");
    }

    @Nonnull
    public ImmutableMap<ResourceLocation, ArmorModelPartDefinition> getParts()
    {
        return parts;
    }

    /**
     * The global render transforms for the model.
     * @return The {@link TRSRTransformation} for the model.
     */
    @Nonnull
    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
