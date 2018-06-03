package com.smithsmodding.armory.client.model.entity;

import com.smithsmodding.armory.api.client.model.entity.IRenderCallback;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ItemStackModelRenderer extends ModelRenderer
{
    private ItemStack stack = null;
    private IBakedModel model = null;
    private final IRenderCallback              preRenderCallback;
    private final IRenderCallback              postRenderCallback;

    public ItemStackModelRenderer(
                                   final IRenderCallback preRenderCallback,
                                   final IRenderCallback postRenderCallback)
    {
        this(preRenderCallback, postRenderCallback, Optional.empty());
    }

    public ItemStackModelRenderer(
      final IRenderCallback preRenderCallback,
      final IRenderCallback postRenderCallback,
      final ModelRenderer originalRenderer)
    {
        this(preRenderCallback, postRenderCallback, Optional.ofNullable(originalRenderer));
    }

    public ItemStackModelRenderer(
      final IRenderCallback preRenderCallback,
      final IRenderCallback postRenderCallback,
      final Optional<ModelRenderer> originalRenderer)
    {
        super(new ModelBase() {});
        this.preRenderCallback = preRenderCallback;
        this.postRenderCallback = postRenderCallback;

        this.rotateAngleZ = (float) Math.PI;
        
        originalRenderer.ifPresent(renderer -> {
            this.rotateAngleX = renderer.rotateAngleX;
            this.rotateAngleY = renderer.rotateAngleY;
            this.rotateAngleZ = renderer.rotateAngleZ;

            this.rotationPointX = renderer.rotationPointX;
            this.rotationPointY = renderer.rotationPointY;
            this.rotationPointZ = renderer.rotationPointZ;

            this.offsetX = renderer.offsetX;
            this.offsetY = renderer.offsetY;
            this.offsetZ = renderer.offsetZ;
        });
    }

    public void setRenderingData(@Nullable ItemStack stack, @Nullable IBakedModel model)
    {
        this.stack = stack;
        this.model = model;
    }

    @Override
    public void render(final float scale)
    {
        Minecraft.getMinecraft().world.profiler.startSection("Armory-3D-Model");
        if (!this.isHidden)
        {
            if (this.showModel)
            {
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
                        GlStateManager.rotate(this.rotateAngleY * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
                    }

                    if (this.rotateAngleX != 0.0F)
                    {
                        GlStateManager.rotate(this.rotateAngleX * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
                    }

                    renderItemStack(scale);

                    GlStateManager.popMatrix();
                }

                GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
            }
        }
        Minecraft.getMinecraft().world.profiler.endSection();
    }

    public void renderItemStack(float scale)
    {
        if (stack == null || model == null)
            return;

        Minecraft.getMinecraft().world.profiler.startSection("Pre-Callback");
        preRenderCallback.apply(scale, this);
        Minecraft.getMinecraft().world.profiler.endSection();

        Minecraft.getMinecraft().world.profiler.startSection("Model-Render");
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
        Minecraft.getMinecraft().world.profiler.endSection();

        Minecraft.getMinecraft().world.profiler.startSection("Post-Callback");
        postRenderCallback.apply(scale, this);
        Minecraft.getMinecraft().world.profiler.endSection();
    }
}
