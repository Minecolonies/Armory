package com.smithsmodding.armory.api.client.model.deserializers.definition;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import javax.annotation.Nonnull;

/**
 * Class describing the definition of a part of the Armor.
 * Combines a {@link ResourceLocation} for the parts texture together with the {@link TRSRTransformation}
 * that is needed to render it.
 */
public class ArmorModelPartDefinition
{
    @Nonnull private final ResourceLocation id;
    @Nonnull private final ResourceLocation   textureLocation;
    @Nonnull private final TRSRTransformation transformation;

    /**
     * Constructor for a new {@link ArmorModelPartDefinition}.
     * Combines the texture {@link ResourceLocation} together with its {@link TRSRTransformation}.
     * @param id
     * @param textureLocation The {@link ResourceLocation} of the texture of this part.
     * @param transformation The {@link TRSRTransformation} that should be applied when rendering the model.
     */
    public ArmorModelPartDefinition(@Nonnull final ResourceLocation id, @Nonnull final ResourceLocation textureLocation, @Nonnull final TRSRTransformation transformation) {
        this.id = id;
        this.textureLocation = textureLocation;
        this.transformation = transformation;
    }

    @Nonnull
    public ResourceLocation getId()
    {
        return id;
    }

    /**
     * Getter for the texture {@link ResourceLocation} of the part.
     * @return The texture {@link ResourceLocation} of the part.
     */
    @Nonnull
    public ResourceLocation getTextureLocation()
    {
        return textureLocation;
    }

    /**
     * Getter for the {@link TRSRTransformation} of the part.
     * The {@link TRSRTransformation} is applied from the center of the armor piece.
     * @return The {@link TRSRTransformation} of the part.
     */
    @Nonnull
    public TRSRTransformation getTransformation()
    {
        return transformation;
    }

    /**
     * Creates an override for the current part with a different texture.
     * @param newTextureLocation The new texture location for this part.
     * @return A override that still has to be registered to the layer, but with the same transformations and id.
     */
    public ArmorModelPartDefinition createOverride(@Nonnull final ResourceLocation newTextureLocation) {
        if (newTextureLocation.equals(getTextureLocation()))
        {
            return this;
        }

        return new ArmorModelPartDefinition(getId(), newTextureLocation, new TRSRTransformation(getTransformation().getMatrix()));
    }
}
