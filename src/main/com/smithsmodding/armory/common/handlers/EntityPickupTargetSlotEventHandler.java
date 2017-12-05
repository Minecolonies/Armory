package com.smithsmodding.armory.common.handlers;

import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.smithscore.common.events.EntityPickupTargetSlotEvent;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityPickupTargetSlotEventHandler
{

    //TODO: Fix the rendering on Zombies, Skeleton and ArmorStands.
    //@SubscribeEvent
    public void onEntityPickupTargetSlot(final EntityPickupTargetSlotEvent event)
    {
        final ItemStack stack = event.getStack();
        if (stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            IMultiComponentArmorCapability armorCapability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

            event.setSlot(armorCapability.getArmorType().getEquipmentSlot());
        }
    }
}
