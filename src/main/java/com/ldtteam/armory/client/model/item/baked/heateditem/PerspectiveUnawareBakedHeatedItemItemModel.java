package com.ldtteam.armory.client.model.item.baked.heateditem;

import com.ldtteam.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.vecmath.Matrix4f;
import java.util.HashMap;

/**
 * Created by Marc on 08.12.2015.
 */
public class PerspectiveUnawareBakedHeatedItemItemModel extends BakedWrappedModel
{

    private boolean inventory = false;

    @Nonnull
    private HashMap<ItemCameraTransforms.TransformType, IBakedModel> modelHashMap = new HashMap<>();

    public PerspectiveUnawareBakedHeatedItemItemModel(IBakedModel standard) {
        super(standard);
    }

    public void setInventoryMode(boolean mode) {
        inventory = mode;
    }

    public void registerModel(ItemCameraTransforms.TransformType cameraTransforms, IBakedModel model)
    {
        modelHashMap.put(cameraTransforms, model);
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
        if (cameraTransformType == ItemCameraTransforms.TransformType.GUI)
        {
            return getModel(ItemCameraTransforms.TransformType.GUI).handlePerspective(cameraTransformType);
        }

        return getParentModel().handlePerspective(cameraTransformType);
    }

    public IBakedModel getModel(ItemCameraTransforms.TransformType cameraTransforms)
    {
        return modelHashMap.get(cameraTransforms);
    }

}
