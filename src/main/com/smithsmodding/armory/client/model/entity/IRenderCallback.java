package com.smithsmodding.armory.client.model.entity;

import org.jetbrains.annotations.NotNull;

@FunctionalInterface
interface IRenderCallback
{
    void apply(float scale, @NotNull final ItemStackModelRenderer renderer);
}
