package com.smithsmodding.armory.client.model.entity;

import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by marcf1 on 8/16/2016.
 */
public class LayerMultiComponentArmorModelHelper
{
    //stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.HEAD),
    public static ModelRenderer getBipedHeadRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preHeadRender, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::headRotationAngleUpdateCallback, originalRenderer);
    }

    public static ModelRenderer getBipedBodyRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyMainRender, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::defaultRotationAngleUpdateCallback, originalRenderer);
    }

    public static ModelRenderer getBipedLeftArmRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyArmLeftCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::leftArmRotationAngleUpdateCallback, originalRenderer);
    }

    public static ModelRenderer getBipedRightArmRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preBodyArmRightCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::rightArmRotationAngleUpdateCallback, originalRenderer);
    }

    public static ModelRenderer getBipedLeftLegRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preLegLeftCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::leftLegRotationAngleUpdateCallback, originalRenderer);
    }

    public static ModelRenderer getBipedRightLegRenderer(@Nullable final ModelRenderer originalRenderer)
    {
        return new ItemStackModelRenderer(LayerMultiComponentArmorModelHelper::preLegRightCallback, LayerMultiComponentArmorModelHelper::defaultPostRenderCallback,
          LayerMultiComponentArmorModelHelper::rightLegRotationAngleUpdateCallback, originalRenderer);
    }

    private static void defaultPostRenderCallback(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private static void preHeadRender(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        if (renderer.rotateAngleY == 0F)
        {
            GlStateManager.rotate(180F, 0F, 1F, 0F);
        }

        /*
        if (renderer.rotateAngleZ == 0F)
        {
            GlStateManager.rotate(180F, 0F, 0F, 1F);
        }
        */

        GlStateManager.translate(0, 4f * scale, 0);
        GlStateManager.scale(0.625F, 0.625F, -0.625F);

        GlStateManager.pushMatrix();
    }

    private static void headRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
        renderer.rotateAngleZ *= -1f;
    }

    private static void preBodyMainRender(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(4.75f * scale, 8f * scale, -2f * scale);
        GlStateManager.scale(1.2f, -1f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private static void preBodyArmRightCallback(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(-1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, 1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private static void rightArmRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }

    private static void preBodyArmLeftCallback(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, 1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private static void leftArmRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }

    private static void preLegRightCallback(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(-0.5f * scale, -2f*scale, 0);
        GlStateManager.scale(1.2f, 1.0f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private static void rightLegRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }

    private static void preLegLeftCallback(float scale, @NotNull final ItemStackModelRenderer renderer) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(+0.5f * scale, -2f*scale, -0*scale);
        GlStateManager.scale(1.2f, 1f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private static void leftLegRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }

    private static void defaultRotationAngleUpdateCallback(float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        //NOOP;
    }
}
