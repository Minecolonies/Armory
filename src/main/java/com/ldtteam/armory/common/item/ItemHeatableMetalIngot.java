package com.ldtteam.armory.common.item;

import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.util.references.ModCreativeTabs;
import com.ldtteam.armory.api.util.references.ModHeatedObjectTypes;
import com.ldtteam.armory.api.util.references.References;

/**
 * Created by marcf on 1/26/2017.
 */
public class ItemHeatableMetalIngot extends ItemHeatableResource {

    public ItemHeatableMetalIngot() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_METALINGOT);
        this.setRegistryName(References.InternalNames.Items.IN_METALINGOT);
    }

    @Override
    public IHeatedObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.INGOT;
    }
}
