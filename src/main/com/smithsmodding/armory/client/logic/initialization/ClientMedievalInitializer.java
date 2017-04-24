package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.common.material.client.MaterialRenderControllers;
import com.smithsmodding.armory.api.util.client.ModelTransforms;
import com.smithsmodding.armory.api.util.references.ModArmor;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientMedievalInitializer extends IInitializationComponent.Impl {

    private static final ClientMedievalInitializer INSTANCE = new ClientMedievalInitializer();

    public static ClientMedievalInitializer getInstance() {
        return INSTANCE;
    }

    private ClientMedievalInitializer () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        ModMaterials.Armor.Core.IRON.setRenderInfo(new MaterialRenderControllers.Metal(0xcacaca, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(MinecraftColor.RED);
            }
        });
        ModMaterials.Armor.Addon.IRON.setRenderInfo(ModMaterials.Armor.Core.IRON.getRenderInfo());

        ModMaterials.Armor.Core.OBSIDIAN.setRenderInfo(new MaterialRenderControllers.MultiColor(0x71589c, 0x8f60d4, 0x8c53df));
        ModMaterials.Armor.Addon.OBSIDIAN.setRenderInfo(ModMaterials.Armor.Core.OBSIDIAN.getRenderInfo());

        ModMaterials.Armor.Core.GOLD.setRenderInfo(new MaterialRenderControllers.Metal(0xffd700, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(MinecraftColor.YELLOW);
            }
        });
        ModMaterials.Armor.Addon.GOLD.setRenderInfo(ModMaterials.Armor.Core.GOLD.getRenderInfo());

        ModMaterials.Armor.Core.STEEL.setRenderInfo(new MaterialRenderControllers.Metal(0x6699CC, 0f, 0.3f, 0f) {
            @Nonnull
            @Override
            public MinecraftColor getLiquidColor() {
                return new MinecraftColor(102, 153, 204, 255);
            }
        });
        ModMaterials.Armor.Addon.STEEL.setRenderInfo(ModMaterials.Armor.Core.STEEL.getRenderInfo());

        ModMaterials.Armor.Core.HARDENED_IRON.setRenderInfo(new MaterialRenderControllers.Metal(0x757CBA, 0f, 0.3f, 0f));
        ModMaterials.Armor.Addon.HARDENED_IRON.setRenderInfo(ModMaterials.Armor.Core.HARDENED_IRON.getRenderInfo());

        ModMaterials.Anvil.STONE.setRenderInfo(new MaterialRenderControllers.BlockTexture("minecraft:blocks/stone"));
        ModMaterials.Anvil.IRON.setRenderInfo(ModMaterials.Armor.Core.IRON.getRenderInfo());
        ModMaterials.Anvil.OBSIDIAN.setRenderInfo(ModMaterials.Armor.Core.OBSIDIAN.getRenderInfo());

        ModelBiped componentModel = new ModelBiped(1);
        ModArmor.Medieval.CHESTPLATE.setRendererForArmor(componentModel.bipedBody);
        ModArmor.Medieval.HELMET.setRendererForArmor(componentModel.bipedHead);
        ModArmor.Medieval.LEGGINGS.setRendererForArmor(componentModel.bipedBody);
        ModArmor.Medieval.SHOES.setRendererForArmor(componentModel.bipedBody);

        ModArmor.Medieval.CHESTPLATE.setRenderTransforms(getChestplateModelTransforms());
        ModArmor.Medieval.HELMET.setRenderTransforms(getHelmetModelTransforms());
        ModArmor.Medieval.LEGGINGS.setRenderTransforms(getLeggingsModelTransforms());
        ModArmor.Medieval.SHOES.setRenderTransforms(getShoesModelTransforms());
    }

    private ModelTransforms getChestplateModelTransforms() {
        ModelTransforms transforms = new ModelTransforms();

        transforms.setOffsetY(transforms.getOffsetY() - 0.25f);

        return transforms;
    }

    private ModelTransforms getLeggingsModelTransforms()
    {
        return new ModelTransforms();
    }

    private ModelTransforms getHelmetModelTransforms()
    {
        ModelTransforms transforms = new ModelTransforms();

        transforms.setOffsetY(transforms.getOffsetY() - 0.25f);
        transforms.setBaseScale(0.625f);

        return transforms;
    }

    private ModelTransforms getShoesModelTransforms()
    {
        return new ModelTransforms();
    }
}
