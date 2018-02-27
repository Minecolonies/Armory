package com.smithsmodding.armory.api.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IRenderCallback
{
    void apply(float scale, @NotNull final ModelRenderer renderer);
}
