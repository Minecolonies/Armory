package com.smithsmodding.armory.client.model.entity;

import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by marcf1 on 8/16/2016.
 */
public class BakedMLABipedModel extends ModelBiped {

    public BakedMLABipedModel(final ItemStack stack,
                                            final BakedMultiLayeredArmorItemModel model,
                                            final EntityLivingBase entity) {
        super(1);


        this.bipedBody = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.BODY), this::preBodyMainRender, this::defaultPostRenderCallback,
                                                     this::defaultRotationAngleUpdateCallback);
        this.bipedLeftArm = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.ARMLEFT), this::preBodyArmLeftCallback, this::defaultPostRenderCallback,
                                                        this::leftArmRotationAngleUpdateCallback);
        this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
        this.bipedRightArm = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.ARMRIGHT), this::preBodyArmRightCallback, this::defaultPostRenderCallback,
                                                         this::rightArmRotationAngleUpdateCallback);
        this.bipedRightArm.setRotationPoint(-5.0F, 2.0F , 0.0F);
        this.bipedHead = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.HEAD), this::preHeadRender, this::defaultPostRenderCallback,
                                                     this::defaultRotationAngleUpdateCallback);
        this.bipedHeadwear = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.HEADWEAR), this::preHeadRender, this::defaultPostRenderCallback,
                                                         this::defaultRotationAngleUpdateCallback);
        this.bipedLeftLeg = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.LEGLEFT), this::preHeadRender, this::defaultPostRenderCallback,
                                                        this::defaultRotationAngleUpdateCallback);
        this.bipedRightLeg = new ItemStackModelRenderer(entity, stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.LEGRIGHT), this::preHeadRender, this::defaultPostRenderCallback,
                                                         this::defaultRotationAngleUpdateCallback);

    }

    private void defaultPostRenderCallback(EntityLivingBase entityLivingBase, ItemStack stack, float scale) {
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    private void preHeadRender(EntityLivingBase entityLivingBase, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(0, -4f * scale, 0);
        GlStateManager.scale(0.625F, -0.625F, -0.625F);

        GlStateManager.pushMatrix();
    }

    private void preBodyMainRender(EntityLivingBase entityLivingBase, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(4.75f * scale, -8f * scale, -2f * scale);
        GlStateManager.scale(1.2f, 1f, 1.2f);

        GlStateManager.pushMatrix();
    }

    private void preBodyArmRightCallback(EntityLivingBase entityLivingBase, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(-1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, 1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private void rightArmRotationAngleUpdateCallback(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack, float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }

    private void preBodyArmLeftCallback(EntityLivingBase entityLivingBase, ItemStack stack, float scale) {
        GlStateManager.pushMatrix();

        GlStateManager.translate(1f * scale, 0*scale, 0);
        GlStateManager.scale(-1.1f, 1.1f, 1.1f);

        GlStateManager.pushMatrix();
    }

    private void leftArmRotationAngleUpdateCallback(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack, float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        renderer.rotateAngleX *= -1f;
        renderer.rotateAngleY *= -1f;
    }


    private void defaultRotationAngleUpdateCallback(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack, float scale, @NotNull final ItemStackModelRenderer renderer)
    {
        //NOOP;
    }

    @FunctionalInterface
    private interface IRenderCallback
    {
        void apply(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack, float scale);
    }

    @FunctionalInterface
    private interface IRotationAngleUpdateCallback
    {
        void apply(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack, float scale, @NotNull final ItemStackModelRenderer renderer);
    }

    private static class ItemStackModelRenderer extends ModelRenderer
    {

        private final EntityLivingBase entity;
        private final ItemStack       stack;
        private final IBakedModel     model;
        private final IRenderCallback preRenderCallback;
        private final IRenderCallback postRenderCallback;
        private final IRotationAngleUpdateCallback rotationAngleUpdateCallback;

        public ItemStackModelRenderer(
                                       final EntityLivingBase entity,
                                       final ItemStack stack,
                                       final IBakedModel model,
                                       final IRenderCallback preRenderCallback,
                                       final IRenderCallback postRenderCallback,
                                       final IRotationAngleUpdateCallback rotationAngleUpdateCallback)
        {
            super(new ModelBase() {});
            this.entity = entity;
            this.stack = stack;
            this.model = model;
            this.preRenderCallback = preRenderCallback;
            this.postRenderCallback = postRenderCallback;
            this.rotationAngleUpdateCallback = rotationAngleUpdateCallback;

            this.rotateAngleZ = (float) Math.PI;
        }

        @Override
        public void render(final float scale)
        {
            if (!this.isHidden)
            {
                if (this.showModel)
                {

                    this.rotationAngleUpdateCallback.apply(entity, stack, scale, this);
                    GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);

                    if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
                    {
                        if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
                        {
                            renderItemStack(scale);
                        }
                        else
                        {
                            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                            renderItemStack(scale);

                            GlStateManager.translate(-this.rotationPointX * scale, -this.rotationPointY * scale, -this.rotationPointZ * scale);
                        }
                    }
                    else
                    {
                        GlStateManager.pushMatrix();
                        GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                        if (this.rotateAngleZ != 0.0F)
                        {
                            GlStateManager.rotate(this.rotateAngleZ * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
                        }

                        if (this.rotateAngleY != 0.0F)
                        {
                            GlStateManager.rotate(-this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                        }

                        if (this.rotateAngleX != 0.0F)
                        {
                            GlStateManager.rotate(-this.rotateAngleX * (180F / (float)Math.PI) + 180, 1.0F, 0.0F, 0.0F);
                        }

                        renderItemStack(scale);

                        GlStateManager.popMatrix();
                    }

                    GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
                }
            }
        }

        /*
        @Override
        public void render(final float scale)
        {
            renderItemStack();
        }*/

        public void renderItemStack(float scale)
        {
            preRenderCallback.apply(entity, stack, scale);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack,model);
            postRenderCallback.apply(entity, stack, scale);
        }
    }

}
