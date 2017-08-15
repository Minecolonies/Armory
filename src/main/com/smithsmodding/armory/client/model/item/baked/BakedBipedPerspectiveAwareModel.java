package com.smithsmodding.armory.client.model.item.baked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Created by marcf1 on 8/16/2016.
 */
public class BakedBipedPerspectiveAwareModel extends ModelBiped {

    public BakedBipedPerspectiveAwareModel(final ItemStack stack,
                                            final BakedMultiLayeredArmorItemModel model,
                                            final EntityLivingBase entity) {
        super(1);

        for(BakedMultiLayeredArmorPartItemModel partItemModel : model.parts.values())
        {
            IBakedModel bakedModel = partItemModel.getOverrides().handleItemState(partItemModel, stack, entity.world, entity);

            switch(partItemModel.getModelPart())
            {
                case BODY:
                    this.bipedBody = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case ARMLEFT:
                    this.bipedLeftArm = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case ARMRIGHT:
                    this.bipedRightArm = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case HEAD:
                    this.bipedHead = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case HEADWEAR:
                    this.bipedHeadwear = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case LEGLEFT:
                    this.bipedLeftLeg = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
                case LEGRIGHT:
                    this.bipedRightLeg = new ItemStackModelRenderer(entity, stack, bakedModel, this::preHeadRender, this::postHeadRender);
                    break;
            }
        }
    }


    private void preHeadRender(EntityLivingBase entityLivingBase, ItemStack stack) {
        GlStateManager.pushMatrix();

        boolean villager = entityLivingBase instanceof EntityVillager || entityLivingBase instanceof EntityZombieVillager;

        GlStateManager.translate(0.0F, -0.25F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(0.625F, -0.625F, -0.625F);

        if (villager)
        {
            GlStateManager.translate(0.0F, 0.1875F, 0.0F);
        }

        GlStateManager.pushMatrix();
    }

    private void postHeadRender(EntityLivingBase entityLivingBase, ItemStack stack) {
        GlStateManager.popMatrix();
        GlStateManager.popMatrix();
    }

    @FunctionalInterface
    private interface IRenderCallback
    {
        void apply(@NotNull final EntityLivingBase entity, @NotNull final ItemStack stack);
    }

    private static class ItemStackModelRenderer extends ModelRenderer
    {

        private final EntityLivingBase entity;
        private final ItemStack       stack;
        private final IBakedModel     model;
        private final IRenderCallback preRenderCallback;
        private final IRenderCallback postRenderCallback;

        public ItemStackModelRenderer(
                                       final EntityLivingBase entity, final ItemStack stack,
                                       final IBakedModel model,
                                       final IRenderCallback preRenderCallback,
                                       final IRenderCallback postRenderCallback)
        {
            super(new ModelBase() {});
            this.entity = entity;
            this.stack = stack;
            this.model = model;
            this.preRenderCallback = preRenderCallback;
            this.postRenderCallback = postRenderCallback;

            this.rotateAngleZ = (float) Math.PI;
        }

        @Override
        public void render(final float scale)
        {
            if (!this.isHidden)
            {
                if (this.showModel)
                {

                    GlStateManager.translate(this.offsetX + 1, this.offsetY, this.offsetZ);

                    if (this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F)
                    {
                        if (this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F)
                        {
                            renderItemStack();
                        }
                        else
                        {
                            GlStateManager.translate(this.rotationPointX * scale, this.rotationPointY * scale, this.rotationPointZ * scale);

                            renderItemStack();

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
                            GlStateManager.rotate(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                        }

                        if (this.rotateAngleX != 0.0F)
                        {
                            GlStateManager.rotate(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                        }

                        renderItemStack();

                        GlStateManager.popMatrix();
                    }

                    GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
                }
            }
        }

        public void renderItemStack()
        {
            preRenderCallback.apply(entity, stack);
            Minecraft.getMinecraft().getRenderItem().renderItem(stack,model);
            postRenderCallback.apply(entity, stack);
        }
    }

}
