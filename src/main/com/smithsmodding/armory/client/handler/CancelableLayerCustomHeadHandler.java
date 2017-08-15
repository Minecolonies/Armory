package com.smithsmodding.armory.client.handler;

import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.smithscore.client.events.render.LayerCustomHeadRenderEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Event handler used to determine if the SC LayerCustomHead renderer should render the head.
 */
public class CancelableLayerCustomHeadHandler
{
    /**
     * Event handler for LayerCustomHeadRenderEvent event.
     *
     * @param event The event to handle.
     */

    @SubscribeEvent
    public void handle(@NotNull final LayerCustomHeadRenderEvent event)
    {
        final EntityLivingBase base = event.getEntityLivingBase();

        ItemStack stack = base.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        event.setCanceled(stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null));
    }
}
