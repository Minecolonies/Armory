package com.smithsmodding.armory.api.client.textures.creation;

import com.smithsmodding.smithscore.core.interfaces.ITextureMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by marcf on 1/5/2017.
 */
public interface ICreationController extends IForgeRegistryEntry<ICreationController> {

    /**
     * Method to create a materialized texture. It is called from the MaterializedTextureCreator to create the texture in the Map
     * @param map The TextureMap to register the textures to.
     * @param baseTexture The baseTexture to manipulate
     * @param buildSprites A List of textures already created. The upper Map holds te baseTexture as key and the lower map the material name as key.
     */
    void createMaterializedTextures(@Nonnull ITextureMap map, @Nonnull ResourceLocation baseTexture, @Nonnull Map<ResourceLocation, Map<String, TextureAtlasSprite>> buildSprites);


}
