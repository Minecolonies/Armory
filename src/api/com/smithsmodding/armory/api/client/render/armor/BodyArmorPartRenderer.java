package com.smithsmodding.armory.api.client.render.armor;

import com.smithsmodding.armory.api.client.armor.IInWorldRenderableArmorComponent;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.item.ItemStack;

/**
 * Created by Iddo on 8/6/2016.
 */
public class BodyArmorPartRenderer {

    private static final ModelBiped modelBiped = new ModelBiped(1);

    public static void render(IInWorldRenderableArmorComponent armorPart, IBakedModel model, ItemStack itemStack) {
        ModelTransforms transforms = armorPart.getRenderTransforms();

        //fitting block to the body
        GlStateManager.translate(transforms.getRotationPointX() + 1, transforms.getRotationPointY(), transforms.getRotationPointZ());
        if (transforms.getRotateAngleZ() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleZ() * (180F / (float) Math.PI), 0.0F, 0.0F, 1.0F);
        }

        if (transforms.getRotateAngleY() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleY() * (180F / (float) Math.PI), 0.0F, 1.0F, 0.0F);
        }

        if (transforms.getRotateAngleX() != 0.0F) {
            GlStateManager.rotate(transforms.getRotateAngleX() * (180F / (float) Math.PI), 1.0F, 0.0F, 0.0F);
        }

        GlStateManager.translate(transforms.getOffsetX(), transforms.getOffsetZ(), transforms.getOffsetZ());
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(transforms.getBaseScale(), -transforms.getBaseScale(), -transforms.getBaseScale());

        Minecraft.getMinecraft().getRenderItem().renderItem(itemStack,model);

        GlStateManager.popMatrix();
    }
}
