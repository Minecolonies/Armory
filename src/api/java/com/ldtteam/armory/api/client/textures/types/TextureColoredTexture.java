package com.ldtteam.armory.api.client.textures.types;

/*
  A BIG NOTE UPFRONT. Due to the similarities between TiC ToolSystem and Armories armor system this is a near repackage.
  Most of this code falls under their license, although some changes are made to fit the system in with Armories used
  of Wrapper classes instead of direct access.
 */

import com.google.common.collect.ImmutableList;
import com.ldtteam.smithscore.client.textures.AbstractColoredTexture;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class TextureColoredTexture extends AbstractColoredTexture {

    @Nullable
    protected final TextureAtlasSprite addTexture;
    protected final String addTextureLocation;
    public boolean stencil = false;
    protected int[][] textureData;

    public TextureColoredTexture(String addTextureLocation, @NotNull TextureAtlasSprite baseTexture,
                                 String spriteName) {
        super(baseTexture, spriteName);
        this.addTextureLocation = addTextureLocation;
        this.addTexture = null;
    }

    public TextureColoredTexture(@NotNull TextureAtlasSprite addTexture, @NotNull TextureAtlasSprite baseTexture,
                                 String spriteName) {
        super(baseTexture, spriteName);
        this.addTextureLocation = addTexture.getIconName();
        this.addTexture = addTexture;
    }

    /**
     * @return all textures that should be loaded before this texture.
     */
    @Override
    public Collection<ResourceLocation> getDependencies()
    {
        return ImmutableList.of(new ResourceLocation(addTextureLocation));
    }

    @Override
    protected int colorPixel (int pixel, int mipmap, int pxCoord) {
        int a = alpha(pixel);
        if (a == 0) {
            return pixel;
        }

        if (textureData == null || textureData.length == 0 || Arrays.stream(textureData).allMatch(Objects::isNull))
        {
            loadData();
        }

        int mipMapLevel = mipmap % textureData.length;
        int coordinate = pxCoord % textureData[mipMapLevel].length;

        int c = textureData[mipMapLevel][coordinate];

        // multiply in the color
        int r = red(c);
        int b = blue(c);
        int g = green(c);

        if (!stencil) {
            r = mult(mult(r, red(pixel)), red(pixel));
            g = mult(mult(g, green(pixel)), green(pixel));
            b = mult(mult(b, blue(pixel)), blue(pixel));
        }
        return compose(r, g, b, a);
    }

    protected void loadData () {
        if (addTexture != null && addTexture.getFrameCount() > 0 && addTexture.getFrameTextureData(0).length > 0 && addTexture.getFrameTextureData(0)[0] != null)
        {
            textureData = addTexture.getFrameTextureData(0);
        }
        else
        {
            textureData = backupLoadTexture(new ResourceLocation(addTextureLocation),
              Minecraft.getMinecraft().getResourceManager());
        }
    }
}
