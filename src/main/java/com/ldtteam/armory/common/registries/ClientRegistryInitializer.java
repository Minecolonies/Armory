package com.ldtteam.armory.common.registries;

import com.ldtteam.armory.api.client.textures.creation.ICreationController;
import com.ldtteam.armory.api.util.references.ModRegistries;
import net.minecraftforge.registries.RegistryBuilder;

/**
 * Created by marcf on 1/14/2017.
 */
public class ClientRegistryInitializer extends CommonRegistryInitializer {

    @Override
    public void initialize() {
        super.initialize();

        RegistryManager.getInstance().textureCreationControllerRegistry  = new RegistryBuilder<ICreationController>()
                .setName(ModRegistries.TEXTURECREATIONCONTROLLER)
                .setType(ICreationController.class)
                .setIDRange(0, 255)
                .create();
    }
}
