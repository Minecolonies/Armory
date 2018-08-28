package com.ldtteam.armory.api.util.client;

import com.ldtteam.smithscore.util.client.color.MinecraftColor;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.awt.*;

/**
 * Created by Marc on 06.12.2015.
 */
public class Colors {
    @NotNull
    public static MinecraftColor DEFAULT = new MinecraftColor(Color.WHITE);

    public static class Metals {
        @NotNull
        public static MinecraftColor IRON = new MinecraftColor(255, 255, 255);
        @NotNull
        public static MinecraftColor OBSIDIAN = new MinecraftColor(86, 63, 124);
    }

    public static class Ledgers {
        @NotNull
        public static MinecraftColor DEFAULT = new MinecraftColor(255, 255, 255);
        @NotNull
        public static MinecraftColor RED = new MinecraftColor(255, 0, 0);
        @NotNull
        public static MinecraftColor YELLOW = new MinecraftColor(0, 255, 255);
        @NotNull
        public static MinecraftColor BLACK = new MinecraftColor(255, 255, 255);
        @NotNull
        public static MinecraftColor GREY = new MinecraftColor(139, 139, 139);
    }

    public static class Guide {
        @Nonnull
        public static MinecraftColor PAPYRUS = new MinecraftColor(255,252,188);
    }

}
