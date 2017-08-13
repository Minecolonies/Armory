package com.smithsmodding.armory.api.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.client.model.ModelPart;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

/**
 * Definition for a single part of the model.
 * Aka, a Arm or a Leg.
 */
public class ArmorModelPartDefinition
{
    @Nonnull
    private final ArmorModelLayerDefinition base;

    @Nonnull
    private final ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> layers;

    @Nonnull
    private final ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> broken;

    @Nonnull
    private final ModelPart                       part;

    public ArmorModelPartDefinition(
                                     @Nonnull final ArmorModelLayerDefinition base,
                                     @Nonnull final ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> layers,
                                     @Nonnull final ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> broken,
                                     @Nonnull ModelPart part)
    {
        this.base = base;
        this.layers = layers;
        this.broken = broken;
        this.part = part;
    }

    @Nonnull
    public ArmorModelLayerDefinition getBase()
    {
        return base;
    }

    @Nonnull
    public ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> getLayers()
    {
        return layers;
    }

    @Nonnull
    public ImmutableMap<ResourceLocation, ArmorModelLayerDefinition> getBroken()
    {
        return broken;
    }

    @Nonnull
    public ModelPart getPart()
    {
        return part;
    }
}
