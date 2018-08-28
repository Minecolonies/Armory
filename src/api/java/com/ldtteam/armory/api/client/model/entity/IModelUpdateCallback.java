package com.ldtteam.armory.api.client.model.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.EntityLivingBase;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface IModelUpdateCallback<E extends EntityLivingBase, M extends ModelBiped>
{

    void apply(@NotNull final E entity, @NotNull final M model);
}
