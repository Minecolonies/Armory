package com.smithsmodding.armory.api.common.capability.armor;

import java.util.function.Function;

public interface IValueModifyingCapability<T extends IValueModifyingCapability<T, V>, V extends Number> extends IValueContainingCapability<T, V>, Function<Number, Number>
{
    @Override
    default Number apply(Number v)
    {
        return getType().apply(v, getValue());
    }

    /**
     * Method to get the type of the capability.
     *
     * @return The type.
     */
    ValueModificationType getType();

    /**
     * Method to set the type of the capability.
     *
     * @param type The new type.
     * @return The instance this was called upon.
     */
    T setType(final ValueModificationType type);

    class Impl<T extends IValueModifyingCapability<T, V>, V extends Number> extends IValueContainingCapability.Impl<T, V> implements IValueModifyingCapability<T, V>
    {

        private ValueModificationType type;

        @Override
        public ValueModificationType getType()
        {
            return type;
        }

        @Override
        public T setType(final ValueModificationType type)
        {
            this.type = type;
            return (T) this;
        }
    }
}
