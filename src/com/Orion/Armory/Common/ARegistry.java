package com.Orion.Armory.Common;

import java.util.*;

import com.Orion.Armory.Common.Armor.ArmorCore;

import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.Orion.Armory.Client.CreativeTab.ArmorTab;

/**
 * Created by Orion on 26-3-2014
 * Based off: TConstructRegistry
 */
public class ARegistry
{
    //instance and logger
    public static ARegistry instance = new ARegistry();
    public static Logger logger = LogManager.getLogger("Armory");

    // Tabs for the creative inventory
    public static ArmorTab tabArmoryArmor;

    //Values for storing all the different parts and materials
    public HashMap<String, boolean[]> armorMaterials = new HashMap<String, boolean[]>();
    public String[] armorUpgrades = {"topHead", "earProtection", "shoulderPads", "bodyProtection", "backProtection", "frontLegProtection", "backLegProtection", "shoeProtection"};
    public String[] armorModifiers = {"helmetAquaAffinity", "helmetAquaBreathing", "helmetNightSight", "helmetThorns", "helmetAutoRepair", "helmetReinforced", "helmetElectric",
            "chestplateStrength", "chestplateHaste", "chestplateFlying", "chestplateThorns","chestplateAutoRepair", "chestplateReinforced", "chestplateElectric",
            "leggingsSpeed", "leggingsJumpAssist", "leggingsUpHillAssist", "leggingsThorns", "leggingsAutoRepair", "leggingsReinforced", "leggingsElectric",
            "shoesFallAssist", "shoesSwimAssist", "shoesAutoRepair", "shoesReinforced", "shoesElectric"};

    //Arraylist for storing all the basic tool mappings
    ArrayList<ArmorCore> armorMappings = new ArrayList<ArmorCore>();

    //ArrayList for storing all the modifiers and upgrades
    ArrayList<ArmorUpgrade> armorUpgrades = new ArrayList<ArmorUpgrade>();
    ArrayList<ArmorModifier> armorModifiers = new ArrayList<ArmorModifier>();

    public ARegistry()
    {
        this.initializeMaterials();
    }

    protected void initializeMaterials()
    {
        this.registerMaterial("Iron", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Steel", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Alumite", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Bronze", new boolean[]{true, true, true, true, true, true, true, true, true});
        this.registerMaterial("Ardite", new boolean[]{false, true, true, true, true, true, true, true, true});
        this.registerMaterial("Cobalt", new boolean[]{false, true, true, false, true, true, true, true, false});
        this.registerMaterial("Obsidian", new boolean[]{false, false, true, true, false, false, false, false, false});
        this.registerMaterial("Manyullyn", new boolean[]{false, true, false, false, true, true, true, true, false});
    }


    public ArrayList<ArmorCore> getAllArmorMappings()
    {
        return armorMappings;
    }

    public void addArmorMapping(ArmorCore pCore)
    {
        armorMappings.add(pCore);
    }

    public HashMap<String, boolean[]> getArmorMaterials()
    {
        return armorMaterials;
    }

    public void registerMaterial(String pMaterialName, boolean[] activeParts)
    {
        armorMaterials.put(pMaterialName, activeParts);
    }

    public int getMaterialID(String pMaterialName)
    {
        Iterator iter = armorMaterials.entrySet().iterator();
        int tMaterialID = -1;

        while (iter.hasNext())
        {
            tMaterialID++;
            Map.Entry<String, boolean[]> tCurrentMaterial = (Map.Entry<String, boolean[]>) iter.next();

            if (tCurrentMaterial.getKey() == pMaterialName)
            {
                return tMaterialID;
            }
        }

        return -1;
    }

    public void registerModifier(ArmorModifier pModifier)
    {
        armorModifiers.add(pModifier);
    }

    public int getUpgradeTextureID(String pMaterialName, String pUpgradeName)
    {
        int tUpgradeID = Arrays.asList(armorUpgrades).indexOf(pUpgradeName)+1;
        int tMaterialID = getMaterialID(pMaterialName);

        if ((tUpgradeID == -1) || (tMaterialID == -1)) {
            return -1;
        }

        return ((tMaterialID) * this.armorUpgrades.length + tUpgradeID);
    }

    public int getModifierTextureID(String pMaterialName, String pModifierName)
    {
        int tModifierID = Arrays.asList(armorModifiers).indexOf(pModifierName)+1;
        int tMaterialID = getMaterialID(pMaterialName);

        if ((tModifierID == -1) || (tMaterialID == -1)) {
            return -1;
        }

        return ((tMaterialID)*this.armorModifiers.length + tModifierID);
    }


}
