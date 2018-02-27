package com.smithsmodding.armory.client.model.entity;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by marcf1 on 8/16/2016.
 */
public class LayerMultiComponentArmorModelHelper
{
    public static ModelRenderer getBipedHeadRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preHeadRender, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    public static ModelRenderer getBipedBodyRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyMainRender, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    public static ModelRenderer getBipedLeftArmRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyArmLeftCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    public static ModelRenderer getBipedRightArmRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyArmRightCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    public static ModelRenderer getBipedLeftLegRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preLegLeftCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    public static ModelRenderer getBipedRightLegRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preLegRightCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback
          , originalRenderer);
    }

    private static void defaultPostRenderCallback(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private static void preHeadRender(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        if (renderer.rotateAngleX == 0F)
        {
            GlStateManager.rotate(180F, 1f, 0f, 0f);
        }

        GlStateManager.translate(0, -4f * scale, 0);
        GlStateManager.scale(-0.625F, -0.625F, 0.625F);

        GlStateManager.pushMatrix();
    }

    private static void preBodyMainRender(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(4.75f * scale, 7.5f * scale, -2f * scale);
        GlStateManager.scale(1.2f, -1f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private static void preBodyArmRightCallback(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(-1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, -1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private static void preBodyArmLeftCallback(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, -1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private static void preLegRightCallback(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(-0.5f * scale, 2f*scale, 0);
        GlStateManager.scale(1.2f, -1.0f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private static void preLegLeftCallback(float scale, @NotNull final ModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(+0.5f * scale, 2f*scale, -0*scale);
        GlStateManager.scale(1.2f, -1f, 1.2f);

        GlStateManager.pushMatrix();
    }
}
