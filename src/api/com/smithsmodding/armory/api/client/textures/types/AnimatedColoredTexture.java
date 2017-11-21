package com.smithsmodding.armory.api.client.textures.types;

/*
  A BIG NOTE UPFRONT. Due to the similarities between TiC ToolSystem and Armories armor system this is a near repackage.
  Most of this code falls under their license, although some changes are made to fit the system in with Armories used
  of Wrapper classes instead of direct access.
 */

import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.util.references.ModLogger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;

/**
 * ONLY USE THIS IF YOU KNOW WHAT YOU'RE DOING!
 * Animated textures eat up a lot of graphic memory. Generating many can lead to severe performance issues!
 * Only use this for very specific and limited applications!
 */
public class AnimatedColoredTexture extends TextureColoredTexture {

    @Nullable
    private TextureAtlasSprite actualTexture;

    public AnimatedColoredTexture(@NotNull TextureAtlasSprite addTexture,
                                  @NotNull TextureAtlasSprite baseTexture, String spriteName) {
        super(addTexture, baseTexture, spriteName);
    }

    @Override
    public boolean load (IResourceManager manager, ResourceLocation location) {
        if (addTexture.getFrameCount() > 0) {
            actualTexture = addTexture;
        } else {
            actualTexture = backupLoadTextureAtlasSprite(new ResourceLocation(addTextureLocation),
                    Minecraft.getMinecraft().getResourceManager());
        }

        return super.load(manager, location);
    }

    @Override
    protected void processData (int[][] data) {
        // get animation data again
        ResourceLocation resourcelocation1 = this.completeResourceLocation(new ResourceLocation(addTextureLocation), 0);
        IResource iresource = null;
        try {
            iresource = Minecraft.getMinecraft().getResourceManager().getResource(resourcelocation1);
        } catch (IOException e) {
            ModLogger.getInstance().error(new Exception("Unable to load " + resourcelocation1, e));
            return;
        }
        // todo: clean all this up to use the metadata of the actualTexture and therefore only run once without separation
        AnimationMetadataSection meta = iresource.getMetadata("animation");

        if (meta == null) {
            throw new IllegalArgumentException(String.format(
                    "Trying to create animated texture from %s but no animation data is present", addTextureLocation));
        }

        // animations are either defined per keyframe or generated by texture size
        // input is the data of the template
        // we now adapt this textureAtlasSprite to match the animated actualTexture

        // animation defined with keyframes
        if (meta.getFrameCount() > 0) {
            //for(int i = 0; i < meta.getFrameCount(); i++) {
            Iterator iterator = meta.getFrameIndexSet().iterator();

            while (iterator.hasNext()) {
                int i1 = ((Integer) iterator.next()).intValue();

                // missing check if frame index is valid

                //this.allocateFrameTextureData(i1);
                if (this.framesTextureData.size() <= i1) {
                    for (int j = this.framesTextureData.size(); j <= i1; ++j) {
                        this.framesTextureData.add(null);
                    }
                }

                int[][] data2 = new int[data.length][];
                for (int j = 0; j < data.length; j++) {
                    if (data[j] != null) {
                        data2[j] = data[j].clone();
                    }
                }

                // set textureData for processing
                textureData = actualTexture.getFrameTextureData(i1);
                // process the copied data
                super.processData(data2);

                // add it to the textures data
                this.framesTextureData.set(i1, data2);
            }
            //}
        }
        // animation defined inheritly through texture size
        else {
            List<AnimationFrame> frameList = Lists.newArrayList();

            int count = actualTexture.getFrameCount();

            for (int i = 0; i < count; i++) {
                int[][] data2 = new int[data.length][];
                for (int j = 0; j < data.length; j++) {
                    if (data[j] != null) {
                        data2[j] = data[j].clone();
                    }
                }

                textureData = actualTexture.getFrameTextureData(i);
                super.processData(data2);

                this.framesTextureData.add(i, data2);
                frameList.add(new AnimationFrame(i, -1));
            }

            meta =
                    new AnimationMetadataSection(frameList, this.width, this.height, meta.getFrameTime(), meta.isInterpolate());
        }

        // todo: access transform this
        try {
            Field f = TextureAtlasSprite.class.getDeclaredField("animationMetadata");
            f.setAccessible(true);
            f.set(this, meta);
        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }
}
