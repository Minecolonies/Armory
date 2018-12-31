package com.ldtteam.armory.api.util.client;

import com.ldtteam.armory.api.util.references.References;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 *
 * Copyrighted according to Project specific license
 */
public class TranslationKeys {

    public static final class CreativeTabs {
        public static final String TK_TAB_GENERAL = "armory.tabs.general";
        public static final String TK_TAB_COMPONENTS = "armory.tabs.components";
        public static final String TK_TAB_ARMOR = "armory.tabs.armor";
        public static final String TK_TAB_HEATEDITEMS = "armory.tabs.heateditem";
    }

    public static final class Messages {
        public static final class Commands {
            public static final String TK_BASEUSAGE         = References.InternalNames.Commands.BASECOMMAND + ".usage";
            public static final String TK_GIVEHEATEDUSAGE   = References.InternalNames.Commands.GIVEHEATED + ".usage";
            public static final String TK_TEMPDECAYUSAGE    = References.InternalNames.Commands.ENABLEDECAY + ".usage";
            public static final String TK_UPGRADEARMORUSAGE = References.InternalNames.Commands.UPGRADEARMOR + ".usage";

            public static final class Successfull {
                public static final String TK_TEMPDECAY_ENABLE = References.InternalNames.Commands.ENABLEDECAY + ".success.enable";
                public static final String TK_TEMPDECAY_DISABLE = References.InternalNames.Commands.ENABLEDECAY + ".success.disable";
            }

            public static final class Errors {
                public static final String TK_UPGRADEARMOR_NOARMORSELECTED = References.InternalNames.Commands.UPGRADEARMOR + ".error.noarmorselected";
                public static final String TK_UPGRADEARMOR_ALREADYINSTALLED = References.InternalNames.Commands.UPGRADEARMOR + ".error.alreadyinstalled";
                public static final String TK_UPGRADEARMOR_NOTPOSSIBLE = References.InternalNames.Commands.UPGRADEARMOR + ".error.notpossible";
            }
        }

    }

    public class Items {
        public class HeatedIngot {
            public static final String TK_TAG_TEMPERATURE = "armory.items.heatedobject.tooltip.temperature";
        }

        public class MultiArmor {
            public class Armor {
                public static final String TK_HELMET     = "item.armory.multiarmor.armor.helmet";
                public static final String TK_CHESTPLATE = "item.armory.multiarmor.armor.chestplate";
                public static final String TK_LEGGINGS   = "item.armory.multiarmor.armor.leggings";
                public static final String TK_SHOES      = "item.armory.multiarmor.armor.shoes";
                public static final String TK_DURABILTIY = "item.armory.multiarmor.tooltip.durability";
                public static final String TK_BROKEN     = "item.armory.multiarmor.tooltip.broken";
            }

            public class Upgrades {
                public class Helmet {
                    public static final String TK_TOP = "item.armory.multiarmor.upgrade.helmet.top";
                    public static final String TK_RIGHT = "item.armory.multiarmor.upgrade.helmet.right";
                    public static final String TK_LEFT = "item.armory.multiarmor.upgrade.helmet.left";
                }

                public class Chestplate {
                    public static final String TK_SHOULDERLEFT = "item.armory.multiarmor.upgrade.chestplate.shoulderleft";
                    public static final String TK_SHOULDERRIGHT = "item.armory.multiarmor.upgrade.chestplate.shoulderright";
                    public static final String TK_STOMACHLEFT = "item.armory.multiarmor.upgrade.chestplate.stomachleft";
                    public static final String TK_STOMACHRIGHT = "item.armory.multiarmor.upgrade.chestplate.stomachright";
                    public static final String TK_BACKLEFT = "item.armory.multiarmor.upgrade.chestplate.backleft";
                    public static final String TK_BACKRIGHT = "item.armory.multiarmor.upgrade.chestplate.backright";

                }

                public class Leggings {
                    public static final String TK_SHINLEFT = "item.armory.multiarmor.upgrade.leggings.shinleft";
                    public static final String TK_SHINRIGHT = "item.armory.multiarmor.upgrade.leggings.shinright";
                    public static final String TK_CALFLEFT = "item.armory.multiarmor.upgrade.leggings.calfleft";
                    public static final String TK_CALFRIGHT = "item.armory.multiarmor.upgrade.leggings.calfright";
                }

                public class Shoes {
                    public static final String TK_LACESLEFT = "item.armory.multiarmor.upgrade.shoes.lacesleft";
                    public static final String TK_LACESRIGHT = "item.armory.multiarmor.upgrade.shoes.lacesright";
                }

            }
        }

        public class MoltenMetalTank {
            public static final String TK_CONTENTS = "item.armory.multimetaltank.contents";
        }

        public class ItemBlocks {

            public class Anvil {
                public static final String TK_MATERIAL = "item.armory.itemblock.anvil.tooltip.material";
                public static final String TK_REMAININGUSES = "item.armory.itemblock.anvil.tooltip.remaininguses";
            }
        }
    }

    public class Materials {
        public class Anvil {
            public static final String TK_ANVIL_STONE = "armory.materials.vanilla.stone";
            public static final String TK_ANVIL_IRON = "armory.materials.vanilla.iron";
            public static final String TK_ANVIL_OBSIDIAN = "armory.materials.vanilla.obsidian";
        }


        public class Armor {
            public static final String TK_ARMOR_IRON = "armory.materials.vanilla.iron";
            public static final String TK_ARMOR_OBSIDIAN = "armory.materials.vanilla.obsidian";
            public static final String TK_ARMOR_GOLD = "armory.materials.vanilla.gold";
            public static final String TK_ARMOR_STEEL = "armory.materials.armory.steel";
            public static final String TK_ARMOR_HARDENED_IRON = "armory.materials.armory.iron.hardened";
        }

    }

    public class Fluids {
        public static final String MOLTEN = "armory.fluids.molten";
    }

    public class Gui {

        public static final String InformationTitel = "armory.gui.infotitel";

        public class Forge {
            public static final String InfoLine1 = "armory.gui.forge.ledger.infoline1";
            public static final String InfoLine2 = "armory.gui.forge.ledger.infoline2";
            public static final String InfoLine3 = "armory.gui.forge.ledger.infoline3";
            public static final String TempTitel = "armory.gui.forge.ledger.temptitel";
            public static final String TempMax = "armory.gui.forge.ledger.tempmax";
            public static final String TempCurrent = "armory.gui.forge.ledger.tempcurrent";
            public static final String LastAdded = "armory.gui.forge.ledger.lastadded";
        }

        public class Fireplace {
            public static final String InfoLine1 = "armory.gui.fireplace.ledger.infoline1";
            public static final String InfoLine2 = "armory.gui.fireplace.ledger.infoline2";
            public static final String InfoLine3 = "armory.gui.fireplace.ledger.infoline3";
            public static final String TempTitel = "armory.gui.fireplace.ledger.temptitel";
            public static final String TempMax = "armory.gui.fireplace.ledger.tempmax";
            public static final String TempCurrent = "armory.gui.fireplace.ledger.tempcurrent";
            public static final String LastAdded = "armory.gui.fireplace.ledger.lastadded";
            public static final String CookingMultiplier = "armory.gui.fireplace.ledger.cookingmultiplier";
        }

        public class Anvil {
            public static final String InfoLine1 = "armory.gui.anvil.ledger.infoline1";
            public static final String InfoLine2 = "armory.gui.anvil.ledger.infoline2";
            public static final String MaterialTitel = "armory.gui.anvil.ledger.materialtitel";
            public static final String MaterialMax = "armory.gui.anvil.ledger.materialmax";
            public static final String MaterialCurrent = "armory.gui.anvil.ledger.materialcurrent";
            public static final String Material = "armory.gui.forge.anvil.material";
        }

        public class JEI {
            public static final String AnvilRecipeName = "armory.jei.compatibility.armorsanvilrecipe";
            public static final String HeatedItemName = "armory.jei.compatibility.heateditemrecipe";
        }

        public class Components {
            public static final String PROGRESSFLAMEFUELLEFT = "armory.gui.components.flame.tooltip";
            public static final String PROGRESSBARPROGRESS = "armory.gui.components.progressbar.tooltip";
            public static final String FLUIDTANKAMOUNT = "armory.gui.components.fluidtank.tooltip.amount";
            public static final String FLUIDTANKCAPACITY = "armory.gui.components.fluidtank.tooltip.capacity";
            public static final String FLUIDTANKNOLIQUID = "armory.gui.components.fluidtank.tooltip.noliquid";
        }

        public class Guide {

            public class Introduction {
                public static final String TK_INTRODUCTION = "armory.gui.guide.pages.introduction.title";

                public static final String TK_SECTION_ONE = "armory.gui.guide.pages.introduction.section.one";
                public static final String TK_SECTION_TWO = "armory.gui.guide.pages.introduction.section.two";
                public static final String TK_SECTION_THREE = "armory.gui.guide.pages.introduction.section.three";
            }

            public class Medieval {
                public static final String TK_MEDIEVAL = "armory.gui.guide.pages.medieval.title";

                public static final String TK_SECTION_ONE_TITLE = "armory.gui.guide.pages.medieval.section.one.title";
                public static final String TK_SECTION_ONE_CONTENT = "armory.gui.guide.pages.medieval.section.one.content";
                public static final String TK_SECTION_TWO_TITLE = "armory.gui.guide.pages.medieval.section.two.title";
                public static final String TK_SECTION_TWO_CONTENT_PART_ONE = "armory.gui.guide.pages.medieval.section.two.content.part.one";

            }

        }
    }
}


