package com.ldtteam.armory.api.client.render.provider.model;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IModelProvider
{

    /**
     * Method used to get a rendering {@link IBakedModel} from this {@link IModelProvider}.
     *
     * @param entityLiving The {@link EntityLivingBase} to get the {@link IBakedModel} for.
     * @param itemStack The {@link ItemStack} for which the {@link IBakedModel} is retrieved.
     * @param armorSlot The {@link EntityEquipmentSlot} in which the {@link ItemStack} resides. Null if unknown.
     *
     * @return The {@link IBakedModel} for the given {@link ItemStack}
     */
    @Nullable
    @SideOnly(Side.CLIENT)
    IBakedModel getRenderingModel(@NotNull final EntityLivingBase entityLiving, @NotNull final  ItemStack itemStack, @Nullable final  EntityEquipmentSlot armorSlot);
}
