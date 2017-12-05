package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.api.util.references.ModBlocks;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.ArmoryClientProxy;
import com.smithsmodding.armory.client.render.entity.LayerMultiComponentArmor;
import com.smithsmodding.armory.client.render.tileentity.*;
import com.smithsmodding.armory.common.block.BlockConduit;
import com.smithsmodding.armory.common.block.BlockMoltenMetalTank;
import com.smithsmodding.armory.common.block.BlockPump;
import com.smithsmodding.armory.common.block.types.EnumConduitType;
import com.smithsmodding.armory.common.block.types.EnumPumpType;
import com.smithsmodding.armory.common.block.types.EnumTankType;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.smithscore.client.block.statemap.ExtendedStateMap;
import com.smithsmodding.smithscore.client.model.loader.MultiComponentModelLoader;
import com.smithsmodding.smithscore.client.model.loader.SmithsCoreOBJLoader;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.client.render.layers.CancelableLayerCustomHead;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.RenderArmorStand;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientSystemInitializer extends IInitializationComponent.Impl {

    private static final ClientSystemInitializer INSTANCE = new ClientSystemInitializer();

    public static ClientSystemInitializer getInstance () {
        return INSTANCE;
    }

    private ClientSystemInitializer () {
    }

    @Override
    public void onPreInit(@Nonnull FMLPreInitializationEvent preInitializationEvent) {
        registerIIR();
        registerTESR();
    }

    @Override
    public void onInit(@Nonnull final FMLInitializationEvent initializationEvent)
    {
        registerRenderLayers();
    }

    private void registerRenderLayers()
    {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        RenderPlayer renderPlayer = skinMap.get("default");
        renderPlayer.addLayer(new LayerMultiComponentArmor(renderPlayer));

        renderPlayer = skinMap.get("slim");
        renderPlayer.addLayer(new LayerMultiComponentArmor(renderPlayer));

        RenderZombie renderZombie = (RenderZombie) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityZombie.class);
        renderZombie.addLayer(new LayerMultiComponentArmor(renderZombie));

        RenderSkeleton renderSkeleton = (RenderSkeleton) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntitySkeleton.class);
        renderSkeleton.addLayer(new LayerMultiComponentArmor(renderSkeleton));

        RenderArmorStand renderArmorStand = (RenderArmorStand) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityArmorStand.class);
        renderArmorStand.addLayer(new LayerMultiComponentArmor(renderSkeleton));
    }

    private void registerIIR() {
        ArmoryClientProxy proxy = (ArmoryClientProxy) Armory.proxy;

        IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry().forEach(iMultiComponentArmor -> {
            ArmoryClientProxy.registerArmorItemModel(iMultiComponentArmor.getItem());
        });

        ArmoryClientProxy.registerHeatedItemItemModel((ItemHeatedItem) ModItems.IT_HEATEDITEM);
        ArmoryClientProxy.registerComponentItemModel((ItemArmorComponent) ModItems.IT_COMPONENT);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_CHAIN);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_PLATE);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_RING);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_NUGGET);
        ArmoryClientProxy.registerMaterializedItemModel(ModItems.IT_INGOT);

        SmithsCoreOBJLoader.INSTANCE.addDomain(References.General.MOD_ID.toLowerCase());
        ModelLoader.setCustomModelResourceLocation(ModItems.IT_GUIDE, 0, new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.Items.SmithingsGuide", "inventory"));

        MultiComponentModelLoader.instance.registerDomain(References.General.MOD_ID);
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_TONGS, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_HAMMER, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_CONDUIT), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.NORMAL)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory");

                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.LIGHT)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory");

                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.VERTICAL)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_CONDUIT), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_TANK), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (EnumTankType.byMetadata(stack.getItemDamage()) == EnumTankType.NORMAL)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory");

                if (EnumTankType.byMetadata(stack.getItemDamage()) == EnumTankType.LIGHT)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_TANK), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_PUMP), new ItemMeshDefinition() {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                if (EnumPumpType.byMetadata(stack.getItemDamage()) == EnumPumpType.HORIZONTAL)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory");

                if (EnumPumpType.byMetadata(stack.getItemDamage()) == EnumPumpType.VERTICAL)
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory");

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_PUMP), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory"),
                new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), new ItemMeshDefinition() {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack) {
                return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory");
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory"));
    }


    private void registerTESR() {
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FORGE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_ANVIL);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FIREPLACE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_RESOURCE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_MOLTENMETALMIXER);

        ModelLoader.setCustomStateMapper(ModBlocks.BL_CONDUIT, new ExtendedStateMap.Builder().withName(BlockConduit.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Conduit.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_TANK, new ExtendedStateMap.Builder().withName(BlockMoltenMetalTank.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Tank.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_PUMP, new ExtendedStateMap.Builder().withName(BlockPump.TYPE).withCamelCase(new char[]{'.'}).withPrefix("Armory.Blocks.Pump.").build());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityRendererForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConduit.class, new TileEntityRendererConduit());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalTank.class, new TileEntityRendererMoltenMetalTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPump.class, new TileEntityRendererPump());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalMixer.class, new TileEntityRendererMoltenMetalMixer());
    }
}
