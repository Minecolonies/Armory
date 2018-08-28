package com.ldtteam.armory.common.item;
/*
 *   ItemHeatableMetalRing
 *   Created by: Orion
 *   Created on: 25-9-2014
 */

import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.util.references.ModCreativeTabs;
import com.ldtteam.armory.api.util.references.ModHeatedObjectTypes;
import com.ldtteam.armory.api.util.references.References;

public class ItemHeatableMetalChain extends ItemHeatableResource {

    public ItemHeatableMetalChain() {
        this.setMaxStackSize(16);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_METALCHAIN);
        this.setRegistryName(References.InternalNames.Items.IN_METALCHAIN);
    }

    @Override
    public IHeatedObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.CHAIN;
    }
}
