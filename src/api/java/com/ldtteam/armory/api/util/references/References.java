/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.ldtteam.armory.api.util.references;
/*
*   References
*   Created by: Orion
*   Created on: 27-6-2014
*/

import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import net.minecraft.util.ResourceLocation;

public class References {
    public static final class GuiIDs {
        public static int FORGEID = 0;
        public static int FIREPLACEID = 1;
        public static int ANVILID = 2;
        public static int MOLTENMETALMIXER = 3;
        public static int SMITHINGSGUIDE = 4;
    }

    public static final class General {
        public static final String MOD_ID = "armory";
        public static final String VERSION = "@version@";
        public static final String MC_VERSION = "@mcversion@";
        public static final String API_VERSION = "@apiversion@";

        public static final int FLUID_INGOT = 144;
    }

    public static final class OreDictionaryIdentifiers {
        public static final String ODI_INGOT = "ingot";
        public static final String ODI_BLOCK = "block";
        public static final String ODI_RING = "ring";
        public static final String ODI_NUGGET = "nugget";
        public static final String ODI_PLATE = "plate";
        public static final String ODI_CHAIN = "chain";
    }

    public static final class InternalNames {
        public static final class Armor {
            public static final String MEDIEVALHELMET = "armory.helmet.medieval";
            public static final String MEDIEVALCHESTPLATE = "armory.chestplate.medieval";
            public static final String MEDIEVALLEGGINGS = "armory.leggings.medieval";
            public static final String MEDIEVALSHOES = "armory.shoes.medieval";
        }

        public static final class Materials {
            public static final class Anvil {
                public static final ResourceLocation AN_STONE = new ResourceLocation(General.MOD_ID ,"stone.anvil");
                public static final ResourceLocation AN_IRON = new ResourceLocation(General.MOD_ID ,"iron.anvil");
                public static final ResourceLocation AN_OBSIDIAN = new ResourceLocation(General.MOD_ID ,"obsidian.anvil");
            }

            public static final class Core {
                public static final ResourceLocation CMN_IRON = new ResourceLocation(General.MOD_ID , "iron.core");
                public static final ResourceLocation CMN_OBSIDIAN = new ResourceLocation(General.MOD_ID , "obsidian.core");
                public static final ResourceLocation CMN_GOLD = new ResourceLocation(General.MOD_ID , "gold.core");
                public static final ResourceLocation CMN_STEEL = new ResourceLocation(General.MOD_ID , "steel.core");
                public static final ResourceLocation CMN_HARDENED_IRON = new ResourceLocation(General.MOD_ID, "iron.hardened.core");
            }

            public static final class Addon {
                public static final ResourceLocation AMN_IRON = new ResourceLocation(General.MOD_ID , "iron.addon");
                public static final ResourceLocation AMN_OBSIDIAN = new ResourceLocation(General.MOD_ID , "obsidian.addon");
                public static final ResourceLocation AMN_GOLD = new ResourceLocation(General.MOD_ID , "gold.addon");
                public static final ResourceLocation AMN_STEEL = new ResourceLocation(General.MOD_ID , "steel.addon");
                public static final ResourceLocation AMN_HARDENED_IRON = new ResourceLocation(General.MOD_ID, "iron.hardened.addon");
            }
        }

        public static final class Fluids {
            public static final String MOLTENMETAL = "armory.fluids.molten";
        }

        public static final class AddonPositions {

            public static final class Helmet {
                public static final ResourceLocation PN_TOP = new ResourceLocation(General.MOD_ID, "top");
                public static final ResourceLocation PN_LEFT = new ResourceLocation(General.MOD_ID,  "left");
                public static final ResourceLocation PN_RIGHT = new ResourceLocation(General.MOD_ID,  "right");
                public static final ResourceLocation PN_AQUABREATHING = new ResourceLocation(General.MOD_ID,  "aquabreathing");
                public static final ResourceLocation PN_NIGHTSIGHT = new ResourceLocation(General.MOD_ID,  "nightsight");
                public static final ResourceLocation PN_THORNS =  new ResourceLocation(General.MOD_ID, "thorns.helmet");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "autorepair.helmet");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "reinforced.helmet");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "electric.helmet");
            }

            public static final class Chestplate {
                public static final ResourceLocation PN_SHOULDERLEFT = new ResourceLocation(General.MOD_ID,  "shoulderleft");
                public static final ResourceLocation PN_SHOULDERRIGHT = new ResourceLocation(General.MOD_ID,  "shoulderright");
                public static final ResourceLocation PN_STOMACHLEFT = new ResourceLocation(General.MOD_ID,  "stomachleft");
                public static final ResourceLocation PN_STOMACHRIGHT =  new ResourceLocation(General.MOD_ID, "stomachright");
                public static final ResourceLocation PN_BACKLEFT = new ResourceLocation(General.MOD_ID,  "backleft");
                public static final ResourceLocation PN_BACKRIGHT = new ResourceLocation(General.MOD_ID,  "backright");
                public static final ResourceLocation PN_STRENGTH = new ResourceLocation(General.MOD_ID,  "strength");
                public static final ResourceLocation PN_HASTE = new ResourceLocation(General.MOD_ID,  "haste");
                public static final ResourceLocation PN_FLYING = new ResourceLocation(General.MOD_ID,  "flying");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID,  "thorns.chestplate");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "autorepair.chestplate");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "reinforced.chestplate");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "electric.chestplate");
            }

            public static final class Leggings {
                public static final ResourceLocation PN_SHINLEFT = new ResourceLocation(General.MOD_ID,  "shinleft");
                public static final ResourceLocation PN_SHINRIGHT = new ResourceLocation(General.MOD_ID,  "shinright");
                public static final ResourceLocation PN_CALFLEFT = new ResourceLocation(General.MOD_ID,  "calfleft");
                public static final ResourceLocation PN_CALFRIGHT = new ResourceLocation(General.MOD_ID,  "calfright");
                public static final ResourceLocation PN_SPEED = new ResourceLocation(General.MOD_ID,  "speed");
                public static final ResourceLocation PN_JUMPASSIST = new ResourceLocation(General.MOD_ID,  "jumpassist");
                public static final ResourceLocation PN_UPHILLASSIST = new ResourceLocation(General.MOD_ID,  "uphillassist");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID,  "thorns.leggings");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "autorepair.leggings");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "reinforced.leggings");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "electric.leggings");
            }

            public static final class Shoes {
                public static final ResourceLocation PN_LACESLEFT = new ResourceLocation(General.MOD_ID,  "lacesleft");
                public static final ResourceLocation PN_LACESRIGHT = new ResourceLocation(General.MOD_ID,  "lacesright");
                public static final ResourceLocation PN_FALLASSIST = new ResourceLocation(General.MOD_ID,  "fallassist");
                public static final ResourceLocation PN_SWIMASSIST = new ResourceLocation(General.MOD_ID,  "swimassist");
                public static final ResourceLocation PN_THORNS = new ResourceLocation(General.MOD_ID, "thorns.shoes");
                public static final ResourceLocation PN_AUTOREPAIR = new ResourceLocation(General.MOD_ID,  "autorepair.shoes");
                public static final ResourceLocation PN_REINFORCED = new ResourceLocation(General.MOD_ID,  "reinforced.shoes");
                public static final ResourceLocation PN_ELECTRIC = new ResourceLocation(General.MOD_ID,  "electric.shoes");
            }
        }

        public static final class Upgrades {
            public static final class Helmet {
                public static final ResourceLocation UN_TOP =  new ResourceLocation(General.MOD_ID,  "top");
                public static final ResourceLocation UN_LEFT = new ResourceLocation(General.MOD_ID,  "left");
                public static final ResourceLocation UN_RIGHT = new ResourceLocation(General.MOD_ID,  "right");
            }

            public static final class Chestplate {
                public static final ResourceLocation UN_SHOULDERLEFT = new ResourceLocation(General.MOD_ID,  "shoulderleft");
                public static final ResourceLocation UN_SHOULDERRIGHT = new ResourceLocation(General.MOD_ID,  "shoulderright");
                public static final ResourceLocation UN_STOMACHLEFT = new ResourceLocation(General.MOD_ID,  "stomachleft");
                public static final ResourceLocation UN_STOMACHRIGHT = new ResourceLocation(General.MOD_ID,  "stomachright");
                public static final ResourceLocation UN_BACKLEFT = new ResourceLocation(General.MOD_ID,  "backleft");
                public static final ResourceLocation UN_BACKRIGHT = new ResourceLocation(General.MOD_ID,  "backright");
            }

            public static final class Leggings {
            public static final ResourceLocation UN_SHINLEFT = new ResourceLocation(General.MOD_ID,  "shinleft");
            public static final ResourceLocation UN_SHINRIGHT = new ResourceLocation(General.MOD_ID,  "shinright");
            public static final ResourceLocation UN_CALFLEFT = new ResourceLocation(General.MOD_ID,  "calfleft");
            public static final ResourceLocation UN_CALFRIGHT = new ResourceLocation(General.MOD_ID,  "calfright");
            }

            public static final class Shoes {
                public static final ResourceLocation UN_LACESLEFT = new ResourceLocation(General.MOD_ID,  "lacesleft");
                public static final ResourceLocation UN_LACESRIGHT = new ResourceLocation(General.MOD_ID,  "lacesright");
            }
        }

        public static final class Modifiers {
            public static final class Helmet {
                public static final String AQUABREATHING = "armory.aquabreathinghelmet";
                public static final String NIGHTSIGHT = "armory.nightsighthelmet";
                public static final String THORNS = "armory.thornshelmet";
                public static final String AUTOREPAIR = "armory.autorepairhelmet";
                public static final String REINFORCED = "armory.reinforcedhelmet";
                public static final String ELECTRIC = "armory.electrichelmet";
            }

            public static final class Chestplate {
                public static final String STRENGTH = "armory.strengthchestplate";
                public static final String HASTE = "armory.hastechestplate";
                public static final String FLYING = "armory.flyingchestplate";
                public static final String THORNS = "armory.thornschestplate";
                public static final String AUTOREPAIR = "armory.autorepairchestplate";
                public static final String REINFORCED = "armory.reinforcedchestplate";
                public static final String ELECTRIC = "armory.electricchestplate";
            }

            public static final class Leggings
            {
                public static final String SPEED = "armory.speedleggings";
                public static final String JUMPASSIST = "armory.jumpassistleggings";
                public static final String UPHILLASSIST = "armory.uphillassistleggings";
                public static final String THORNS = "armory.thornsleggings";
                public static final String AUTOREPAIR = "armory.autorepairleggings";
                public static final String REINFORCED = "armory.reinforcedleggings";
                public static final String ELECTRIC = "armory.electricleggings";
            }

            public static final class Shoes {
                public static final String FALLASSIST = "armory.fallassistshoes";
                public static final String SWIMASSIST = "armory.swimassistshoes";
                public static final String AUTOREPAIR = "armory.autorepairshoes";
                public static final String REINFORCED = "armory.reinforcedshoes";
                public static final String ELECTRIC = "armory.electricshoes";
            }
        }

        public static final class Tiers {
            public static final String MEDIEVAL = "amrory.tiers.armor";
            public static final String PLATED = "armory.tiers.plated";
            public static final String QUANTUM = "armory.tiers.quantum";
        }

        public static final class Items {
            public static final String IN_METALRING = "armory.items.components.metalring";
            public static final String IN_METALCHAIN = "armory.items.components.metalchain";
            public static final String IN_METALINGOT = "armory.items.components.metalingot";
            public static final String IN_METALNUGGET = "armory.items.components.metalnugget";
            public static final String IN_METALPLATE = "armory.items.components.metalplate";
            public static final String IN_HEATEDINGOT = "armory.items.components.heatedingots";
            public static final String IN_HAMMER = "armory.items.itemhammer";
            public static final String IN_TONGS = "armory.items.tongs";
            public static final String IN_GUIDE = "armory.items.smithingsguide";
            public static final String IN_ARMOR_COMPONENT = "armory.items.armorcomponent";

            public static final class Armor {
                public static final ResourceLocation IN_HELMET = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.helmet");
                public static final ResourceLocation IN_CHESTPLATE = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.chestplate");
                public static final ResourceLocation IN_LEGGINGS = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.leggings");
                public static final ResourceLocation IN_SHOES = new ResourceLocation(General.MOD_ID, "armory.items.armor.medieval.shoes");
            }

        }

        public static final class Blocks {
            public static final String Forge = "armory.blocks.forge";
            public static final String Fireplace = "armory.blocks.fireplace";
            public static final String ArmorsAnvil = "armory.blocks.anvil";
            public static final String Conduit = "armory.blocks.conduit";
            public static final String Tank = "armory.blocks.tank";
            public static final String ConduitPump = "armory.blocks.pump";
            public static final String RedstonePumpGenerator = "armory.blocks.redstonegenerator";
            public static final String Resource = "armory.blocks.resource";
            public static final String MoltenMetalMixer = "armory.blocks.moltenmetalmixer";
        }

        public static final class TileEntities {
            public static final String ForgeContainer = "container.armory.forge";
            public static final String FireplaceContainer = "container.armory.fireplace";
            public static final String ArmorsAnvil = "container.armory.anvil";
            public static final String Conduit = "container.armory.conduit";
            public static final String Tank = "container.armory.tank";
            public static final String Pump = "container.armory.pump";
            public static final String MoltenMetalMixer = "container.armory.moltenmetalmixer";

            public static final class Structures {
                public static final String Forge = "structures.armory.forge";
            }
        }

        public static final class HeatedObjectTypeNames {
            public static final ResourceLocation HTN_INGOT = new ResourceLocation(General.MOD_ID.toLowerCase(), "ingot");
            public static final ResourceLocation HTN_RING = new ResourceLocation(General.MOD_ID.toLowerCase(), "ring");
            public static final ResourceLocation HTN_CHAIN = new ResourceLocation(General.MOD_ID.toLowerCase(), "chain");
            public static final ResourceLocation HTN_NUGGET = new ResourceLocation(General.MOD_ID.toLowerCase(), "nugget");
            public static final ResourceLocation HTN_PLATE = new ResourceLocation(General.MOD_ID.toLowerCase(), "plate");
            public static final ResourceLocation HTN_BLOCK = new ResourceLocation(General.MOD_ID.toLowerCase(), "block");
        }
        
        public static final class HeatableObjectNames {
            public static final ResourceLocation HON_ITEMSTACK = new ResourceLocation(General.MOD_ID, "itemstack");
            //TODO: Create Others.
        }
        
        public static final class InitializationComponents {
            public static final class Common {
                public static final ResourceLocation COMMAND = new ResourceLocation(General.MOD_ID, "common.command");
                public static final ResourceLocation CONFIG = new ResourceLocation(General.MOD_ID, "common.config");
                public static final ResourceLocation EVENTHANDLER = new ResourceLocation(General.MOD_ID, "common.eventhandler");
                public static final ResourceLocation MEDIEVAL = new ResourceLocation(General.MOD_ID, "common.medieval");
                public static final ResourceLocation STRUCTURE = new ResourceLocation(General.MOD_ID, "common.structure");
                public static final ResourceLocation SYSTEM = new ResourceLocation(General.MOD_ID, "common.system");
            }

            public static final class Client {
                public static final ResourceLocation MODELLOADER = new ResourceLocation(General.MOD_ID, "client.modelloader");
                public static final ResourceLocation EVENTHANDLER = new ResourceLocation(General.MOD_ID, "client.eventhandler");
                public static final ResourceLocation STRUCTURE = new ResourceLocation(General.MOD_ID, "client.structure");
                public static final ResourceLocation MEDIEVAL = new ResourceLocation(General.MOD_ID, "client.medieval");
                public static final ResourceLocation SYSTEM = new ResourceLocation(General.MOD_ID, "client.system");
            }
        }

        public static final class GUIComponents {
            public static final String TAB = "armory.gui.base.tab.";

            public static final class Forge {
                public static final String BACKGROUND = "gui.forge.background";
                public static final String INVENTORY = "gui.forge.inventory.player";
                public static final String SLOT = "gui.forge.inventory.slots.";

                public static final String FLAMEONE = "gui.forge.inventory.flame1";
                public static final String FLAMETWO = "gui.forge.inventory.flame2";
                public static final String FLAMETHREE = "gui.forge.inventory.flame3";
                public static final String FLAMEFOUR = "gui.forge.inventory.flame4";
                public static final String FLAMEFIVE = "gui.forge.inventory.flame5";

                public static final String MELT = "gui.forge.inventory.melt";
                
                public static final String PROGRESSSOLIDIFYING = "gui.forge.moltenmetals.solidifyingprogress";
            }

            public static final class Fireplace {
                public static final String BACKGROUND = "gui.fireplace.background";
                public static final String INVENTORY = "gui.fireplace.inventory.player";
                public static final String SLOT = "gui.fireplace.inventory.slots.";
                public static final String FLAMEONE = "gui.fireplace.inventory.flame1";
                public static final String FLAMETWO = "gui.fireplace.inventory.flame2";
                public static final String FLAMETHREE = "gui.fireplace.inventory.flame3";

                public static final String COOKINGPROGRESS = "gui.fireplace.cooking.progress";
            }
            
            public static final class Anvil {
                public static final String BACKGROUND = "gui.anvil.background";
                public static final String PLAYERINVENTORY = "gui.anvil.player";
                public static final String EXTENDEDCRAFTING = "gui.anvil.extendedcrafting";
                public static final String EXPERIENCELABEL = "gui.anvil.label.experience";
                public static final String TOOLSLOTBORDER = "gui.anvil.tools.border";
                public static final String HAMMERSLOT = "gui.anvil.tools.slot.hammer";
                public static final String TONGSLOT = "gui.anvil.tools.slot.tongs";
                public static final String LOGO = "gui.anvil.logo";
                public static final String TEXTBOXBORDER = "gui,anvil.name.border";
                public static final String TEXTBOX = "gui.anvil.name.textbox";
            }
            
            public static class MoltenMetalMixer {
                public static final String CPN_BACKGROUND = "gui.moltenmetalmixer.background";
                public static final String CPN_INVENTORY = "gui.moltenmetalmixer.inventory.player";
                
                public static final String CPN_MOLTENMETALSLEFT = "gui.moltenmetalmixer.liquids.left";
                public static final String CPN_MOLTENMETALSRIGHT = "gui.moltenmetalmixer.liquids.right";
                public static final String CPN_MOLTENMETALSOUTPUT = "gui.moltenmetalmixer.liquids.out";
                
                public static final String CPN_PROGRESSMIXINGINLEFTHORIZONTAL = "gui.moltenmetalmixer.mixingprogress.in.left.horizontal";
                public static final String CPN_PROGRESSMIXINGINRIGHTHORIZONTAL = "gui.moltenmetalmixer.mixingprogress.in.right.horizontal";
                public static final String CPN_PROGRESSMIXINGINLEFTVERTICAL = "gui.moltenmetalmixer.mixingprogress.in.left.vertical";
                public static final String CPN_PROGRESSMIXINGINRIGHTVERTICAL = "gui.moltenmetalmixer.mixingprogress.in.right.vertical";
            }
        }

        public static final class Commands {
            public static final String BASECOMMAND = General.MOD_ID.toLowerCase();
            public static final String GIVEHEATED = "give-heated-item";
            public static final String ENABLEDECAY = "temp-decay";
            public static final String UPGRADEARMOR = "upgrade-armor";
        }

        public static final class Recipes {
            public static final class Anvil {
                public static final ResourceLocation RN_ANVIL = new ResourceLocation(General.MOD_ID, "recipes.anvil.anvil");

                public static final ResourceLocation RN_FORGE = new ResourceLocation(General.MOD_ID, "recipes.anvil.forge");
                public static final ResourceLocation RN_FIREPLACE = new ResourceLocation(General.MOD_ID, "recipes.anvil.fireplace");

                public static final ResourceLocation RN_TONGS = new ResourceLocation(General.MOD_ID, "recipes.anvil.tongs");
                public static final ResourceLocation RN_HAMMER = new ResourceLocation(General.MOD_ID, "recipes.anvil.hammer");

                public static final ResourceLocation RN_RING = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.ring");
                public static final ResourceLocation RN_CHAIN = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chain");
                public static final ResourceLocation RN_PLATE = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.plate");
                public static final ResourceLocation RN_NUGGET = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.nugget");

                public static final ResourceLocation RN_HELMET = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet");
                public static final ResourceLocation RN_CHESTPLATE = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate");
                public static final ResourceLocation RN_LEGGINGS = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings");
                public static final ResourceLocation RN_SHOES = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.shoes");

                public static final ResourceLocation RN_HELMETTOP = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.protection.top");
                public static final ResourceLocation RN_HELMETLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.protection.left");
                public static final ResourceLocation RN_HELMETRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.protection.right");

                public static final ResourceLocation RN_CHESTPLATESHOULDERLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.shoulder.left");
                public static final ResourceLocation RN_CHESTPLATESHOULDERRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.shoulder.right");
                public static final ResourceLocation RN_CHESTPLATEBACKLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.back.left");
                public static final ResourceLocation RN_CHESTPLATEBACKRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.back.right");
                public static final ResourceLocation RN_CHESTPLATESTOMACHLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.stomach.left");
                public static final ResourceLocation RN_CHESTPLATESTOMACHRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.protection.stomach.right");

                public static final ResourceLocation RN_LEGGINGSCALFLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.protection.calf.left");
                public static final ResourceLocation RN_LEGGINGSCALFRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.protection.calf.right");
                public static final ResourceLocation RN_LEGGINGSSHINLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.protection.shin.left");
                public static final ResourceLocation RN_LEGGINGSSHINRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.protection.shin.right");

                public static final ResourceLocation RN_SHOESLACESLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.shoes.protection.laces.left");
                public static final ResourceLocation RN_SHOESLACESRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.shoes.protection.laces.right");

                public static final ResourceLocation RN_HELMETUPGRADETOP = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.upgrade.top");
                public static final ResourceLocation RN_HELMETUPGRADELEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.upgrade.left");
                public static final ResourceLocation RN_HELMETUPGRADERIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.helmet.upgrade.right");

                public static final ResourceLocation RN_CHESTPLATEUPGRADESHOULDERLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.shoulder.left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESHOULDERRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.shoulder.right");
                public static final ResourceLocation RN_CHESTPLATEUPGRADEBACKLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.back.left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADEBACKRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.back.right");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESTOMACHLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.stomach.left");
                public static final ResourceLocation RN_CHESTPLATEUPGRADESTOMACHRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.chestplate.upgrade.stomach.right");

                public static final ResourceLocation RN_LEGGINGSUPGRADECALFLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.upgrade.calf.left");
                public static final ResourceLocation RN_LEGGINGSUPGRADECALFRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.upgrade.calf.right");
                public static final ResourceLocation RN_LEGGINGSUPGRADESHINLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.upgrade.shin.left");
                public static final ResourceLocation RN_LEGGINGSUPGRADESHINRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.leggings.upgrade.shin.right");

                public static final ResourceLocation RN_SHOESUPGRADELACESLEFT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.shoes.upgrade.laces.left");
                public static final ResourceLocation RN_SHOESUPGRADELACESRIGHT = new ResourceLocation(General.MOD_ID, "recipes.anvil.armor.shoes.upgrade.laces.right");
            }
        }

        public static class TextureCreation {
            public static final ResourceLocation TCN_CORE = new ResourceLocation(General.MOD_ID, "core");
            public static final ResourceLocation TCN_ADDON = new ResourceLocation(General.MOD_ID, "addon");
            public static final ResourceLocation TCN_ANVIL = new ResourceLocation(General.MOD_ID, "anvil");

        }
    }

    public static final class Colors
    {
        public static final class Materials
        {
            public static final MinecraftColor CLR_IRON = new MinecraftColor(202,202,202);

            public static final MinecraftColor CLR_GOLD = new MinecraftColor(MinecraftColor.YELLOW);

            public static final MinecraftColor CLR_OBSIDIAN = new MinecraftColor(91,73,101);

            public static final MinecraftColor CLR_STEEL = new MinecraftColor(90, 90, 90);

            public static final MinecraftColor CLR_HARDENED_IRON = new MinecraftColor(47,118,148);

            public static final MinecraftColor CLR_STONE = new MinecraftColor(120,122,121);
        }
    }

    public class NBTTagCompoundData {
        //Stack addon naming
        public static final String InstalledAddons = "installedaddons";
        public static final String ArmorData = "armordata";
        public static final String RenderCompound = "rendercompound";
        public static final String CoreMaterial = "corematerial";
        public static final String CustomName = "name";

        //Versioning used when there is a change in the NBT tag structure.
        public class Versioning {
            public static final int NBTTagVersion = 1;
            public static final String NBTImpVersion = "nbtversion";
        }

        //Used when storing the addon data.
        public class Addons {
            public static final String AddonID = "addonid";
            public static final String ParentID = "parentid";
            public static final String AddonPositionID = "addonpositionid";
            public static final String AddonInstalledAmount = "addoninstalledamount";
            public static final String AddonMaxInstalledAmount = "addonmaxinstalledamount";
            public static final String AddonMaterial = "addonmaterial";
        }

        //Used when storing data from the armor
        public class Armor {
            public static final String NAME = "armorname";
            public static final String CORE_MATERIAL = "material";
            public static final String ADDONS = "addons";
            public static final String CURRENT_DURABILITY = "currentdurability";
            public static final String TOTAL_DURABILITY = "totaldurability";
            public static final String IS_BROKEN = "isbroken";
            public static final String CAPABILITY_DATA = "capabilitydata";
        }

        public class Item {
            public class ItemComponent {
                public static final String MATERIAL = "corematerial";
                public static final String EXTENSION = "extension";
            }
        }

        public class HeatedObject {
            public static final String HEATEDTYPE = "type";
            public static final String HEATEDOBJECT = "object";
            public static final String HEATEDTEMP = "temperature";
            public static final String HEATEDSTACK = "stack";
        }

        public class MaterializedStack {
            public static final String MATERIAL = "material";
        }

        public class Fluids {
            public class MoltenMetal {
                public static final String MATERIAL = "core_material";
            }

            public class MoltenMetalTank {
                public static final String MAXLIQUIDCOUNT = "max";
            }
        }

        public class TE {
            public class Basic {
                public static final String DIRECTION = "direction";
                public static final String NAME = "name";
            }

            public class ForgeBase {
                public static final String MAXTEMPERATURE = "maxtemperature";
                public static final String CURRENTTEMPERATURE = "currenttemperature";
                public static final String CURRENTLYBURNING = "isburning";
                public static final String LASTADDEDHEAT = "lastaddedheat";
                public static final String LASTTEMPERATURE = "lasttemperature";
                public static final String FUELSTACKBURNINGTIME = "fuelstackburningtime";
                public static final String FUELSTACKFUELAMOUNT = "fuelstackfuelamount";
                public static final String LASTPOSITIVEINFLUENCE = "positiveinfluence";
                public static final String LASTNEGATIVEINFLUENCE = "negativeinfluence";
            }

            public class Forge {
                public static final String MIXINGPROGRESS = "mixingprogress";
                public static final String MELTINGPROGRESS = "meltingprogress";

                public class Structure {
                    public static final String DATA = "data";
                    public static final String PARTS = "parts";
                    public static final String FLUIDS = "fluids";
                }
            }

            public class Fireplace {
                public static final String MAXTEMPERATURE = "maxtemperature";
                public static final String CURRENTTEMPERATURE = "currenttemperature";
                public static final String LASTADDEDHEAT = "lastaddedheat";
                public static final String LASTTEMPERATURE = "lasttemperature";
                public static final String FUELSTACKBURNINGTIME = "fuelstackburningtime";
                public static final String FUELSTACKFUELAMOUNT = "fuelstackfuelamount";
                public static final String COOKINGPROGRESS = "cookingprogress";
                public static final String COOKINGSPEED = "cookingspeed";
            }

            public class Anvil {
                public static final String CRAFTINGPROGRESS = "craftingprogress";
                public static final String MATERIAL = "corematerial";
                public static final String ITEMNAME = "itemname";
                public static final String PROCESSING = "processing";
                public static final String REMAININGUSES = "usesRemaining";
            }

            public class Conduit {
                public static final String CONTENTS = "contents";

                public class Structure {
                    public static final String DATA = "data";
                    public static final String PARTS = "parts";
                    public static final String FLUIDS = "fluids";
                    public static final String FLUIDSIZE = "size";
                    public static final String TYPE = "type";
                    public static final String OUTPUTCONNECTIONS = "outputs";
                    public static final String INPUTCONNECTIONS = "inputs";

                    public class Connections {
                        public static final String COORDINATE = "coordinate";
                        public static final String SIDES = "sides";
                    }
                }
            }

            public class Pump {
                public static final String FLUIDS = "fluids";
                public static final String DELAY = "delay";
            }

            public class MoltenMetalTank {
                public static final String TYPE = "type";
                public static final String CONTENTS = "contents";
            }

            public class MoltenMetalMixer {
                public static final String CURRENTRECIPE = "recipe";
                public static final String CURRENTPROGRESS = "progress";
                public static final String FACING = "facing";
            }
        }
    }

    public class Compatibility {
        public class JEI {
            public class RecipeTypes {
                public static final String ANVIL = "armory.compat.jei.recipestypes.anvil";
            }
        }

    }

}
