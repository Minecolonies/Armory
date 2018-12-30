package com.ldtteam.armory.common.registries;

import com.ldtteam.armory.api.common.armor.IMultiComponentArmor;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtension;
import com.ldtteam.armory.api.common.armor.IMultiComponentArmorExtensionPosition;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.crafting.mixing.IMoltenMetalMixingRecipe;
import com.ldtteam.armory.api.common.heatable.IHeatableObject;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.common.material.armor.IAddonArmorMaterial;
import com.ldtteam.armory.api.common.material.armor.ICoreArmorMaterial;
import com.ldtteam.armory.api.common.material.core.RegistryMaterialWrapper;
import com.ldtteam.armory.api.util.references.ModRegistries;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * Created by marcf on 1/14/2017.
 */
public class CommonRegistryInitializer {

    public void initialize() {
        RegistryManager.getInstance().coreArmorMaterialIForgeRegistry  = new RegistryBuilder<ICoreArmorMaterial>()
                .setName(ModRegistries.COREARMORMATERIALS)
                .setType(ICoreArmorMaterial.class)
                .create();

        RegistryManager.getInstance().addonArmorMaterialIForgeRegistry = new RegistryBuilder<IAddonArmorMaterial>()
                .setName(ModRegistries.ADDONARMORMATERIALS)
                .setType(IAddonArmorMaterial.class)
                .create();

        RegistryManager.getInstance().anvilMaterialIForgeRegistry = new RegistryBuilder<IAnvilMaterial>()
                .setName(ModRegistries.ANVILMATERIAL)
                .setType(IAnvilMaterial.class)
                .create();

        RegistryManager.getInstance().combinedMaterialRegistry = new RegistryBuilder<RegistryMaterialWrapper>()
                .setName(ModRegistries.COMBINEDMATERIAL)
                .setType(RegistryMaterialWrapper.class)
                .create();

        RegistryManager.getInstance().multiComponentArmorRegistry = new RegistryBuilder<IMultiComponentArmor>()
                .setName(ModRegistries.MULTICOMPONENTARMOR)
                .setType(IMultiComponentArmor.class)
                .create();

        RegistryManager.getInstance().multiComponentArmorExtensionPositionRegistry = new RegistryBuilder<IMultiComponentArmorExtensionPosition>()
                .setName(ModRegistries.MULTICOMPONENTARMOREXTENSIONPOSITION)
                .setType(IMultiComponentArmorExtensionPosition.class)
                .create();

        RegistryManager.getInstance().multiComponentArmorExtensionRegistry = new RegistryBuilder<IMultiComponentArmorExtension>()
                .setName(ModRegistries.MULTICOMPONENTARMOREXTENSION)
                .setType(IMultiComponentArmorExtension.class)
                .create();

        RegistryManager.getInstance().heatableObjectRegistry = new RegistryBuilder<IHeatableObject>()
                .setName(ModRegistries.HEATABLEOBJECT)
                .setType(IHeatableObject.class)
                .create();

        RegistryManager.getInstance().heatableObjectTypeRegistry = new RegistryBuilder<IHeatedObjectType>()
                .setName(ModRegistries.HEATABLEOJBECTTYPE)
                .setType(IHeatedObjectType.class)
                .create();

        RegistryManager.getInstance().anvilRecipeRegistry = new RegistryBuilder<IAnvilRecipe>()
                .setName(ModRegistries.ANVILRECIPE)
                .setType(IAnvilRecipe.class)
                .allowModification()
                .create();

        RegistryManager.getInstance().moltenMetalMixingRecipesRegistry = new RegistryBuilder<IMoltenMetalMixingRecipe>()
                .setName(ModRegistries.FLUIDTOFLUIDMIXING)
                .setType(IMoltenMetalMixingRecipe.class)
                .create();
    }
}
