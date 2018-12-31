package com.ldtteam.armory.client.model.item.baked.components;

import com.ldtteam.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by Marc on 08.12.2015.
 */
public class BakedTemperatureBarModel extends BakedWrappedModel {

    @Nonnull
    ArrayList<IBakedModel> textures = new ArrayList<>();

    /**
     * Creates a new Baked model from its parent for a single Component.
     *
     * @param base The models base.
     */
    public BakedTemperatureBarModel(IBakedModel base) {
        super(base);
    }

    public void addTexture(IBakedModel texture) {
        textures.add(texture);
    }

    public IBakedModel getModel(int index) {
        if (index < 0 || index >= textures.size())
            return textures.get(0);

        return textures.get(index);
    }

    public int getModelCount() {
        return textures.size();
    }
}
