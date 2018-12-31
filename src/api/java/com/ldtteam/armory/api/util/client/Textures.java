package com.ldtteam.armory.api.util.client;

import com.ldtteam.smithscore.client.events.texture.TextureStitchCollectedEvent;
import com.ldtteam.smithscore.client.textures.HolographicTexture;
import com.ldtteam.smithscore.client.textures.TextureCreator;
import com.ldtteam.smithscore.util.client.CustomResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 06.12.2015.
 */
public class Textures {

    /**
     * Actual construction method is called from the ForgeEvent system. This method kicks the creation of the textures
     * of and provided a map to register the textures in.
     *
     * @param event The events fired before the TextureSheet is stitched. TextureStitchEvent.Pre instance.
     */
    @SubscribeEvent(priority = EventPriority.LOW)
    public void registerTexturesToMap(@Nonnull TextureStitchCollectedEvent event) {
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWPICKAXE.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWBOOK.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWHAMMER.getPrimaryLocation()));
        TextureCreator.registerBaseTexture(new ResourceLocation(Gui.Anvil.HOLOWTONGS.getPrimaryLocation()));

        Gui.Anvil.LOGO_BIG.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Gui.Anvil.LOGO_BIG.getPrimaryLocation())));
        Gui.Anvil.LOGO_SMALL.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Gui.Anvil.LOGO_SMALL.getPrimaryLocation())));
        Gui.FirePit.THERMOMETERICON.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Gui.FirePit.THERMOMETERICON.getPrimaryLocation())));
        Blocks.LiquidMetalFlow.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Blocks.LiquidMetalFlow.getPrimaryLocation())));
        Blocks.LiquidMetalStill.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Blocks.LiquidMetalStill.getPrimaryLocation())));
        Gui.Book.SLOT.addIcon(event.getMap().addNewTextureFromResourceLocation(new ResourceLocation(Gui.Book.SLOT.getPrimaryLocation())));
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void loadTexturesAfterCreation (TextureStitchEvent.Post event) {
        //Only run the creation once, after all mods have been loaded.
        Gui.Anvil.HOLOWPICKAXE.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWPICKAXE.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWBOOK.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWBOOK.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWHAMMER.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWHAMMER.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
        Gui.Anvil.HOLOWTONGS.addIcon(TextureCreator.getBuildSprites().get(Gui.Anvil.HOLOWTONGS.getPrimaryLocation()).get(HolographicTexture.HolographicTextureController.IDENTIFIER));
    }

    public static class Gui {
        @Nonnull
        private static String GUITEXTUREPATH = "armory:textures/gui/";
        @Nonnull
        private static String COMPONENTTEXTUREPATH = GUITEXTUREPATH + "components/";

        public static class Basic {
            @Nonnull
            private static String BASICTEXTUREPATH = GUITEXTUREPATH + "basic/";

            public static class Slots {
                @Nonnull
                public static CustomResource HAMMERSLOT = new CustomResource("Gui.Anvil.Slot.Hammer", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 18, 0, 18, 18);
                @Nonnull
                public static CustomResource TONGSSLOT = new CustomResource("Gui.Anvil.Slot.Tongs", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 36, 0, 18, 18);
                @Nonnull
                public static CustomResource BOOKSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 55, 1, 16, 16);
                @Nonnull
                public static CustomResource UPGRADETOOLSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 73, 1, 16, 16);
                @Nonnull
                public static CustomResource UPGRADEPAYMENTSLOT = new CustomResource("Gui.Anvil.Slot.Book", Basic.BASICTEXTUREPATH + "slot.png", Colors.DEFAULT, 91, 1, 16, 16);
            }

            public static class Components {
                protected static final String RANDOMTEXTUREFILE = GUITEXTUREPATH + "components/randomelements.png";

                public static final CustomResource HORIZONTALTAILLEFTTORIGHTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.LTR.Empty", RANDOMTEXTUREFILE, 32, 3, 50, 4);
                public static final CustomResource HORIZONTALTAILLEFTTORIGHTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.LTR.Full", RANDOMTEXTUREFILE, 32, 0, 50, 3);

                public static final CustomResource HORIZONTALTAILRIGHTTOLEFTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.RTL.Empty", RANDOMTEXTUREFILE, 32, 10, 50, 4);
                public static final CustomResource HORIZONTALTAILRIGHTTOLEFTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Tail.RTL.Full", RANDOMTEXTUREFILE, 32, 7, 50, 3);

                public static final CustomResource HORIZONTALHEADLEFTTORIGHTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.LTR.Empty", RANDOMTEXTUREFILE, 82, 16, 50, 16);
                public static final CustomResource HORIZONTALHEADLEFTTORIGHTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.LTR.Full", RANDOMTEXTUREFILE, 132, 16, 50, 16);

                public static final CustomResource HORIZONTALHEADRIGHTTOLEFTEMPTY = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.RTL.Empty", RANDOMTEXTUREFILE, 82, 0, 50, 16);
                public static final CustomResource HORIZONTALHEADRIGHTTOLEFTFULL = new CustomResource("Gui.Components.ProgressBars.HorizontalCornered.Head.RTL.Full", RANDOMTEXTUREFILE, 132, 0, 50, 16);

                public static final CustomResource VERTICALHEADTOPTOBOTTOMLEFTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESLEFT.Head.TTB.Empty", RANDOMTEXTUREFILE, 182, 0, 16, 26);
                public static final CustomResource VERTICALHEADTOPTOBOTTOMLEFTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESLEFT.Head.TTB.Full", RANDOMTEXTUREFILE, 198, 0, 16, 26);

                public static final CustomResource VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESRIGHT.Head.TTB.Empty", RANDOMTEXTUREFILE, 214, 0, 16, 26);
                public static final CustomResource VERTICALHEADTOPTOBOTTOMRIGHTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESRIGHT.Head.TTB.Full", RANDOMTEXTUREFILE, 230, 0, 16, 26);

                public static final CustomResource VERTICALTAILTOPTOBOTTOMLEFTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESLEFT.Tail.TTB.Empty", RANDOMTEXTUREFILE, 0, 38, 4, 20);
                public static final CustomResource VERTICALTAILTOPTOBOTTOMLEFTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESLEFT.Tail.TTB.Full", RANDOMTEXTUREFILE, 4, 38, 4, 20);

                public static final CustomResource VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTOREMPTY = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESRIGHT.Tail.TTB.Empty", RANDOMTEXTUREFILE, 8, 38, 4, 20);
                public static final CustomResource VERTICALTAILTOPTOBOTTOMRIGHTCONNTECTORFULL = new CustomResource("Gui.Components.ProgressBars.Vertical.TK_LACESRIGHT.Tail.TTB.Full", RANDOMTEXTUREFILE, 12, 38, 4, 20);
            }


            public static class Images {
                @Nonnull
                private static String IMAGETEXTUREPATH = GUITEXTUREPATH + "images/";

                @Nonnull
                public static CustomResource HAMMER = new CustomResource("Gui.Anvil.Image.Hammer", Basic.Images.IMAGETEXTUREPATH + "anvilhammer", Colors.DEFAULT, 0, 0, 30, 30);
            }
        }

        public static class FirePit {
            @Nonnull
            public static CustomResource THERMOMETERICON = new CustomResource("Gui.Forge.Thermometer", "armory:gui/images/16x thermoalt", Colors.DEFAULT, 0, 0, 16, 16);
            @Nonnull
            public static CustomResource DROPEMPTY = new CustomResource("Gui.Anvil.ProgressBars.Drop.Empty", Basic.Components.RANDOMTEXTUREFILE, Colors.DEFAULT, 16, 32, 8, 12);
            @Nonnull
            public static CustomResource DROPFULL = new CustomResource("Gui.Anvil.ProgressBars.Drop.Empty", Basic.Components.RANDOMTEXTUREFILE, Colors.DEFAULT, 24, 32, 8, 12);
        }

        public static class Anvil {
            @Nonnull
            public static CustomResource LOGO_BIG      = new CustomResource("Gui.Anvil.Image.Logo.Big", "armory:gui/images/32x anvilhammer", Colors.DEFAULT, 0, 0, 32, 32);
            @Nonnull
            public static CustomResource LOGO_SMALL    = new CustomResource("Gui.Anvil.Image.Logo.Small", "armory:gui/images/16x anvilhammer", Colors.DEFAULT, 0, 0, 16, 16);
            @Nonnull
            public static CustomResource EXPERIENCEORB = new CustomResource("Gui.Anvil.Image.ExperienceOrb", GUITEXTUREPATH + "components/randomelements.png", Colors.DEFAULT, 16, 0, 16, 16);
            @Nonnull
            public static CustomResource HOLOWPICKAXE  = new CustomResource("Gui.Anvil.SlotHolo.Pickaxe", "minecraft:items/iron_pickaxe", Colors.DEFAULT, 0, 0, 16, 16);
            @Nonnull
            public static CustomResource HOLOWBOOK     = new CustomResource("Gui.Anvil.SlotHolo.Book", "minecraft:items/book_normal", Colors.DEFAULT, 0,0, 16, 16);
            @Nonnull
            public static CustomResource HOLOWHAMMER   = new CustomResource("Gui.Anvil.SlotHolo.Hammer", "armory:items/basic/16x work hammer", Colors.DEFAULT, 0,0,16,16);
            @Nonnull
            public static CustomResource HOLOWTONGS    = new CustomResource("Gui.Anvil.SlotHolo.Tongs", "armory:items/basic/16x tongs", Colors.DEFAULT, 0,0,16,16);
        }

        public static class Book
        {
            @Nonnull
            public static CustomResource SLOT = new CustomResource("Gui.Book.Anvil.5x5.Slot", "armory:gui/components/slot_default", Colors.DEFAULT, 0, 0, 18, 18);
        }

        public static class Compatibility {
            public static class JEI {
                public static class ArmorsAnvil {
                    @Nonnull
                    public static CustomResource GUI = new CustomResource("Gui.Compatibility.JEI.Anvil.gui", GUITEXTUREPATH + "jei/anvil.png", 0, 0, 162, 133);
                }
                public static class HeatedItem {
                    @Nonnull
                    public static CustomResource GUI = new CustomResource("Gui.Compatibility.JEI.Anvil.gui", GUITEXTUREPATH + "jei/heated_item.png", 0, 0, 101, 32);
                }
            }
        }
    }

    public static class Blocks {
        public static final CustomResource LiquidMetalFlow = new CustomResource("Armory.General.LiquidMetal.Flow", "armory:blocks/liquidmetal/flow");
        public static final CustomResource LiquidMetalStill = new CustomResource("Armory.General.LiquidMetal.Still", "armory:blocks/liquidmetal/still");
    }

}
