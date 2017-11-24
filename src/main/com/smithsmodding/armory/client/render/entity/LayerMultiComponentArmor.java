package com.smithsmodding.armory.client.render.entity;

import com.google.common.collect.Maps;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.util.common.armor.ArmorHelper;
import com.smithsmodding.armory.common.item.armor.ItemMultiComponentArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * Class used to render an ItemMultiComponentArmor as Armor on Biped Models.
 */
public class LayerMultiComponentArmor implements LayerRenderer<EntityLivingBase>
{
    protected static final ResourceLocation ENCHANTED_ITEM_GLINT_RES = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    private final RenderLivingBase<?> renderer;
    private static final Map<String, ResourceLocation> ARMOR_TEXTURE_RES_MAP = Maps.<String, ResourceLocation>newHashMap();

    public LayerMultiComponentArmor(RenderLivingBase<?> rendererIn)
    {
        this.renderer = rendererIn;
    }

    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.CHEST);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.LEGS);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.FEET);
        this.renderArmorLayer(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale, EntityEquipmentSlot.HEAD);
    }

    public boolean shouldCombineTextures()
    {
        return false;
    }

    private void renderArmorLayer(EntityLivingBase entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        @Nonnull final ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

        if (itemstack.getItem() instanceof ItemMultiComponentArmor)
        {
            @Nullable final IMultiComponentArmor armorData = ArmorHelper.getArmorForItem(itemstack.getItem());

            if (armorData.getEquipmentSlotIndex() == slotIn.getSlotIndex())
            {
                ModelBiped t = getArmorModelHook(entityLivingBaseIn, itemstack, slotIn);

                if (t == null)
                {
                    //Abort if the model is not available.
                    return;
                }

                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                t.setModelAttributes(this.renderer.getMainModel());
                t.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                this.setModelSlotVisible(t, slotIn);

                t.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    protected void setModelSlotVisible(ModelBiped modelBiped, EntityEquipmentSlot slotIn)
    {
        this.setModelVisible(modelBiped);

        switch (slotIn)
        {
            case HEAD:
                modelBiped.bipedHead.showModel = true;
                //modelBiped.bipedHeadwear.showModel = true;
                break;
            case CHEST:
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightArm.showModel = true;
                //modelBiped.bipedLeftArm.showModel = true;
                break;
            case LEGS:
                modelBiped.bipedBody.showModel = true;
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
                break;
            case FEET:
                modelBiped.bipedRightLeg.showModel = true;
                modelBiped.bipedLeftLeg.showModel = true;
        }
    }


    protected void setModelVisible(ModelBiped model)
    {
        model.setInvisible(false);
    }

    /*=================================== FORGE START =========================================*/

    /**
     * Hook to allow item-sensitive armor model. for LayerBipedArmor.
     */
    @Nullable
    protected ModelBiped getArmorModelHook(EntityLivingBase entity, ItemStack itemStack, EntityEquipmentSlot slot)
    {
        if (itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemMultiComponentArmor))
        {
            return null;
        }

        ItemMultiComponentArmor itemMultiComponentArmor = (ItemMultiComponentArmor) itemStack.getItem();
        return itemMultiComponentArmor.getArmorModel(entity, itemStack, slot, null);
    }
    /*=================================== FORGE END ===========================================*/
}
