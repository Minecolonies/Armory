package com.smithsmodding.armory.client.logic.initialization;

import com.smithsmodding.armory.api.common.initialization.IInitializationComponent;
import com.smithsmodding.armory.client.render.entity.LayerMultiComponentArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.RenderArmorStand;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderSkeleton;
import net.minecraft.client.renderer.entity.RenderZombie;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import javax.annotation.Nonnull;
import java.util.Map;

/**
 * Created by marcf on 1/25/2017.
 */
public class ClientSystemInitializer extends IInitializationComponent.Impl {

    private static final ClientSystemInitializer INSTANCE = new ClientSystemInitializer();

    public static ClientSystemInitializer getInstance () {
        return INSTANCE;
    }

    private ClientSystemInitializer () {
    }

    @Override
    public void onInit(@Nonnull final FMLInitializationEvent initializationEvent)
    {
        registerRenderLayers();
    }

    private void registerRenderLayers()
    {
        Map<String, RenderPlayer> skinMap = Minecraft.getMinecraft().getRenderManager().getSkinMap();

        RenderPlayer renderPlayer = skinMap.get("default");
        renderPlayer.addLayer(new LayerMultiComponentArmor(new ModelPlayer(0f, false), renderPlayer.getMainModel()));

        renderPlayer = skinMap.get("slim");
        renderPlayer.addLayer(new LayerMultiComponentArmor(new ModelPlayer(0f, true), renderPlayer.getMainModel()));

        RenderZombie renderZombie = (RenderZombie) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityZombie.class);
        renderZombie.addLayer(new LayerMultiComponentArmor(new ModelZombie(), renderZombie.getMainModel()));

        RenderSkeleton renderSkeleton = (RenderSkeleton) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntitySkeleton.class);
        renderSkeleton.addLayer(new LayerMultiComponentArmor(new ModelSkeleton(), renderSkeleton.getMainModel()));

        RenderArmorStand renderArmorStand = (RenderArmorStand) Minecraft.getMinecraft().getRenderManager().entityRenderMap.get(EntityArmorStand.class);
        renderArmorStand.addLayer(new LayerMultiComponentArmor(new ModelArmorStandArmor(), renderArmorStand.getMainModel()));
    }


}
