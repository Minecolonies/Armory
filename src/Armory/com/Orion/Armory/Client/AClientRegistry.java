package com.Orion.Armory.Client;
/*
*   AClientRegistry
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;

public class AClientRegistry extends ARegistry
{
    public static void registerRenderMappings()
    {
        iLogger.info("Counted " + iInstance.getAllArmorMappings().size() + " types of Armor.");
        iLogger.info("Counted " + iInstance.getArmorMaterials().size() + " types of Materials.");
        iLogger.info("Counted " + iInstance.getUpgrades().size() + " types of Upgrades.");
        iLogger.info("Counted " + iInstance.getModifiers().size() + " types of Modifiers.");

        iLogger.info("Total amount of rescources: " + (iInstance.getAllArmorMappings().size() * (iInstance.getArmorMaterials().size() + iInstance.getUpgrades().size() + iInstance.getModifiers().size())));

        for(ArmorCore tArmor: iInstance.getAllArmorMappings())
        {
            for(ArmorMaterial tMaterial : iInstance.getArmorMaterials())
            {
                tArmor.registerResource(tMaterial.getResource(tArmor.iArmorPart));
            }

            for(ArmorUpgrade tUpgrade : iInstance.getUpgrades())
            {
                tArmor.registerResource(tUpgrade.getResource());
            }

            for(ArmorModifier tModifier: iInstance.getModifiers())
            {
                tArmor.registerResource(tModifier.getResource());
            }
        }
    }

}