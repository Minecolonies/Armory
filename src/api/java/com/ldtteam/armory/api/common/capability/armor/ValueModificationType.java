package com.ldtteam.armory.api.common.capability.armor;

import java.util.function.BiFunction;

public enum ValueModificationType implements BiFunction<Number, Number, Number>
{

    ADD(((number, number2) -> number.doubleValue() + number2.doubleValue())),
    MULTIPLY(((number, number2) -> number.doubleValue() * number2.doubleValue())),
    SET((number, number2) -> number2);

    private final BiFunction<Number, Number, Number> callback;

    ValueModificationType(final BiFunction<Number, Number, Number> callback)
    {
        this.callback = callback;
    }

    @Override
    public Number apply(final Number number, final Number number2)
    {
        return callback.apply(number, number2);
    }
}
