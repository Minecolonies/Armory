package com.smithsmodding.armory.client.model.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
interface IRotationAngleUpdateCallback
{
    void apply(float scale, @NotNull final ItemStackModelRenderer renderer);
}
