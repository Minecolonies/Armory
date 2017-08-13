package com.smithsmodding.armory.api.client.armor;

import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
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

    /**
     * Method returns the layer that this component is rendered in.
     * @return The instance of the layer this component is rendered in.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    LayerRenderer<EntityLivingBase> getRenderingLayer();

    /**
     * Method used to set the layer that this component is rendered in.
     * @param layerRenderer The new layer instance that this component is rendered in.
     * @return The instance this method was called on.
     */
    @SideOnly(Side.CLIENT)
    @Nonnull
    T setRenderingLayer(@Nonnull LayerRenderer<EntityLivingBase> layerRenderer);
}
