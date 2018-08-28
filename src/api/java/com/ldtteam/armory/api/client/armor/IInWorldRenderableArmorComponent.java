package com.ldtteam.armory.api.client.armor;

import com.ldtteam.armory.api.util.client.ModelTransforms;
import net.minecraft.client.model.ModelRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface describing an armor component that can be rendered in the world.
 */
public interface IInWorldRenderableArmorComponent<T> {
    /**
     * Method to get the renderer that is used to render the Armor on the entity.
     * @return The in world renderer.
     */
    @Nullable
    @SideOnly(Side.CLIENT)
    ModelRenderer getRendererForArmor();

    /**
     * Method to get the transforms for the in world rendering.
     * @return The transforms for the in world rendering.
     */
    @Nonnull
    @SideOnly(Side.CLIENT)
    ModelTransforms getRenderTransforms();

    /**
     * Method to set the renderer of this Renderable.
     * @param renderer The new renderer of this Renderable
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    T setRendererForArmor(@Nonnull ModelRenderer renderer);

    /**
     * Method to set the transforms of this Renderable
     * @param transforms The new transforms of this Renderable.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    T setRenderTransforms(@Nonnull ModelTransforms transforms);
}
