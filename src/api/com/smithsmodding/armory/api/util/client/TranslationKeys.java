package com.smithsmodding.armory.api.util.client;

import com.smithsmodding.armory.api.util.references.References;

/**
 * Created by Orion
 * Created on 4/16/2015
 * 5:48 PM
 *
 * Copyrighted according to Project specific license
 */
public class TranslationKeys {

    public static final class CreativeTabs {
        public static final String General = "Armory.Tabs.General";
        public static final String Components = "Armory.Tabs.Components";
        public static final String Armor = "Armory.Tabs.Armor";
        public static final String HeatedItems = "Armory.Tabs.HeatedItem";
    }

    public static final class Messages {
        public static final class Commands {
            public static final String BASEUSAGE = References.InternalNames.Commands.BASECOMMAND + ".usage";
            public static final String GIVEHEATEDUSAGE = References.InternalNames.Commands.GIVEHEATED + ".usage";
            public static final String TEMPDECAYUSAGE = References.InternalNames.Commands.ENABLEDECAY + ".usage";
        }

    }

    public class Items {
        public class HeatedIngot {
            public static final String TemperatureTag = "Armory.Items.HeatedObject.Tooltip.Temperature";
        }

        public class MultiArmor {
            public class Armor {
                public static final String TK_HELMET = "item.Armory.MultiArmor.Armor.Helmet";
                public static final String TK_CHESTPLATE = "iten.Armory.MultiArmor.Armor.ChestPlate";
                public static final String TK_LEGGINGS = "item.Armory.MultiArmor.Armor.Leggings";
                public static final String TK_SHOES = "item.Armory.MultiArmor.Armor.Shoes";
            }

            public class Upgrades {
                public class Helmet {
                    public static final String TK_TOP = "item.Armory.MultiArmor.Upgrade.Helmet.Top";
                    public static final String TK_RIGHT = "item.Armory.MultiArmor.Upgrade.Helmet.Right";
                    public static final String TK_LEFT = "item.Armory.MultiArmor.Upgrade.Helmet.Left";
                }

                public class Chestplate {
                    public static final String TK_SHOULDERLEFT = "item.Armory.MultiArmor.Upgrade.ChestPlate.ShoulderLeft";
                    public static final String TK_SHOULDERRIGHT = "item.Armory.MultiArmor.Upgrade.ChestPlate.ShoulderRight";
                    public static final String TK_STOMACHLEFT = "item.Armory.MultiArmor.Upgrade.ChestPlate.StomachLeft";
                    public static final String TK_STOMACHRIGHT = "item.Armory.MultiArmor.Upgrade.ChestPlate.StomachRight";
                    public static final String TK_BACKLEFT = "item.Armory.MultiArmor.Upgrade.ChestPlate.BackLeft";
                    public static final String TK_BACKRIGHT = "item.Armory.MultiArmor.Upgrade.ChestPlate.BackRight";

                }

                public class Leggings {
                    public static final String TK_SHINLEFT = "item.Armory.MultiArmor.Upgrade.Leggings.ShinLeft";
                    public static final String TK_SHINRIGHT = "item.Armory.MultiArmor.Upgrade.Leggings.ShinRight";
                    public static final String TK_CALFLEFT = "item.Armory.MultiArmor.Upgrade.Leggings.CalfLeft";
                    public static final String TK_CALFRIGHT = "item.Armory.MultiArmor.Upgrade.Leggings.CalfRight";
                }

                public class Shoes {
                    public static final String TK_LACESLEFT = "item.Armory.MultiArmor.Upgrade.Shoes.LacesLeft";
                    public static final String TK_LACESRIGHT = "item.Armory.MultiArmor.Upgrade.Shoes.LacesRight";
                }

            }
        }
    }

    public class Materials {
        public class Anvil {
            public static final String TK_ANVIL_STONE = "Armory.Materials.AnvilOnly.Vanilla.Stone";
            public static final String TK_ANVIL_IRON = "Armory.Materials.Vanilla.Iron";
            public static final String TK_ANVIL_OBSIDIAN = "Armory.Materials.Vanilla.Obsidian";
        }


        public class Armor {
            public static final String TK_ARMOR_IRON = "Armory.Materials.Vanilla.Iron";
            public static final String TK_ARMOR_OBSIDIAN = "Armory.Materials.Vanilla.Obsidian";
            public static final String TK_ARMOR_GOLD = "Armory.Materials.Vanilla.Gold";
            public static final String TK_ARMOR_STEEL = "Armory.Materials.Armory.Steel";
        }

    }

    public class Fluids {
        public static final String MOLTEN = "Armory.Fluids.Molten";
    }

    public class Gui {

        public static final String InformationTitel = "Armory.GUI.InfoTitel";

        public class Forge {
            public static final String InfoLine1 = "Armory.GUI.Forge.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Forge.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.Forge.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.Forge.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.Forge.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.Forge.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.Forge.Ledger.LastAdded";
        }

        public class Fireplace {
            public static final String InfoLine1 = "Armory.GUI.Fireplace.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Fireplace.Ledger.InfoLine2";
            public static final String InfoLine3 = "Armory.GUI.Fireplace.Ledger.InfoLine3";
            public static final String TempTitel = "Armory.GUI.Fireplace.Ledger.TempTitel";
            public static final String TempMax = "Armory.GUI.Fireplace.Ledger.TempMax";
            public static final String TempCurrent = "Armory.GUI.Fireplace.Ledger.TempCurrent";
            public static final String LastAdded = "Armory.GUI.Fireplace.Ledger.LastAdded";
            public static final String CookingMultiplier = "Armory.GUI.Fireplace.Ledger.CookingMultiplier";
        }

        public class Anvil {
            public static final String InfoLine1 = "Armory.GUI.Anvil.Ledger.InfoLine1";
            public static final String InfoLine2 = "Armory.GUI.Anvil.Ledger.InfoLine2";
        }

        public class JEI {
            public static final String AnvilRecipeName = "Armory.NEI.Compatibility.ArmorsAnvilRecipe";
        }

        public class Components {
            public static final String PROGRESSFLAMEFUELLEFT = "Armory.GUI.Components.Flame.ToolTip";
            public static final String PROGRESSBARPROGRESS = "Armory.GUI.Components.ProgressBar.ToolTip";
            public static final String FLUIDTANKAMOUNT = "Armory.GUI.Components.FluidTank.ToolTip.Amount";
            public static final String FLUIDTANKCAPACITY = "Armory.GUI.Components.FluidTank.ToolTip.Capacity";
            public static final String FLUIDTANKNOLIQUID = "Armory.GUI.Components.FluidTank.ToolTip.NoLiquid";
        }
    }
}


