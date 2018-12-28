package com.ldtteam.armory.common.logic.initialization;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.armor.*;
import com.ldtteam.armory.api.common.armor.callback.IExtensionRecipeRetrievalCallback;
import com.ldtteam.armory.api.common.capability.armor.IArmorAbsorptionRatioCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorDefenceCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorDurabilityCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorToughnessCapability;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.ldtteam.armory.api.common.events.common.RegisterMaterialDependentCoreExtensionEvent;
import com.ldtteam.armory.api.common.helpers.IMaterialConstructionHelper;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.util.common.CapabilityHelper;
import com.ldtteam.armory.api.util.references.*;
import com.ldtteam.armory.common.armor.MedievalArmor;
import com.ldtteam.armory.common.armor.extension.MedievalArmorExtension;
import com.ldtteam.armory.common.armor.extension.MedievalArmorExtensionPosition;
import com.ldtteam.armory.common.armor.extension.MedievalMaterialDependantArmorExtension;
import com.ldtteam.armory.common.crafting.blacksmiths.recipe.ArmorUpgradeAnvilRecipe;
import com.ldtteam.armory.common.crafting.mixing.MoltenMetalMixingRecipe;
import com.ldtteam.smithscore.common.events.AutomaticEventBusSubscriber;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Armor.*;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.*;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Upgrades.Helmet.*;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Upgrades.Leggings.*;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Upgrades.Shoes.TK_LACESLEFT;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Items.MultiArmor.Upgrades.Shoes.TK_LACESRIGHT;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Materials.Anvil.*;
import static com.ldtteam.armory.api.util.client.TranslationKeys.Materials.Armor.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Defence.Medieval.ChestPlate.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Defence.Medieval.Helmet.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Defence.Medieval.Leggings.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Defence.Medieval.Shoes.DEF_LACESLEFT;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Defence.Medieval.Shoes.DEF_LACESRIGHT;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Durability.Medieval.ChestPlate.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Durability.Medieval.Helmet.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Durability.Medieval.Leggings.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Durability.Medieval.Shoes.DUR_LACESLEFT;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Durability.Medieval.Shoes.DUR_LACESRIGHT;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Toughness.Medieval.ChestPlate.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Toughness.Medieval.Helmet.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Toughness.Medieval.Leggings.*;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Toughness.Medieval.Shoes.TOU_LACESLEFT;
import static com.ldtteam.armory.api.util.references.ModData.Addons.Toughness.Medieval.Shoes.TOU_LACESRIGHT;
import static com.ldtteam.armory.api.util.references.ModData.Armor.Defence.*;
import static com.ldtteam.armory.api.util.references.ModData.Armor.Ratio.*;
import static com.ldtteam.armory.api.util.references.ModData.Armor.Toughness.*;
import static com.ldtteam.armory.api.util.references.ModData.Durability.Anvil.*;
import static com.ldtteam.armory.api.util.references.ModData.Durability.Armor.*;
import static com.ldtteam.armory.api.util.references.ModData.Materials.Gold.*;
import static com.ldtteam.armory.api.util.references.ModData.Materials.Hardened_Iron.*;
import static com.ldtteam.armory.api.util.references.ModData.Materials.Iron.*;
import static com.ldtteam.armory.api.util.references.ModData.Materials.Obsidian.*;
import static com.ldtteam.armory.api.util.references.ModData.Materials.Steel.*;
import static com.ldtteam.armory.api.util.references.ModExtensionPositions.Medieval.ChestPlate.*;
import static com.ldtteam.armory.api.util.references.ModExtensionPositions.Medieval.Helmet.*;
import static com.ldtteam.armory.api.util.references.ModExtensionPositions.Medieval.Leggings.*;
import static com.ldtteam.armory.api.util.references.ModExtensionPositions.Medieval.Shoes.POSITIONLACESLEFT;
import static com.ldtteam.armory.api.util.references.ModExtensionPositions.Medieval.Shoes.POSITIONLACESRIGHT;
import static com.ldtteam.armory.api.util.references.ModExtensions.Medieval.ChestPlate.*;
import static com.ldtteam.armory.api.util.references.ModExtensions.Medieval.Helmet.*;
import static com.ldtteam.armory.api.util.references.ModExtensions.Medieval.Leggings.*;
import static com.ldtteam.armory.api.util.references.ModExtensions.Medieval.Shoes.LACESLEFT;
import static com.ldtteam.armory.api.util.references.ModExtensions.Medieval.Shoes.LACESRIGHT;
import static com.ldtteam.armory.api.util.references.References.InternalNames.AddonPositions.Chestplate.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.AddonPositions.Helmet.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.AddonPositions.Leggings.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.AddonPositions.Shoes.PN_LACESLEFT;
import static com.ldtteam.armory.api.util.references.References.InternalNames.AddonPositions.Shoes.PN_LACESRIGHT;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Materials.Addon.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Materials.Anvil.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Materials.Core.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Recipes.Anvil.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Upgrades.Chestplate.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Upgrades.Helmet.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Upgrades.Leggings.*;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Upgrades.Shoes.UN_LACESLEFT;
import static com.ldtteam.armory.api.util.references.References.InternalNames.Upgrades.Shoes.UN_LACESRIGHT;
/**
 * Created by marcf on 1/25/2017.
 */
@AutomaticEventBusSubscriber(types = {AutomaticEventBusSubscriber.BusType.SERVER}, modid = References.General.MOD_ID)
@Mod.EventBusSubscriber(modid = References.General.MOD_ID)
public class CommonMedievalRegistrationInitializer
{

    //Events
    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handleMedievalArmorRegistration(@Nonnull RegistryEvent.Register<IMultiComponentArmor> armorRegisterEvent)
    {
        IForgeRegistry<IMultiComponentArmor> armorRegistry = armorRegisterEvent.getRegistry();

        ModArmor.Medieval.HELMET =
          new MedievalArmor.Builder(TK_HELMET,
            TextFormatting.RESET.toString(),
            ModItems.Armor.IT_HELMET,
            EntityEquipmentSlot.HEAD,
            Arrays.asList(POSITIONTOP, POSITIONLEFT, POSITIONRIGHT),
            Arrays.asList(TOP, LEFT, RIGHT),
            builder ->
              builder
                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.set(DUR_HELMET))
                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY, IArmorAbsorptionRatioCapability.set(RAT_HELMET))
                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.set(DEF_HELMET))
                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.set(TOU_HELMET))
          )
            .build()
            .setRegistryName(new ResourceLocation(References.General.MOD_ID.toLowerCase(), References.InternalNames.Armor.MEDIEVALHELMET));

        ModArmor.Medieval.CHESTPLATE =
          new MedievalArmor.Builder(TK_CHESTPLATE,
            TextFormatting.RESET.toString(),
            ModItems.Armor.IT_CHESTPLATE,
            EntityEquipmentSlot.CHEST,
            Arrays.asList(POSITIONSHOULDERLEFT, POSITIONSHOULDERRIGHT, POSITIONSTOMACHLEFT, POSITIONSTOMACHRIGHT, POSITIONBACKLEFT, POSITIONBACKRIGHT),
            Arrays.asList(SHOULDERLEFT, SHOULDERRIGHT, STOMACHLEFT, STOMACHRIGHT, BACKLEFT, BACKRIGHT),
            builder ->
              builder
                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.set(DUR_CHESTPLATE))
                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY, IArmorAbsorptionRatioCapability.set(RAT_CHESTPLATE))
                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.set(DEF_CHESTPLATE))
                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.set(TOU_CHESTPLATE))
          ).build()
            .setRegistryName(new ResourceLocation(References.General.MOD_ID.toLowerCase(), References.InternalNames.Armor.MEDIEVALCHESTPLATE));

        ModArmor.Medieval.LEGGINGS =
          new MedievalArmor.Builder(TK_LEGGINGS,
            TextFormatting.RESET.toString(),
            ModItems.Armor.IT_LEGGINGS,
            EntityEquipmentSlot.LEGS,
            Arrays.asList(POSITIONSHINLEFT, POSITIONSHINRIGHT, POSITIONCALFLEFT, POSITIONCALFRIGHT),
            Arrays.asList(SHINLEFT, SHINRIGHT, CALFLEFT, CALFRIGHT),
            builder ->
              builder
                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.set(DUR_LEGGINGS))
                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY, IArmorAbsorptionRatioCapability.set(RAT_LEGGINGS))
                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.set(DEF_LEGGINGS))
                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.set(TOU_LEGGINGS))
          ).build()
            .setRegistryName(new ResourceLocation(References.General.MOD_ID.toLowerCase(), References.InternalNames.Armor.MEDIEVALLEGGINGS));

        ModArmor.Medieval.SHOES =
          new MedievalArmor.Builder(TK_SHOES,
            TextFormatting.RESET.toString(),
            ModItems.Armor.IT_SHOES,
            EntityEquipmentSlot.FEET,
            Arrays.asList(POSITIONLACESLEFT, POSITIONLACESRIGHT),
            Arrays.asList(LACESLEFT, LACESRIGHT),
            builder ->
              builder
                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.set(DUR_SHOES))
                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY, IArmorAbsorptionRatioCapability.set(RAT_SHOES))
                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.set(DEF_SHOES))
                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.set(TOU_SHOES))
          ).build()
            .setRegistryName(new ResourceLocation(References.General.MOD_ID.toLowerCase(), References.InternalNames.Armor.MEDIEVALSHOES));

        armorRegistry.register(ModArmor.Medieval.HELMET);
        armorRegistry.register(ModArmor.Medieval.CHESTPLATE);
        armorRegistry.register(ModArmor.Medieval.LEGGINGS);
        armorRegistry.register(ModArmor.Medieval.SHOES);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handleCoreMaterialRegistration(@Nonnull RegistryEvent.Register<ICoreArmorMaterial> coreArmorMaterialRegisterMaterialEvent)
    {
        IForgeRegistry<ICoreArmorMaterial> registry = coreArmorMaterialRegisterMaterialEvent.getRegistry();
        IMaterialConstructionHelper helper = IArmoryAPI.Holder.getInstance().getHelpers().getMaterialConstructionHelper();

        ModMaterials.Armor.Core.IRON = helper
                                         .createMedievalCoreArmorMaterial(
                                           TK_ARMOR_IRON,
                                           References.Colors.Materials.CLR_IRON.encodeColor(),
                                           "Iron",
                                           IRON_MELTINGPOINT,
                                           IRON_VAPORIZINGPOINT,
                                           IRON_MELTINGTIME,
                                           IRON_VAPORIZINGTIME,
                                           IRON_HEATCOEFFICIENT,
                                           builder ->
                                             builder
                                               .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.multiply(1f))
                                               .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY, IArmorAbsorptionRatioCapability.multiply(1f))
                                               .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.multiply(1f))
                                               .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.multiply(1f))
                                         ).setRegistryName(CMN_IRON);

        ModMaterials.Armor.Core.STEEL = helper
                                          .createMedievalCoreArmorMaterial(TK_ARMOR_STEEL,
                                            References.Colors.Materials.CLR_STEEL.encodeColor(),
                                            "Steel",
                                            STEEL_MELTINGPOINT,
                                            STEEL_VAPORIZINGPOINT,
                                            STEEL_MELTINGTIME,
                                            STEEL_VAPORIZINGTIME,
                                            STEEL_HEATCOEFFICIENT,
                                            builder ->
                                              builder
                                                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                  IArmorDurabilityCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                  IArmorAbsorptionRatioCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                  IArmorDefenceCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                  IArmorToughnessCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                          ).setRegistryName(CMN_STEEL);

        ModMaterials.Armor.Core.GOLD = helper
                                         .createMedievalCoreArmorMaterial(TK_ARMOR_GOLD,
                                           References.Colors.Materials.CLR_GOLD.encodeColor(),
                                           "Gold",
                                           GOLD_MELTINGPOINT,
                                           GOLD_VAPORIZINGPOINT,
                                           GOLD_MELTINGTIME,
                                           GOLD_VAPORIZINGTIME,
                                           GOLD_HEATCOEFFICIENT,
                                           builder ->
                                             builder
                                               .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                 IArmorDurabilityCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                               .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                 IArmorAbsorptionRatioCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                               .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                 IArmorDefenceCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                               .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                 IArmorToughnessCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                         ).setRegistryName(CMN_GOLD);

        ModMaterials.Armor.Core.OBSIDIAN = helper
                                             .createMedievalCoreArmorMaterial(TK_ARMOR_OBSIDIAN,
                                               References.Colors.Materials.CLR_OBSIDIAN.encodeColor(),
                                               "Obsidian",
                                               OBSIDIAN_MELTINGPOINT,
                                               OBSIDIAN_VAPORIZINGPOINT,
                                               OBSIDIAN_MELTINGTIME,
                                               OBSIDIAN_VAPORIZINGTIME,
                                               OBSIDIAN_HEATCOEFFICIENT,
                                               builder ->
                                                 builder
                                                   .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                     IArmorDurabilityCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                   .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                     IArmorAbsorptionRatioCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                   .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                     IArmorDefenceCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                   .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                     IArmorToughnessCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                             ).setRegistryName(CMN_OBSIDIAN);

        ModMaterials.Armor.Core.HARDENED_IRON = helper
                                                  .createMedievalCoreArmorMaterial(TK_ARMOR_HARDENED_IRON,
                                                    References.Colors.Materials.CLR_HARDENED_IRON.encodeColor(),
                                                    "IronHardened",
                                                    HARDENED_IRON_MELTINGPOINT,
                                                    HARDENED_IRON_VAPORIZINGPOINT,
                                                    HARDENED_IRON_MELTINGTIME,
                                                    HARDENED_IRON_VAPORIZINGTIME,
                                                    HARDENED_IRON_HEATCOEFFICIENT,
                                                    builder ->
                                                      builder
                                                        .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                          IArmorDurabilityCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                        .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                          IArmorAbsorptionRatioCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                        .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                          IArmorDefenceCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                        .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                          IArmorToughnessCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                  ).setRegistryName(CMN_HARDENED_IRON);

        registry.register(ModMaterials.Armor.Core.IRON);
        registry.register(ModMaterials.Armor.Core.GOLD);
        registry.register(ModMaterials.Armor.Core.STEEL);
        registry.register(ModMaterials.Armor.Core.OBSIDIAN);
        registry.register(ModMaterials.Armor.Core.HARDENED_IRON);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handleAddonMaterialRegistration(@Nonnull RegistryEvent.Register<IAddonArmorMaterial> addonArmorMaterialRegisterMaterialEvent)
    {
        IForgeRegistry<IAddonArmorMaterial> registry = addonArmorMaterialRegisterMaterialEvent.getRegistry();
        IMaterialConstructionHelper helper = IArmoryAPI.Holder.getInstance().getHelpers().getMaterialConstructionHelper();

        ModMaterials.Armor.Addon.IRON = helper
                                          .createMedievalAddonArmorMaterial(TK_ARMOR_IRON,
                                            References.Colors.Materials.CLR_IRON.encodeColor(),
                                            "Iron",
                                            IRON_MELTINGPOINT,
                                            IRON_VAPORIZINGPOINT,
                                            IRON_MELTINGTIME,
                                            IRON_VAPORIZINGTIME,
                                            IRON_HEATCOEFFICIENT,
                                            builder ->
                                              builder
                                                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                  IArmorDurabilityCapability.multiply(1f))
                                                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                  IArmorAbsorptionRatioCapability.multiply(1f))
                                                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                  IArmorDefenceCapability.multiply(1f))
                                                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                  IArmorToughnessCapability.multiply(1f))
                                          ).setRegistryName(AMN_IRON);

        ModMaterials.Armor.Addon.STEEL = helper
                                           .createMedievalAddonArmorMaterial(TK_ARMOR_STEEL,
                                             References.Colors.Materials.CLR_STEEL.encodeColor(),
                                             "Steel",
                                             STEEL_MELTINGPOINT,
                                             STEEL_VAPORIZINGPOINT,
                                             STEEL_MELTINGTIME,
                                             STEEL_VAPORIZINGTIME,
                                             STEEL_HEATCOEFFICIENT,
                                             builder ->
                                               builder
                                                 .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                   IArmorDurabilityCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                 .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                   IArmorAbsorptionRatioCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                 .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                   IArmorDefenceCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                 .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                   IArmorToughnessCapability.multiply((STEEL_MELTINGPOINT / IRON_MELTINGPOINT)))
                                           ).setRegistryName(AMN_STEEL);

        ModMaterials.Armor.Addon.GOLD = helper
                                          .createMedievalAddonArmorMaterial(TK_ARMOR_GOLD,
                                            References.Colors.Materials.CLR_GOLD.encodeColor(),
                                            "Gold",
                                            GOLD_MELTINGPOINT,
                                            GOLD_VAPORIZINGPOINT,
                                            GOLD_MELTINGTIME,
                                            GOLD_VAPORIZINGTIME,
                                            GOLD_HEATCOEFFICIENT,
                                            builder ->
                                              builder
                                                .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                  IArmorDurabilityCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                  IArmorAbsorptionRatioCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                  IArmorDefenceCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                  IArmorToughnessCapability.multiply((GOLD_MELTINGPOINT / IRON_MELTINGPOINT)))
                                          ).setRegistryName(AMN_GOLD);

        ModMaterials.Armor.Addon.OBSIDIAN = helper
                                              .createMedievalAddonArmorMaterial(TK_ARMOR_OBSIDIAN,
                                                References.Colors.Materials.CLR_OBSIDIAN.encodeColor(),
                                                "Obsidian",
                                                OBSIDIAN_MELTINGPOINT,
                                                OBSIDIAN_VAPORIZINGPOINT,
                                                OBSIDIAN_MELTINGTIME,
                                                OBSIDIAN_VAPORIZINGTIME,
                                                OBSIDIAN_HEATCOEFFICIENT,
                                                builder ->
                                                  builder
                                                    .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                      IArmorDurabilityCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                    .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                      IArmorAbsorptionRatioCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                    .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                      IArmorDefenceCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                    .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                      IArmorToughnessCapability.multiply((OBSIDIAN_MELTINGPOINT / IRON_MELTINGPOINT)))
                                              ).setRegistryName(AMN_OBSIDIAN);

        ModMaterials.Armor.Addon.HARDENED_IRON = helper
                                                   .createMedievalAddonArmorMaterial(TK_ARMOR_HARDENED_IRON,
                                                     References.Colors.Materials.CLR_HARDENED_IRON.encodeColor(),
                                                     "IronHardened",
                                                     HARDENED_IRON_MELTINGPOINT,
                                                     HARDENED_IRON_VAPORIZINGPOINT,
                                                     HARDENED_IRON_MELTINGTIME,
                                                     HARDENED_IRON_VAPORIZINGTIME,
                                                     HARDENED_IRON_HEATCOEFFICIENT,
                                                     builder ->
                                                       builder
                                                         .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY,
                                                           IArmorDurabilityCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                         .register(ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY,
                                                           IArmorAbsorptionRatioCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                         .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY,
                                                           IArmorDefenceCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                         .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY,
                                                           IArmorToughnessCapability.multiply((HARDENED_IRON_MELTINGPOINT / IRON_MELTINGPOINT)))
                                                   ).setRegistryName(AMN_HARDENED_IRON);

        registry.register(ModMaterials.Armor.Addon.IRON);
        registry.register(ModMaterials.Armor.Addon.GOLD);
        registry.register(ModMaterials.Armor.Addon.STEEL);
        registry.register(ModMaterials.Armor.Addon.OBSIDIAN);
        registry.register(ModMaterials.Armor.Addon.HARDENED_IRON);
    }

    @SubscribeEvent(priority = EventPriority.LOW)
    public static void handleAnvilMaterialRegistration(@Nonnull RegistryEvent.Register<IAnvilMaterial> anvilMaterialRegisterMaterialEvent)
    {
        IForgeRegistry<IAnvilMaterial> registry = anvilMaterialRegisterMaterialEvent.getRegistry();
        IMaterialConstructionHelper helper = IArmoryAPI.Holder.getInstance().getHelpers().getMaterialConstructionHelper();

        ModMaterials.Anvil.IRON = helper
                                    .createMedievalAnvilMaterial(TK_ANVIL_IRON, References.Colors.Materials.CLR_IRON.encodeColor(), "Iron",
                                      IRON_MELTINGPOINT, IRON_VAPORIZINGPOINT, IRON_MELTINGTIME, IRON_VAPORIZINGTIME, IRON_HEATCOEFFICIENT, DAN_IRON).setRegistryName(AN_IRON);

        ModMaterials.Anvil.OBSIDIAN = helper
                                        .createMedievalAnvilMaterial(TK_ANVIL_OBSIDIAN, References.Colors.Materials.CLR_OBSIDIAN.encodeColor(), "Obsidian",
                                          OBSIDIAN_MELTINGPOINT, OBSIDIAN_VAPORIZINGPOINT, OBSIDIAN_MELTINGTIME, OBSIDIAN_VAPORIZINGTIME, OBSIDIAN_HEATCOEFFICIENT, DAN_OBSIDIAN)
                                        .setRegistryName(AN_OBSIDIAN);

        ModMaterials.Anvil.STONE = helper
                                     .createMedievalAnvilMaterial(TK_ANVIL_STONE, References.Colors.Materials.CLR_STONE.encodeColor(), "Stone",
                                       OBSIDIAN_MELTINGPOINT, OBSIDIAN_VAPORIZINGPOINT, OBSIDIAN_MELTINGTIME, OBSIDIAN_VAPORIZINGTIME, OBSIDIAN_HEATCOEFFICIENT, DAN_STONE)
                                     .setRegistryName(AN_STONE);

        registry.register(ModMaterials.Anvil.IRON);
        registry.register(ModMaterials.Anvil.OBSIDIAN);
        registry.register(ModMaterials.Anvil.STONE);
    }

    @SubscribeEvent
    public static void handleMedievalArmorExtensionPositionRegistration(@Nonnull RegistryEvent.Register<IMultiComponentArmorExtensionPosition> extensionPositionRegisterEvent)
    {
        IForgeRegistry<IMultiComponentArmorExtensionPosition> extensionPositionRegistry = extensionPositionRegisterEvent.getRegistry();

        //Helmet
        POSITIONTOP = new MedievalArmorExtensionPosition(0)
                        .setRegistryName(PN_TOP);


        POSITIONLEFT = new MedievalArmorExtensionPosition(1)
                         .setRegistryName(PN_LEFT);


        POSITIONRIGHT = new MedievalArmorExtensionPosition(2)
                          .setRegistryName(PN_RIGHT);


        //ChestPlate
        POSITIONSHOULDERLEFT = new MedievalArmorExtensionPosition(0)
                                 .setRegistryName(PN_SHOULDERLEFT);

        POSITIONSHOULDERRIGHT = new MedievalArmorExtensionPosition(1)
                                  .setRegistryName(PN_SHOULDERRIGHT);

        POSITIONSTOMACHLEFT = new MedievalArmorExtensionPosition(2)
                                .setRegistryName(PN_STOMACHLEFT);

        POSITIONSTOMACHRIGHT = new MedievalArmorExtensionPosition(3)
                                 .setRegistryName(PN_STOMACHRIGHT);

        POSITIONBACKLEFT = new MedievalArmorExtensionPosition(4)
                             .setRegistryName(PN_BACKLEFT);

        POSITIONBACKRIGHT = new MedievalArmorExtensionPosition(5)
                              .setRegistryName(PN_BACKRIGHT);


        //Leggings
        POSITIONSHINLEFT = new MedievalArmorExtensionPosition(0)
                             .setRegistryName(PN_SHINLEFT);

        POSITIONSHINRIGHT = new MedievalArmorExtensionPosition(1)
                              .setRegistryName(PN_SHINRIGHT);

        POSITIONCALFLEFT = new MedievalArmorExtensionPosition(2)
                             .setRegistryName(PN_CALFLEFT);

        POSITIONCALFRIGHT = new MedievalArmorExtensionPosition(3)
                              .setRegistryName(PN_CALFRIGHT);

        //Shoes
        POSITIONLACESLEFT = new MedievalArmorExtensionPosition(0)
                              .setRegistryName(PN_LACESLEFT);

        POSITIONLACESRIGHT = new MedievalArmorExtensionPosition(1)
                               .setRegistryName(PN_LACESRIGHT);

        extensionPositionRegistry.register(POSITIONTOP);
        extensionPositionRegistry.register(POSITIONLEFT);
        extensionPositionRegistry.register(POSITIONRIGHT);

        extensionPositionRegistry.register(POSITIONSHOULDERLEFT);
        extensionPositionRegistry.register(POSITIONSHOULDERRIGHT);
        extensionPositionRegistry.register(POSITIONSTOMACHLEFT);
        extensionPositionRegistry.register(POSITIONSTOMACHRIGHT);
        extensionPositionRegistry.register(POSITIONBACKLEFT);
        extensionPositionRegistry.register(POSITIONBACKRIGHT);

        extensionPositionRegistry.register(POSITIONSHINLEFT);
        extensionPositionRegistry.register(POSITIONSHINRIGHT);
        extensionPositionRegistry.register(POSITIONCALFLEFT);
        extensionPositionRegistry.register(POSITIONCALFRIGHT);

        extensionPositionRegistry.register(POSITIONLACESLEFT);
        extensionPositionRegistry.register(POSITIONLACESRIGHT);
    }

    @SubscribeEvent
    public static void handleMedievalArmorExtensionRegistration(@Nonnull RegistryEvent.Register<IMultiComponentArmorExtension> extensionRegisterEvent)
    {
        handleMaterielIndependentExtensionRegistration(extensionRegisterEvent);
        handleMaterielDependentExtensionRegistration(extensionRegisterEvent);
    }

    private static void handleMaterielIndependentExtensionRegistration(@Nonnull RegistryEvent.Register<IMultiComponentArmorExtension> extensionRegisterEvent)
    {
        IForgeRegistry<IMultiComponentArmorExtension> extensionRegistry = extensionRegisterEvent.getRegistry();

        //Helmet
        TOP = new MedievalArmorExtension.Builder(TK_TOP,
          TextFormatting.RESET.toString(),
          POSITIONTOP,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_TOP))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_TOP))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_TOP)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);

                  return new AnvilRecipe()
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(10,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(14,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_HELMETTOP.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(2, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(8).setHammerUsage(5).setTongUsage(4)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_HELMETUPGRADETOP.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName().getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_TOP);

        LEFT = new MedievalArmorExtension.Builder(TK_LEFT,
          TextFormatting.RESET.toString(),
          POSITIONLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_LEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_LEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_LEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(23,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_HELMETLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(10, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(8).setHammerUsage(5).setTongUsage(4)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_HELMETUPGRADELEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                       .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_LEFT);

        RIGHT = new MedievalArmorExtension.Builder(TK_RIGHT,
          TextFormatting.RESET.toString(),
          POSITIONRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_RIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_RIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_RIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(21,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID, RN_HELMETRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(14, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(8).setHammerUsage(5).setTongUsage(4)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_HELMETUPGRADERIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                        .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_RIGHT);

        //ChestPlate
        SHOULDERLEFT = new MedievalArmorExtension.Builder(TK_SHOULDERLEFT,
          TextFormatting.RESET.toString(),
          POSITIONSHOULDERLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_SHOULDERLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_SHOULDERLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_SHOULDERLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(2,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(3,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(4,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(9,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATESHOULDERLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADESHOULDERLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                                   .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_SHOULDERLEFT);

        SHOULDERRIGHT = new MedievalArmorExtension.Builder(TK_SHOULDERRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONSHOULDERRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_SHOULDERRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_SHOULDERRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_SHOULDERRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(0,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(1,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(2,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(5,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATESHOULDERRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADESHOULDERRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                                    .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_SHOULDERRIGHT);

        STOMACHLEFT = new MedievalArmorExtension.Builder(TK_STOMACHLEFT,
          TextFormatting.RESET.toString(),
          POSITIONSTOMACHLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_STOMACHLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_STOMACHLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_STOMACHLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(5,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(10,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(15,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(21,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATESTOMACHLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADESTOMACHLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                                  .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_STOMACHLEFT);

        STOMACHRIGHT = new MedievalArmorExtension.Builder(TK_STOMACHRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONSTOMACHRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_STOMACHRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_STOMACHRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_STOMACHRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(9,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(14,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(19,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(23,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATESTOMACHRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADESTOMACHRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                                   .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_STOMACHRIGHT);

        BACKLEFT = new MedievalArmorExtension.Builder(TK_BACKLEFT,
          TextFormatting.RESET.toString(),
          POSITIONBACKLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_BACKLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_BACKLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_BACKLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(10,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(15,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(20,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEBACKLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADEBACKLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                               .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_BACKLEFT);

        BACKRIGHT = new MedievalArmorExtension.Builder(TK_BACKRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONBACKRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_BACKRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_BACKRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_BACKRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(14,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(19,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(24,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEBACKRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(8)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_CHESTPLATEUPGRADEBACKRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                                .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_BACKRIGHT);


        //Leggings
        SHINLEFT = new MedievalArmorExtension.Builder(TK_SHINLEFT,
          TextFormatting.RESET.toString(),
          POSITIONSHINLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_SHINLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_SHINLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_SHINLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(12,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSSHINLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(6)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSUPGRADESHINLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                             .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_SHINLEFT);

        SHINRIGHT = new MedievalArmorExtension.Builder(TK_SHINRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONSHINRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_SHINRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_SHINRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_SHINRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(12,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSSHINRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(6)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSUPGRADESHINRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                              .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_SHINRIGHT);

        CALFLEFT = new MedievalArmorExtension.Builder(TK_CALFLEFT,
          TextFormatting.RESET.toString(),
          POSITIONCALFLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_CALFLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_CALFLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_CALFLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(3,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(23,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSCALFLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(6)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSUPGRADECALFLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                             .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_CALFLEFT);

        CALFRIGHT = new MedievalArmorExtension.Builder(TK_CALFRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONCALFRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_CALFRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_CALFRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_CALFRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(1,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(21,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSCALFRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(17,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(12).setHammerUsage(7).setTongUsage(6)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_LEGGINGSUPGRADECALFRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                              .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_CALFRIGHT);


        //Shoes
        LACESLEFT = new MedievalArmorExtension.Builder(TK_LACESLEFT,
          TextFormatting.RESET.toString(),
          POSITIONLACESLEFT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_LACESLEFT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_LACESLEFT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_LACESLEFT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(6,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(15,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(16,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(20,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_SHOESLACESLEFT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(11,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(8).setHammerUsage(4).setTongUsage(5)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_SHOESUPGRADELACESLEFT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                           .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_LACESLEFT);

        LACESRIGHT = new MedievalArmorExtension.Builder(TK_LACESRIGHT,
          TextFormatting.RESET.toString(),
          POSITIONLACESRIGHT,
          builder ->
            builder
              .register(ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY, IArmorDurabilityCapability.add(DUR_LACESRIGHT))
              .register(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, IArmorDefenceCapability.add(DEF_LACESRIGHT))
              .register(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, IArmorToughnessCapability.add(TOU_LACESRIGHT)),
          new IExtensionRecipeRetrievalCallback()
          {
              @Nullable
              @Override
              public IAnvilRecipe getCreationRecipe(IMultiComponentArmorExtension extension)
              {
                  if (!(extension instanceof IMaterialDependantMultiComponentArmorExtension))
                  {
                      return null;
                  }

                  IMaterialDependantMultiComponentArmorExtension materialDependantMultiComponentArmorExtension = (IMaterialDependantMultiComponentArmorExtension) extension;
                  IMaterial material = materialDependantMultiComponentArmorExtension.getMaterial();

                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, materialDependantMultiComponentArmorExtension);


                  return new AnvilRecipe()
                           .setCraftingSlotContent(8,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(18,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(19,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(24,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.NUGGET,
                               ModHeatableObjects.ITEMSTACK,
                               material,
                               material.getMeltingPoint() * 0.5F * 0.85F,
                               material.getMeltingPoint() * 0.5F * 0.95F))
                           .setResult(upgradeStack)
                           .setHammerUsage(Math.round(material.getMeltingPoint()) / 300)
                           .setTongUsage((int) (material.getMeltingPoint() - 1000) / 300)
                           .setProgress(Math.round(material.getMeltingPoint()) / 100)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_SHOESLACESRIGHT.getResourcePath() + "-" + material.getRegistryName().getResourcePath()));
              }

              @Nullable
              @Override
              public IAnvilRecipe getAttachingRecipe(IMultiComponentArmorExtension extension, IMultiComponentArmor armor, ICoreArmorMaterial coreMaterial)
              {
                  ItemStack upgradeStack = CapabilityHelper.generateArmorComponentStack(ModItems.IT_COMPONENT, extension);

                  return new ArmorUpgradeAnvilRecipe(armor, coreMaterial)
                           .setCraftingSlotContent(13,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setCraftingSlotContent(7,
                             new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.RING,
                               ModHeatableObjects.ITEMSTACK,
                               coreMaterial,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.85F,
                               coreMaterial.getMeltingPoint() * 0.5F * 0.95F))
                           .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(upgradeStack))
                           .setProgress(8).setHammerUsage(4).setTongUsage(5)
                           .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                             RN_SHOESUPGRADELACESRIGHT.getResourcePath() + "-" + coreMaterial.getRegistryName().getResourcePath() + "-" + extension.getRegistryName()
                                                                                                                                            .getResourcePath()));
              }
          }
        ).build().setRegistryName(UN_LACESRIGHT);

        extensionRegistry.register(TOP);
        extensionRegistry.register(LEFT);
        extensionRegistry.register(RIGHT);
        extensionRegistry.register(SHOULDERLEFT);
        extensionRegistry.register(SHOULDERRIGHT);
        extensionRegistry.register(STOMACHLEFT);
        extensionRegistry.register(STOMACHRIGHT);
        extensionRegistry.register(BACKLEFT);
        extensionRegistry.register(BACKRIGHT);
        extensionRegistry.register(SHINLEFT);
        extensionRegistry.register(SHINRIGHT);
        extensionRegistry.register(CALFLEFT);
        extensionRegistry.register(CALFRIGHT);
        extensionRegistry.register(LACESLEFT);
        extensionRegistry.register(LACESRIGHT);

        new RegisterMaterialDependentCoreExtensionEvent(extensionRegistry).PostCommon();
    }

    private static void handleMaterielDependentExtensionRegistration(@Nonnull RegistryEvent.Register<IMultiComponentArmorExtension> extensionRegisterEvent)
    {
        IForgeRegistry<IMultiComponentArmorExtension> extensionRegistry = extensionRegisterEvent.getRegistry();

        List<IMultiComponentArmorExtension> materializedExtensions = new ArrayList<>();

        for (IAddonArmorMaterial material : IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry())
        {
            for (IMultiComponentArmorExtension extension : IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry())
            {
                if (!(extension instanceof IMaterializableMultiComponentArmorExtension))
                {
                    continue;
                }

                final IMaterializableMultiComponentArmorExtension materializableMultiComponentArmorExtension = (IMaterializableMultiComponentArmorExtension) extension;

                materializedExtensions.add(
                  new MedievalMaterialDependantArmorExtension.Builder(materializableMultiComponentArmorExtension.getTranslationKey(),
                    materializableMultiComponentArmorExtension.getTextFormatting(),
                    materializableMultiComponentArmorExtension.getPosition(),
                    materializableMultiComponentArmorExtension,
                    material,
                    materializableMultiComponentArmorExtension::cloneCapabilities)
                    .build()
                    .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                      extension.getRegistryName().getResourcePath() + "-" + material.getRegistryName().getResourcePath())));
            }
        }

        extensionRegistry.registerAll(materializedExtensions.toArray(new IMultiComponentArmorExtension[0]));
    }

    @SubscribeEvent
    public static void handleFluidFluidToFluidMixingRecipeRegistration(@Nonnull RegistryEvent.Register<IMoltenMetalMixingRecipe> fluidToFluidMixingRecipeRegisterEvent)
    {
        IForgeRegistry<IMoltenMetalMixingRecipe> registry = fluidToFluidMixingRecipeRegisterEvent.getRegistry();

        registry.register(new MoltenMetalMixingRecipe()
                            .setLeftInputData(ModMaterials.Armor.Core.IRON.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setRightInputData(ModMaterials.Armor.Core.OBSIDIAN.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setExamplaryOutputData(ModMaterials.Armor.Core.HARDENED_IRON.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setProcessingTime(125)
                            .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                              ModMaterials.Armor.Core.HARDENED_IRON.getOreDictionaryIdentifier() + "-" + ModMaterials.Armor.Core.IRON.getOreDictionaryIdentifier() + "-"
                                + ModMaterials.Armor.Core.OBSIDIAN.getOreDictionaryIdentifier())));

        registry.register(new MoltenMetalMixingRecipe()
                            .setLeftInputData(ModMaterials.Armor.Core.OBSIDIAN.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setRightInputData(ModMaterials.Armor.Core.IRON.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setExamplaryOutputData(ModMaterials.Armor.Core.HARDENED_IRON.getOreDictionaryIdentifier(), References.General.FLUID_INGOT)
                            .setProcessingTime(125)
                            .setRegistryName(new ResourceLocation(References.General.MOD_ID,
                              ModMaterials.Armor.Core.HARDENED_IRON.getOreDictionaryIdentifier() + "-" + ModMaterials.Armor.Core.OBSIDIAN.getOreDictionaryIdentifier() + "-"
                                + ModMaterials.Armor.Core.IRON.getOreDictionaryIdentifier())));
    }
}
