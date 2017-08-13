package com.smithsmodding.armory.client.model.item.baked;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

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
                    this.bipedBody = new ItemStackModelRenderer(stack, entity);
                    break;
                case ARMLEFT:
                    this.bipedLeftArm = new ItemStackModelRenderer(stack, entity);
                    break;
                case ARMRIGHT:
                    this.bipedRightArm = new ItemStackModelRenderer(stack, entity);
                    break;
                case HEAD:
                    this.bipedHead = new ItemStackModelRenderer(stack, entity);
                    break;
                case HEADWEAR:
                    this.bipedHeadwear = new ItemStackModelRenderer(stack, entity);
                    break;
                case LEGLEFT:
                    this.bipedLeftLeg = new ItemStackModelRenderer(stack, entity);
                    break;
                case LEGRIGHT:
                    this.bipedRightLeg = new ItemStackModelRenderer(stack, entity);
                    break;
            }
        }
    }

    private static class ItemStackModelRenderer extends ModelRenderer
    {

        private final ItemStack   stack;
        private final EntityLivingBase entity;

        public ItemStackModelRenderer(
                                       final ItemStack stack, final EntityLivingBase entity)
        {
            super(new ModelBase() {});
            this.stack = stack;
            this.entity = entity;
            this.rotateAngleZ = (float) Math.PI;
        }

        /*@Override
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
        }*/

        @Override
        public void render(final float scale)
        {
            renderItemStack();
        }

        public void renderItemStack()
        {
            Minecraft.getMinecraft().getItemRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.HEAD);
        }
    }
}
