package com.smithsmodding.armory.common.block.properties;

import net.minecraftforge.common.property.IUnlistedProperty;

public class PropertyAnvilDurability implements IUnlistedProperty<Integer>
{
    private final String name;

    public PropertyAnvilDurability(final String name) {this.name = name;}

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public boolean isValid(final Integer value)
    {
        return value > -1;
    }

    @Override
    public Class<Integer> getType()
    {
        return Integer.class;
    }

    @Override
    public String valueToString(final Integer value)
    {
        return value.toString();
    }
}
