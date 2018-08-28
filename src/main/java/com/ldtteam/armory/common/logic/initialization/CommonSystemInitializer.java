package com.ldtteam.armory.common.logic.initialization;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.util.common.CapabilityHelper;
import com.ldtteam.armory.api.util.references.*;
import com.ldtteam.armory.common.config.ArmoryConfig;
import com.ldtteam.armory.common.creativetabs.ArmorTab;
import com.ldtteam.armory.common.creativetabs.ComponentsTab;
import com.ldtteam.armory.common.creativetabs.GeneralTabs;
import com.ldtteam.armory.common.creativetabs.HeatedItemTab;
import com.ldtteam.armory.common.item.ItemHeatableResource;
import com.ldtteam.armory.common.tileentity.*;
import com.ldtteam.smithscore.util.common.helper.ItemStackHelper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.util.Iterator;

/**
 * Created by marcf on 1/25/2017.
 */
public class CommonSystemInitializer extends IInitializationComponent.Impl implements IInitializationComponent {

    private static final CommonSystemInitializer INSTANCE = new CommonSystemInitializer();

    public static CommonSystemInitializer getInstance() {
        return INSTANCE;
    }

    private CommonSystemInitializer() {
    }

    private static void removeRecipes()
    {
        if (!ArmoryConfig.enableHardModeNuggetRemoval)
        {
            return;
        }

        Iterator<IRecipe> iterator = CraftingManager.REGISTRY.iterator();
        while (iterator.hasNext())
        {
            IRecipe recipe = iterator.next();
            tryRemoveRecipeFromGame(recipe, iterator);
        }
    }

    @Override
    public void onPostInit(@Nonnull FMLPostInitializationEvent event) {
        removeRecipes();
        initializeOreDict();
    }

    private static void tryRemoveRecipeFromGame(@Nonnull IRecipe recipe, @Nonnull Iterator iterator) {
        if (recipe.getRecipeOutput().isEmpty())
            return;

        if (recipe.getRecipeOutput().getItem() == null)
            return;

        int[] oreIds = OreDictionary.getOreIDs(recipe.getRecipeOutput());

        for (int Id : oreIds) {
            String oreName = OreDictionary.getOreName(Id);
            if (oreName.contains("nugget")) {
                for (ICoreArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        }
                        catch (Exception ex)
                        {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }

                for (IAddonArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        }
                        catch (Exception ex)
                        {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }

                for (IAnvilMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
                    if (oreName.toLowerCase().contains(material.getOreDictionaryIdentifier().toLowerCase())) {
                        try {
                            iterator.remove();
                            return;
                        }
                        catch (Exception ex)
                        {
                            ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            return;
                        }
                    }
                }
            }
        }
    }

    @Override
    public void onPreInit(@Nonnull final FMLPreInitializationEvent preInitializationEvent)
    {
        registerCreativeTabs();
    }

    private static void registerCreativeTabs()
    {
        ModCreativeTabs.GENERAL = new GeneralTabs();
        ModCreativeTabs.COMPONENTS = new ComponentsTab();
        ModCreativeTabs.HEATEDITEM = new HeatedItemTab();
        ModCreativeTabs.ARMOR = new ArmorTab();
    }

    @Override
    public void onInit(@Nonnull final FMLInitializationEvent initializationEvent)
    {
        registerTileEntities();
        setupCreativeTabs();
    }

    private void registerTileEntities()
    {
        GameRegistry.registerTileEntity(TileEntityForge.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.ForgeContainer));
        GameRegistry.registerTileEntity(TileEntityFireplace.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.FireplaceContainer));
        GameRegistry.registerTileEntity(TileEntityBlackSmithsAnvil.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.ArmorsAnvil));
        GameRegistry.registerTileEntity(TileEntityConduit.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.Conduit));
        GameRegistry.registerTileEntity(TileEntityMoltenMetalTank.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.Tank));
        GameRegistry.registerTileEntity(TileEntityPump.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.Pump));
        GameRegistry.registerTileEntity(TileEntityMoltenMetalMixer.class, new ResourceLocation(References.General.MOD_ID, References.InternalNames.TileEntities.MoltenMetalMixer));
    }

    private static void initializeOreDict() {
        NonNullList<ItemStack> chains = NonNullList.create();
        NonNullList<ItemStack> rings = NonNullList.create();
        NonNullList<ItemStack> plates = NonNullList.create();
        NonNullList<ItemStack> nuggets = NonNullList.create();
        NonNullList<ItemStack> ingots = NonNullList.create();
        NonNullList<ItemStack> blocks = NonNullList.create();

        ItemHeatableResource.getSubItemsStatic(ModItems.IT_CHAIN, chains);
        ItemHeatableResource.getSubItemsStatic(ModItems.IT_NUGGET, nuggets);
        ItemHeatableResource.getSubItemsStatic(ModItems.IT_PLATE, plates);
        ItemHeatableResource.getSubItemsStatic(ModItems.IT_RING, rings);
        ItemHeatableResource.getSubItemsStatic(ModItems.IT_INGOT, ingots);
        ItemHeatableResource.getSubItemsStatic(Item.getItemFromBlock(ModBlocks.BL_RESOURCE), blocks);

        for (ItemStack chain : chains) {
            OreDictionary.registerOre("chain" + CapabilityHelper.getMaterialFromMaterializedStack(chain).getOreDictionaryIdentifier(), chain);
        }

        for (ItemStack ring : rings) {
            OreDictionary.registerOre("ring" + CapabilityHelper.getMaterialFromMaterializedStack(ring).getOreDictionaryIdentifier(), ring);
        }

        for (ItemStack plate : plates) {
            OreDictionary.registerOre("plate" + CapabilityHelper.getMaterialFromMaterializedStack(plate).getOreDictionaryIdentifier(), plate);
        }

        for (ItemStack nugget : nuggets) {
            OreDictionary.registerOre("nugget" + CapabilityHelper.getMaterialFromMaterializedStack(nugget).getOreDictionaryIdentifier(), nugget);
        }

        for (ItemStack ingot : ingots) {
            OreDictionary.registerOre("ingot" + CapabilityHelper.getMaterialFromMaterializedStack(ingot).getOreDictionaryIdentifier(), ingot);
        }

        for (ItemStack block : blocks) {
            OreDictionary.registerOre("block" + CapabilityHelper.getMaterialFromMaterializedStack(block).getOreDictionaryIdentifier(), block);
        }

        OreDictionary.registerOre("blockObsidian", Blocks.OBSIDIAN);
        OreDictionary.registerOre("blockIron", Blocks.IRON_BLOCK);
        OreDictionary.registerOre("blockGold", Blocks.GOLD_BLOCK);
        OreDictionary.registerOre("blockStone", Blocks.STONE);
    }

    private static void setupCreativeTabs()
    {
        ModItems.IT_CHAIN.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_GUIDE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_HAMMER.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_INGOT.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_NUGGET.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_PLATE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_RING.setCreativeTab(ModCreativeTabs.GENERAL);
        ModItems.IT_TONGS.setCreativeTab(ModCreativeTabs.GENERAL);

        ModItems.IT_COMPONENT.setCreativeTab(ModCreativeTabs.COMPONENTS);

        ModItems.IT_HEATEDITEM.setCreativeTab(ModCreativeTabs.HEATEDITEM);

        ModItems.Armor.IT_CHESTPLATE.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_HELMET.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_LEGGINGS.setCreativeTab(ModCreativeTabs.ARMOR);
        ModItems.Armor.IT_SHOES.setCreativeTab(ModCreativeTabs.ARMOR);

        ModBlocks.BL_ANVIL.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_CONDUIT.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_FIREPLACE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_FORGE.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_PUMP.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_TANK.setCreativeTab(ModCreativeTabs.GENERAL);
        ModBlocks.BL_RESOURCE.setCreativeTab(ModCreativeTabs.GENERAL);
    }
}
