package com.smithsmodding.armory.api.common.armor.callback;

import com.smithsmodding.armory.api.common.capability.armor.IArmorCapability;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/21/2017.
 */
@FunctionalInterface
public interface IDefaultCapabilitiesRetrievalCallback {
    /**
     * Method to get capabilities from the Callback.
     * @return All the default capabilities this Armor should provides.
     */
    @Nonnull
    HashMap<Capability<? extends IArmorCapability>, Object> get();
}