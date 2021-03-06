package com.ldtteam.armory.common.item;

import com.ldtteam.armory.api.common.heatable.IHeatedObjectType;
import com.ldtteam.armory.api.util.references.ModCreativeTabs;
import com.ldtteam.armory.api.util.references.ModHeatedObjectTypes;
import com.ldtteam.armory.api.util.references.References;

/**
 * Created by Orion
 * Created on 17.05.2015
 * 14:41
 * <p>
 * Copyrighted according to Project specific license
 */
public class ItemHeatableNugget extends ItemHeatableResource {

    public ItemHeatableNugget() {
        this.setMaxStackSize(64);
        this.setCreativeTab(ModCreativeTabs.COMPONENTS);
        this.setUnlocalizedName(References.InternalNames.Items.IN_METALNUGGET);
        this.setRegistryName(References.InternalNames.Items.IN_METALNUGGET);
    }

    @Override
    public IHeatedObjectType getHeatableObjectType() {
        return ModHeatedObjectTypes.NUGGET;
    }

}
