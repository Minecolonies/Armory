package com.ldtteam.armory.compatibility.recipes.heated;

import com.google.common.collect.Maps;
import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.common.material.core.IMaterial;
import com.ldtteam.armory.api.common.material.core.RegistryMaterialWrapper;
import com.ldtteam.armory.api.util.references.ModHeatableObjects;
import com.ldtteam.armory.api.util.references.ModHeatedObjectTypes;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class HeatedItemRecipeMaker
{

    private HeatedItemRecipeMaker()
    {
        throw new IllegalStateException("Tried to initialize: HeatedItemRecipeMaker but this is a Utility class.");
    }

    public static List<HeatedItemRecipe> generateRecipes()
    {
        final Map<IHeatedObjectType, Map<String, List<ItemStack>>> typeOreDicMaterialStackMap = Maps.newHashMap();

        for (final IHeatedObjectType type :
          IArmoryAPI.Holder.getInstance().getRegistryManager().getHeatableObjectTypeRegistry())
        {
            typeOreDicMaterialStackMap.putIfAbsent(type, Maps.newHashMap());

            for (final RegistryMaterialWrapper materialWrapper:
              IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry())
            {
                if (typeOreDicMaterialStackMap.get(type).containsKey(materialWrapper.getWrapped().getOreDictionaryIdentifier()))
                    continue;

                typeOreDicMaterialStackMap.get(type).putIfAbsent(materialWrapper.getWrapped().getOreDictionaryIdentifier(), OreDictionary.getOres(type.getOreDictionaryPrefix() + materialWrapper.getWrapped().getOreDictionaryIdentifier()));
            }
        }

        return typeOreDicMaterialStackMap
          .entrySet()
          .stream()
          .flatMap(iHeatedObjectTypeMapEntry -> iHeatedObjectTypeMapEntry.getValue().entrySet().stream().map(Map.Entry::getValue).flatMap(List::stream).filter(IArmoryAPI.Holder.getInstance().getHelpers().getHeatableOverrideManager()::isHeatable).map(HeatedItemRecipe::new)).collect(Collectors.toList());
    }
}
