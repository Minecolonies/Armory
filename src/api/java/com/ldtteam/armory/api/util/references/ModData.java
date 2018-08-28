package com.ldtteam.armory.api.util.references;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 1/24/2017.
 */
public class ModData
{

    public static class Materials
    {

        public static class Iron
        {
            @Nonnull
            public static final Float   IRON_MELTINGPOINT    = 1538F;
            @Nonnull
            public static final Integer IRON_MELTINGTIME     = 500;
            @Nonnull
            public static final Float   IRON_VAPORIZINGPOINT = 2862F;
            @Nonnull
            public static final Integer IRON_VAPORIZINGTIME  = (int) (IRON_MELTINGTIME * (float) Iron.IRON_VAPORIZINGPOINT / (float) Iron.IRON_MELTINGPOINT);
            @Nonnull
            public static final Float   IRON_HEATCOEFFICIENT = 0.225F;
        }

        public static class Gold
        {
            @Nonnull
            public static final Float   GOLD_MELTINGPOINT    = 1064F;
            @Nonnull
            public static final Integer GOLD_MELTINGTIME     = (int) (Iron.IRON_MELTINGTIME * (GOLD_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float   GOLD_VAPORIZINGPOINT = 2856F;
            @Nonnull
            public static final Integer GOLD_VAPORIZINGTIME  = (int) (GOLD_MELTINGTIME * GOLD_VAPORIZINGPOINT / GOLD_MELTINGPOINT);
            @Nonnull
            public static final Float   GOLD_HEATCOEFFICIENT = 0.478F;
        }

        public static class Obsidian
        {
            @Nonnull
            public static final Float   OBSIDIAN_MELTINGPOINT    = 798F;
            @Nonnull
            public static final Integer OBSIDIAN_MELTINGTIME     = (int) (Iron.IRON_MELTINGTIME * (OBSIDIAN_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float   OBSIDIAN_VAPORIZINGPOINT = 2950F;
            @Nonnull
            public static final Integer OBSIDIAN_VAPORIZINGTIME  = (int) (OBSIDIAN_MELTINGTIME * Obsidian.OBSIDIAN_VAPORIZINGPOINT / Obsidian.OBSIDIAN_MELTINGPOINT);
            @Nonnull
            public static final Float   OBSIDIAN_HEATCOEFFICIENT = 0.345F;
        }

        public static class Steel
        {
            @Nonnull
            public static final Float   STEEL_MELTINGPOINT    = 1373F;
            @Nonnull
            public static final Integer STEEL_MELTINGTIME     = (int) (Iron.IRON_MELTINGTIME * (STEEL_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float   STEEL_VAPORIZINGPOINT = 3165F;
            @Nonnull
            public static final Integer STEEL_VAPORIZINGTIME  = (int) (STEEL_MELTINGTIME * Steel.STEEL_VAPORIZINGPOINT / Steel.STEEL_MELTINGPOINT);
            @Nonnull
            public static final Float   STEEL_HEATCOEFFICIENT = 0.2F;
        }

        public static class Hardened_Iron
        {
            @Nonnull
            public static final Float   HARDENED_IRON_MELTINGPOINT    = 1785F;
            @Nonnull
            public static final Integer HARDENED_IRON_MELTINGTIME     = (int) (Iron.IRON_MELTINGTIME * (HARDENED_IRON_MELTINGPOINT / Iron.IRON_MELTINGPOINT));
            @Nonnull
            public static final Float   HARDENED_IRON_VAPORIZINGPOINT = 2963F;
            @Nonnull
            public static final Integer HARDENED_IRON_VAPORIZINGTIME  =
              (int) (HARDENED_IRON_MELTINGTIME * Hardened_Iron.HARDENED_IRON_VAPORIZINGPOINT / Hardened_Iron.HARDENED_IRON_MELTINGPOINT);
            @Nonnull
            public static final Float   HARDENED_IRON_HEATCOEFFICIENT = 0.176F;
        }
    }

    public static class Durability
    {

        public static class Armor
        {
            @Nonnull
            public static final Integer DUR_HELMET     = 345;
            @Nonnull
            public static final Integer DUR_CHESTPLATE = 475;
            @Nonnull
            public static final Integer DUR_LEGGINGS   = 380;
            @Nonnull
            public static final Integer DUR_SHOES      = 310;
        }

        public static class Anvil
        {
            @Nonnull
            public static final Integer DAN_IRON     = 1575;
            @Nonnull
            public static final Integer DAN_OBSIDIAN = 2100;
            @Nonnull
            public static final Integer DAN_STONE    = 575;
        }
    }

    public static class Armor
    {
        public static class Ratio
        {
            @Nonnull
            public static final Float RAT_HELMET     = 0.2f;
            @Nonnull
            public static final Float RAT_CHESTPLATE = 0.4f;
            @Nonnull
            public static final Float RAT_LEGGINGS   = 0.3f;
            @Nonnull
            public static final Float RAT_SHOES      = 0.1f;
        }

        public static class Defence
        {

            @Nonnull
            public static final Float DEF_HELMET     = 1f;
            @Nonnull
            public static final Float DEF_CHESTPLATE = 2f;
            @Nonnull
            public static final Float DEF_LEGGINGS   = 1.5f;
            @Nonnull
            public static final Float DEF_SHOES      = 0.5f;
        }

        public static class Toughness
        {

            @Nonnull
            public static final Float TOU_HELMET     = 1f;
            @Nonnull
            public static final Float TOU_CHESTPLATE = 2f;
            @Nonnull
            public static final Float TOU_LEGGINGS   = 1.5f;
            @Nonnull
            public static final Float TOU_SHOES      = 0.5f;
        }
    }

    public static class Addons
    {

        public static class Durability
        {
            public static final class Medieval
            {
                public static final class Helmet
                {

                    @Nonnull
                    public static Float DUR_TOP = 30f;

                    @Nonnull
                    public static Float DUR_RIGHT = 20f;

                    @Nonnull
                    public static Float DUR_LEFT = 20f;
                }

                public static final class ChestPlate
                {

                    @Nonnull
                    public static Float DUR_SHOULDERLEFT = 20f;

                    @Nonnull
                    public static Float DUR_SHOULDERRIGHT = 20f;

                    @Nonnull
                    public static Float DUR_STOMACHLEFT = 40f;

                    @Nonnull
                    public static Float DUR_STOMACHRIGHT = 40f;

                    @Nonnull
                    public static Float DUR_BACKLEFT = 50f;

                    @Nonnull
                    public static Float DUR_BACKRIGHT = 50f;
                }

                public static final class Leggings
                {

                    @Nonnull
                    public static Float DUR_SHINLEFT = 35f;

                    @Nonnull
                    public static Float DUR_SHINRIGHT = 35f;

                    @Nonnull
                    public static Float DUR_CALFLEFT = 35f;

                    @Nonnull
                    public static Float DUR_CALFRIGHT = 35f;
                }

                public static final class Shoes
                {

                    @Nonnull
                    public static Float DUR_LACESLEFT = 15f;

                    @Nonnull
                    public static Float DUR_LACESRIGHT = 15f;
                }
            }
        }

        public static class Defence
        {
            public static final class Medieval
            {
                public static final class Helmet
                {

                    @Nonnull
                    public static Float DEF_TOP = 3.0f;

                    @Nonnull
                    public static Float DEF_RIGHT = 2.0f;

                    @Nonnull
                    public static Float DEF_LEFT = 2.0f;
                }

                public static final class ChestPlate
                {

                    @Nonnull
                    public static Float DEF_SHOULDERLEFT = 2.0f;

                    @Nonnull
                    public static Float DEF_SHOULDERRIGHT = 2.0f;

                    @Nonnull
                    public static Float DEF_STOMACHLEFT = 4.0f;

                    @Nonnull
                    public static Float DEF_STOMACHRIGHT = 4.0f;

                    @Nonnull
                    public static Float DEF_BACKLEFT = 5.0f;

                    @Nonnull
                    public static Float DEF_BACKRIGHT = 5.0f;
                }

                public static final class Leggings
                {

                    @Nonnull
                    public static Float DEF_SHINLEFT = 3.5f;

                    @Nonnull
                    public static Float DEF_SHINRIGHT = 3.5f;

                    @Nonnull
                    public static Float DEF_CALFLEFT = 3.5f;

                    @Nonnull
                    public static Float DEF_CALFRIGHT = 3.5f;
                }

                public static final class Shoes
                {

                    @Nonnull
                    public static Float DEF_LACESLEFT = 1.5f;

                    @Nonnull
                    public static Float DEF_LACESRIGHT = 1.5f;
                }
            }
        }

        public static class Toughness
        {
            public static final class Medieval
            {
                public static final class Helmet
                {

                    @Nonnull
                    public static Float TOU_TOP = 0.3f;

                    @Nonnull
                    public static Float TOU_RIGHT = 0.2f;

                    @Nonnull
                    public static Float TOU_LEFT = 0.2f;
                }

                public static final class ChestPlate
                {

                    @Nonnull
                    public static Float TOU_SHOULDERLEFT = 0.2f;

                    @Nonnull
                    public static Float TOU_SHOULDERRIGHT = 0.2f;

                    @Nonnull
                    public static Float TOU_STOMACHLEFT = 0.4f;

                    @Nonnull
                    public static Float TOU_STOMACHRIGHT = 0.4f;

                    @Nonnull
                    public static Float TOU_BACKLEFT = 0.5f;

                    @Nonnull
                    public static Float TOU_BACKRIGHT = 0.5f;
                }

                public static final class Leggings
                {

                    @Nonnull
                    public static Float TOU_SHINLEFT = 0.3f;

                    @Nonnull
                    public static Float TOU_SHINRIGHT = 0.3f;

                    @Nonnull
                    public static Float TOU_CALFLEFT = 0.3f;

                    @Nonnull
                    public static Float TOU_CALFRIGHT = 0.3f;
                }

                public static final class Shoes
                {

                    @Nonnull
                    public static Float TOU_LACESLEFT = 0.1f;

                    @Nonnull
                    public static Float TOU_LACESRIGHT = 0.1f;
                }
            }
        }
    }
}
