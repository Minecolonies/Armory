package com.smithsmodding.armory.api.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IRotationAngleUpdateCallback
{
    void apply(float scale, @NotNull final ModelRenderer renderer);
}
