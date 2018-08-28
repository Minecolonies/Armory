package com.ldtteam.armory.api.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IRotationAngleUpdateCallback
{
    void apply(float scale, @NotNull final ModelRenderer renderer);
}
