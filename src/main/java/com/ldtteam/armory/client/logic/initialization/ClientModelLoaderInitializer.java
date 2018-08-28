package com.ldtteam.armory.client.logic.initialization;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.util.references.ModBlocks;
import com.ldtteam.armory.api.util.references.ModItems;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.client.ArmoryClientProxy;
import com.ldtteam.armory.client.model.loaders.*;
import com.ldtteam.armory.client.render.tileentity.*;
import com.ldtteam.armory.common.block.BlockConduit;
import com.ldtteam.armory.common.block.BlockMoltenMetalTank;
import com.ldtteam.armory.common.block.BlockPump;
import com.ldtteam.armory.common.block.types.EnumConduitType;
import com.ldtteam.armory.common.block.types.EnumPumpType;
import com.ldtteam.armory.common.block.types.EnumTankType;
import com.ldtteam.armory.common.item.ItemArmorComponent;
import com.ldtteam.armory.common.item.ItemHeatedItem;
import com.ldtteam.armory.common.tileentity.*;
import com.ldtteam.smithscore.client.block.statemap.ExtendedStateMap;
import com.ldtteam.smithscore.client.model.loader.MultiComponentModelLoader;
import com.ldtteam.smithscore.client.model.loader.SmithsCoreOBJLoader;
import com.ldtteam.smithscore.client.proxy.CoreClientProxy;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/25/2017.
 */
@Mod.EventBusSubscriber(modid = References.General.MOD_ID, value = Side.CLIENT)
public class ClientModelLoaderInitializer
{

    @Nonnull
    private static ArmorComponentModelLoader    armorComponentModelLoader    = new ArmorComponentModelLoader();
    @Nonnull
    private static MultiLayeredArmorModelLoader multiLayeredArmorModelLoader = new MultiLayeredArmorModelLoader();
    @Nonnull
    private static HeatedItemModelLoader        heatedItemModelLoader        = new HeatedItemModelLoader();
    @Nonnull
    private static AnvilModelLoader             anvilBlockModelLoader        = new AnvilModelLoader();
    @Nonnull
    private static MaterializedItemModelLoader  materializedItemModelLoader  = new MaterializedItemModelLoader();
    @Nonnull
    private static MaterializedBlockModelLoader materializedBlockModelLoader = new MaterializedBlockModelLoader();

    @SubscribeEvent
    public static void onModelRegistry(final ModelRegistryEvent event)
    {
        registerLoaders();
        registerIIR();
        registerTESR();
    }

    public static void registerLoaders()
    {
        ModelLoaderRegistry.registerLoader(multiLayeredArmorModelLoader);
        ModelLoaderRegistry.registerLoader(heatedItemModelLoader);
        ModelLoaderRegistry.registerLoader(anvilBlockModelLoader);
        ModelLoaderRegistry.registerLoader(armorComponentModelLoader);
        ModelLoaderRegistry.registerLoader(materializedItemModelLoader);
        ModelLoaderRegistry.registerLoader(materializedBlockModelLoader);
    }

    private static void registerIIR()
    {
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
        ModelLoader.setCustomModelResourceLocation(ModItems.IT_GUIDE,
          0,
          new ModelResourceLocation(References.General.MOD_ID.toLowerCase() + ":" + "armory.items.smithingsguide", "inventory"));
        ModelBakery.registerItemVariants(ModItems.IT_GUIDE, new ResourceLocation(References.General.MOD_ID.toLowerCase(), "armory.items.smithingsguide"));

        MultiComponentModelLoader.instance.registerDomain(References.General.MOD_ID);
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_TONGS,
          new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));
        CoreClientProxy.registerMultiComponentItemModel(ModItems.IT_HAMMER,
          new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Resources." + MultiComponentModelLoader.EXTENSION));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_CONDUIT), new ItemMeshDefinition()
        {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.NORMAL)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory");
                }

                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.LIGHT)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory");
                }

                if (EnumConduitType.byMetadata(stack.getItemDamage()) == EnumConduitType.VERTICAL)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory");
                }

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_CONDUIT),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Normal"), "inventory"),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Light"), "inventory"),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Conduit.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_TANK), new ItemMeshDefinition()
        {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                if (EnumTankType.byMetadata(stack.getItemDamage()) == EnumTankType.NORMAL)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory");
                }

                if (EnumTankType.byMetadata(stack.getItemDamage()) == EnumTankType.LIGHT)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory");
                }

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_TANK),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Normal"), "inventory"),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Tank.Light"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_PUMP), new ItemMeshDefinition()
        {
            @Nullable
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                if (EnumPumpType.byMetadata(stack.getItemDamage()) == EnumPumpType.HORIZONTAL)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory");
                }

                if (EnumPumpType.byMetadata(stack.getItemDamage()) == EnumPumpType.VERTICAL)
                {
                    return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory");
                }

                return null;
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_PUMP),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Horizontal"), "inventory"),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID.toLowerCase(), "Armory.Blocks.Pump.Vertical"), "inventory"));

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), new ItemMeshDefinition()
        {
            @Override
            public ModelResourceLocation getModelLocation(ItemStack stack)
            {
                return new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory");
            }
        });

        ModelBakery.registerItemVariants(Item.getItemFromBlock(ModBlocks.BL_RESOURCE),
          new ModelResourceLocation(new ResourceLocation(References.General.MOD_ID, "Armory.Blocks.Resource"), "inventory"));
    }

    private static void registerTESR()
    {
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FORGE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_ANVIL);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_FIREPLACE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_RESOURCE);
        ArmoryClientProxy.registerBlockModel(ModBlocks.BL_MOLTENMETALMIXER);

        ModelLoader.setCustomStateMapper(ModBlocks.BL_CONDUIT,
          new ExtendedStateMap.Builder().withName(BlockConduit.TYPE).withCamelCase(new char[] {'.'}).withPrefix("Armory.Blocks.Conduit.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_TANK,
          new ExtendedStateMap.Builder().withName(BlockMoltenMetalTank.TYPE).withCamelCase(new char[] {'.'}).withPrefix("Armory.Blocks.Tank.").build());
        ModelLoader.setCustomStateMapper(ModBlocks.BL_PUMP,
          new ExtendedStateMap.Builder().withName(BlockPump.TYPE).withCamelCase(new char[] {'.'}).withPrefix("Armory.Blocks.Pump.").build());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityForge.class, new TileEntityRendererForge());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityConduit.class, new TileEntityRendererConduit());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalTank.class, new TileEntityRendererMoltenMetalTank());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPump.class, new TileEntityRendererPump());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMoltenMetalMixer.class, new TileEntityRendererMoltenMetalMixer());
    }
}
