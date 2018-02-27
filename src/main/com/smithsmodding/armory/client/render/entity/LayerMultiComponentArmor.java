package com.smithsmodding.armory.client.render.entity;

import com.smithsmodding.armory.api.client.model.ModelPart;
import com.smithsmodding.armory.api.client.model.entity.IModelUpdateCallback;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.util.common.armor.ArmorHelper;
import com.smithsmodding.armory.client.model.entity.ItemStackModelRenderer;
import com.smithsmodding.armory.client.model.entity.LayerMultiComponentArmorModelHelper;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.smithsmodding.armory.common.item.armor.ItemMultiComponentArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Class used to render an ItemMultiComponentArmor as Armor on Biped Models.
 */
public class LayerMultiComponentArmor<E extends EntityLivingBase, M extends ModelBiped> implements LayerRenderer<E>
{
    @Nonnull
    private final M         modelBiped;
    @Nonnull
    private final ModelBase mainModel;

    public LayerMultiComponentArmor(@Nonnull final M modelBiped, @Nonnull final ModelBase mainModel)
    {
        this.modelBiped = modelBiped;
        this.mainModel = mainModel;

        this.updateModel();
    }

    private void updateModel()
    {
        modelBiped.bipedHead = LayerMultiComponentArmorModelHelper.getBipedHeadRenderer(modelBiped.bipedHead);
        modelBiped.bipedBody = LayerMultiComponentArmorModelHelper.getBipedBodyRenderer(modelBiped.bipedBody);
        modelBiped.bipedLeftArm = LayerMultiComponentArmorModelHelper.getBipedLeftArmRenderer(modelBiped.bipedLeftArm);
        modelBiped.bipedRightArm = LayerMultiComponentArmorModelHelper.getBipedRightArmRenderer(modelBiped.bipedRightArm);
        modelBiped.bipedLeftLeg = LayerMultiComponentArmorModelHelper.getBipedLeftLegRenderer(modelBiped.bipedLeftLeg);
        modelBiped.bipedRightLeg = LayerMultiComponentArmorModelHelper.getBipedRightLegRenderer(modelBiped.bipedRightLeg);
    }

    public void doRenderLayer(E entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
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

    private void renderArmorLayer(E entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale, EntityEquipmentSlot slotIn)
    {
        @Nonnull final ItemStack itemstack = entityLivingBaseIn.getItemStackFromSlot(slotIn);

        if (itemstack.getItem() instanceof ItemMultiComponentArmor)
        {
            @NotNull final ItemMultiComponentArmor itemMultiComponentArmor = (ItemMultiComponentArmor) itemstack.getItem();
            @Nullable final IMultiComponentArmor armorData = ArmorHelper.getArmorForItem(itemstack.getItem());

            if (armorData != null && armorData.getEquipmentSlot().getSlotIndex() == slotIn.getSlotIndex())
            {
                final IBakedModel t = itemMultiComponentArmor.getRenderingModel(entityLivingBaseIn, itemstack, armorData.getEquipmentSlot());

                if (!(t instanceof BakedMultiLayeredArmorItemModel))
                {
                    //Abort if the model is not available.
                    return;
                }

                Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
                modelBiped.setLivingAnimations(entityLivingBaseIn, limbSwing, limbSwingAmount, partialTicks);
                modelBiped.setModelAttributes(this.mainModel);

                this.setModelSlotVisible(modelBiped, slotIn);
                this.updateModelsForStackAndModel(itemstack, (BakedMultiLayeredArmorItemModel) t, entityLivingBaseIn);

                modelBiped.isChild = entityLivingBaseIn.isChild();
                modelBiped.swingProgress = entityLivingBaseIn.getSwingProgress(partialTicks);
                modelBiped.render(entityLivingBaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            }
        }
    }

    private void updateModelsForStackAndModel(@NotNull final ItemStack stack, @NotNull final BakedMultiLayeredArmorItemModel model, @NotNull final EntityLivingBase entity)
    {
        ((ItemStackModelRenderer) modelBiped.bipedHead).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.HEAD));
        ((ItemStackModelRenderer) modelBiped.bipedBody).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.BODY));
        ((ItemStackModelRenderer) modelBiped.bipedLeftArm).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.ARMLEFT));
        ((ItemStackModelRenderer) modelBiped.bipedRightArm).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.ARMRIGHT));
        ((ItemStackModelRenderer) modelBiped.bipedLeftLeg).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.LEGLEFT));
        ((ItemStackModelRenderer) modelBiped.bipedRightLeg).setRenderingData(stack, model.getUntranslatedModel(model, stack, entity.world, entity, ModelPart.LEGRIGHT));
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
                modelBiped.bipedLeftArm.showModel=true;
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
}
