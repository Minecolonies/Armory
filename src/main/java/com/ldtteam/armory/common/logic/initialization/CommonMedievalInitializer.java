package com.ldtteam.armory.common.logic.initialization;

import com.google.common.collect.Sets;
import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.OreDicAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.initialization.IInitializationComponent;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.common.CapabilityHelper;
import com.ldtteam.armory.api.util.references.*;
import com.ldtteam.armory.common.factories.ArmorFactory;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.registries.ForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Set;
import java.util.function.Consumer;

import static com.ldtteam.armory.api.util.references.References.InternalNames.Recipes.Anvil.*;

/**
 * Created by marcf on 1/20/2017.
 */
public final class CommonMedievalInitializer extends IInitializationComponent.Impl {

    private final static CommonMedievalInitializer INSTANCE = new CommonMedievalInitializer();

    public static CommonMedievalInitializer getInstance() {
        return INSTANCE;
    }

    private CommonMedievalInitializer() {
    }

    @Override
    public void onLoadCompleted(@Nonnull FMLLoadCompleteEvent event) {
        ((ForgeRegistry<IAnvilRecipe>) IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry()).clear();
        initializeAnvilRecipes();
        GameRegistry.addShapedRecipe(
          new ResourceLocation(References.General.MOD_ID, References.InternalNames.Items.IN_HAMMER),
          new ResourceLocation(References.General.MOD_ID, References.InternalNames.Items.IN_HAMMER),
          new ItemStack(ModItems.IT_HAMMER, 1, 150),
          "  B", " S ", "S  ",
          'B', new ItemStack(Blocks.IRON_BLOCK),
          'S', new ItemStack(Items.STICK)
        );
        registerHeatableOverrides();
    }

    private static void initializeAnvilRecipes() {
        ItemStack fireplaceStack = new ItemStack(ModBlocks.BL_FIREPLACE);
        IAnvilRecipe fireplaceRecipe = new AnvilRecipe()
                .setCraftingSlotContent(0, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(1, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(2, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(3, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(4, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(5, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(6, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(8, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(9, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(10, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(12, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(14, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(15, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(16, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(18, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                .setCraftingSlotContent(19, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(20, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(21, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(22, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(23, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setCraftingSlotContent(24, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                .setHammerUsage(0).setTongUsage(0).setResult(fireplaceStack).setProgress(10).setRegistryName(RN_FIREPLACE);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(fireplaceRecipe);

        ItemStack forgeStack = new ItemStack(ModBlocks.BL_FORGE);
        IAnvilRecipe forgeRecipe = new AnvilRecipe()
                .setCraftingSlotContent(0, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(4, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.95F))
                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.75F * 0.95F))
                .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(22, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(22, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.65F * 0.95F))
                .setHammerUsage(15).setTongUsage(25).setResult(forgeStack).setProgress(60).setRegistryName(RN_FORGE);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(forgeRecipe);

        ItemStack hammerStack = new ItemStack(ModItems.IT_HAMMER, 1);
        hammerStack.setItemDamage(150);
        IAnvilRecipe hammerRecipe = new AnvilRecipe()
                .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(8, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(12, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(16, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setCraftingSlotContent(20, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                .setProgress(4).setResult(hammerStack).setHammerUsage(4).setTongUsage(0).setRegistryName(RN_HAMMER);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(hammerRecipe);

        ItemStack tongStack = new ItemStack(ModItems.IT_TONGS, 1);
        tongStack.setItemDamage(150);
        IAnvilRecipe tongRecipe = new AnvilRecipe()
                .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, ModMaterials.Armor.Core.IRON, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.85F, ModMaterials.Armor.Core.IRON.getMeltingPoint() * 0.5F * 0.95F))
                .setProgress(4).setResult(tongStack).setHammerUsage(4).setTongUsage(0).setRegistryName(RN_TONGS);
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(tongRecipe);

        initializeAnvilCreationAnvilRecipes();
        initializeMedievalArmorAnvilRecipes();
        initializeMedievalUpgradeAnvilRecipes();
        initializeUpgradeRecipeSystem();

        ModLogger.getInstance().warn(String.format("Registered: %d anvil recipes.",
          IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().getValuesCollection().size()));
    }

    private static void initializeAnvilCreationAnvilRecipes() {
        for (IAnvilMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry()) {
            IAnvilRecipe recipe = material.getRecipeForAnvil();

            if (recipe == null)
                continue;

            recipe.setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_ANVIL.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));

            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(recipe);
        }

        IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().forEach(new MaterializedResourceRecipeCreationHandler());
    }

    private static void initializeMedievalArmorAnvilRecipes() {
        for (ICoreArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            ItemStack chestPlateStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.CHESTPLATE, material, new ArrayList<>());
            IAnvilRecipe chestPlateRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(chestPlateStack).setHammerUsage(38).setTongUsage(24).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_CHESTPLATE.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(chestPlateRecipe);

            ItemStack helmetStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, material, new ArrayList<>());
            IAnvilRecipe helmetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(helmetStack).setHammerUsage(28).setTongUsage(16).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_HELMET.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(helmetRecipe);

            ItemStack leggingsStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.LEGGINGS, material, new ArrayList<>());
            IAnvilRecipe leggingsRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))

                    .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(leggingsStack).setHammerUsage(28).setTongUsage(16).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_LEGGINGS.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(leggingsRecipe);

            ItemStack shoeStack = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.SHOES, material, new ArrayList<>());
            IAnvilRecipe shoeRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.CHAIN, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.35F * 0.85F, material.getMeltingPoint() * 0.35F * 0.95F)))
                    .setProgress(20).setResult(shoeStack).setHammerUsage(18).setTongUsage(12).setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_SHOES.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
            IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(shoeRecipe);
        }
    }

    private static void initializeMedievalUpgradeAnvilRecipes() {
        for(IMultiComponentArmorExtension extension : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry()) {
            if (!extension.hasItemStack())
                continue;

            IAnvilRecipe recipe = extension.getRecipeCallback().getCreationRecipe(extension);

            if (IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(recipe.getRegistryName()))
                continue;

            if (recipe != null)
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(recipe);
        }
    }

    private static void initializeUpgradeRecipeSystem() {
        IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorRegistry().forEach(iMultiComponentArmor -> {
            IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().forEach(iCoreArmorMaterial -> {
                IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().forEach(extension -> {
                    if (!extension.hasItemStack())
                        return;

                    if (extension.getArmor() != iMultiComponentArmor)
                        return;

                    IAnvilRecipe upgradeRecipe = extension.getRecipeCallback().getAttachingRecipe(extension, iMultiComponentArmor, iCoreArmorMaterial);

                    if (upgradeRecipe == null)
                        return;

                    if (IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(upgradeRecipe.getRegistryName()))
                        return;

                    IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(upgradeRecipe);
                });
            });
        });
    }

    private static void registerHeatableOverrides() {
        final Set<String> oreDictionaryProcessed = Sets.newHashSet();

        IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry().getValuesCollection()
          .stream()
          .filter(iCoreArmorMaterial -> iCoreArmorMaterial.getRegistryName().getResourceDomain().equals(References.General.MOD_ID))
          .filter(iCoreArmorMaterial -> !oreDictionaryProcessed.contains(iCoreArmorMaterial.getOreDictionaryIdentifier()))
          .forEach(iCoreArmorMaterial -> {
              oreDictionaryProcessed.add(iCoreArmorMaterial.getOreDictionaryIdentifier());

              registerHeatableOverrideForItems(
                iCoreArmorMaterial,
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_INGOT, iCoreArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_NUGGET, iCoreArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_BLOCK, iCoreArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_CHAIN, iCoreArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_RING, iCoreArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_PLATE, iCoreArmorMaterial.getOreDictionaryIdentifier())
              );
          });

        IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry().getValuesCollection()
          .stream()
          .filter(iAddonArmorMaterial -> iAddonArmorMaterial.getRegistryName().getResourceDomain().equals(References.General.MOD_ID))
          .filter(iAddonArmorMaterial -> !oreDictionaryProcessed.contains(iAddonArmorMaterial.getOreDictionaryIdentifier()))
          .forEach(iAddonArmorMaterial -> {
              oreDictionaryProcessed.add(iAddonArmorMaterial.getOreDictionaryIdentifier());

              registerHeatableOverrideForItems(
                iAddonArmorMaterial,
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_INGOT, iAddonArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_NUGGET, iAddonArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_BLOCK, iAddonArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_CHAIN, iAddonArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_RING, iAddonArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_PLATE, iAddonArmorMaterial.getOreDictionaryIdentifier())
              );
          });

        IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry().getValuesCollection()
          .stream()
          .filter(iAnvilArmorMaterial -> iAnvilArmorMaterial.getRegistryName().getResourceDomain().equals(References.General.MOD_ID))
          .filter(iAnvilArmorMaterial -> !oreDictionaryProcessed.contains(iAnvilArmorMaterial.getOreDictionaryIdentifier()))
          .forEach(iAnvilArmorMaterial -> {
              oreDictionaryProcessed.add(iAnvilArmorMaterial.getOreDictionaryIdentifier());

              registerHeatableOverrideForItems(
                iAnvilArmorMaterial,
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_INGOT, iAnvilArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_NUGGET, iAnvilArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_BLOCK, iAnvilArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_CHAIN, iAnvilArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_RING, iAnvilArmorMaterial.getOreDictionaryIdentifier()),
                findItemStacksFromOreDictionary(References.OreDictionaryIdentifiers.ODI_PLATE, iAnvilArmorMaterial.getOreDictionaryIdentifier())
              );
          });
    }

    private static NonNullList<ItemStack> findItemStacksFromOreDictionary(@Nonnull String oreType, @Nonnull String materialOreDictionaryName)
    {
        return OreDictionary.getOres(oreType + materialOreDictionaryName);
    }

    private static void registerHeatableOverrideForItems(@Nonnull IMaterial material
      , @Nonnull NonNullList<ItemStack> ingotItems
      , @Nonnull NonNullList<ItemStack> nuggetItems
      , @Nonnull NonNullList<ItemStack> blockItems
      , @Nonnull NonNullList<ItemStack> chainItems
      , @Nonnull NonNullList<ItemStack> ringItems
      , @Nonnull NonNullList<ItemStack> plateItems) {

        registerHeatableOverrideForItemsAndType(material, ingotItems, ModHeatedObjectTypes.INGOT);
        registerHeatableOverrideForItemsAndType(material, nuggetItems, ModHeatedObjectTypes.NUGGET);
        registerHeatableOverrideForItemsAndType(material, plateItems, ModHeatedObjectTypes.PLATE);
        registerHeatableOverrideForItemsAndType(material, ringItems, ModHeatedObjectTypes.RING);
        registerHeatableOverrideForItemsAndType(material, chainItems, ModHeatedObjectTypes.CHAIN);
        registerHeatableOverrideForItemsAndType(material, blockItems, ModHeatedObjectTypes.BLOCK);
    }

    private static void registerHeatableOverrideForItemsAndType(@Nonnull IMaterial material
      , @Nonnull NonNullList<ItemStack> items
      , @Nonnull IHeatedObjectType type) {
        if (!items.isEmpty())
            items.forEach(item -> {
                IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager().registerHeatedOverride(
                  ModHeatableObjects.ITEMSTACK
                  , type
                  , material
                  , item);
            });
    }

    private static class MaterializedResourceRecipeCreationHandler implements Consumer<IMaterial> {

        /**
         * Performs this operation on the given argument.
         *
         * @param material the input argument
         */
        @Override
        public void accept(IMaterial material) {
            ItemStack ringStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_RING, material, 1);
            IAnvilRecipe ringRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.65F, material.getMeltingPoint() * 0.75F)))
                    .setProgress(9).setResult(ringStack).setHammerUsage(4).setTongUsage(0).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_RING.getResourcePath() + "-" + material.getOreDictionaryIdentifier()));

            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(ringRecipe.getRegistryName()))
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(ringRecipe);

            ItemStack plateStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_PLATE, material, 1);

            IAnvilRecipe plateRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, material, material.getMeltingPoint() * 0.65F, material.getMeltingPoint() * 0.75F)))
                    .setProgress(15).setResult(plateStack).setHammerUsage(15).setTongUsage(2).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_PLATE.getResourcePath() + "-" + material.getOreDictionaryIdentifier()));

            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(plateRecipe.getRegistryName()))
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(plateRecipe);

            ItemStack nuggetStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_NUGGET, material, 9);

            IAnvilRecipe nuggetRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.5F) * 0.65F, (material.getMeltingPoint() * 0.5F) * 0.75F)))
                    .setProgress(6).setResult(nuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_NUGGET.getResourcePath() + "-" + material.getOreDictionaryIdentifier()));

            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(nuggetRecipe.getRegistryName()))
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(nuggetRecipe);

            ItemStack chainStack = CapabilityHelper.generateMaterializedStack(ModItems.IT_CHAIN, material, 1);

            IAnvilRecipe chainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING, ModHeatableObjects.ITEMSTACK, material, (material.getMeltingPoint() * 0.35F) * 0.65F, (material.getMeltingPoint() * 0.35F) * 0.75F)))
                    .setProgress(10).setResult(chainStack).setHammerUsage(16).setTongUsage(16)
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_CHAIN.getResourcePath() + "-" + material.getOreDictionaryIdentifier()));

            if (!IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().containsKey(chainRecipe.getRegistryName()))
                IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().register(chainRecipe);
        }
    }

}
