package com.smithsmodding.armory.api.common.helpers;

import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Predicate;

public interface IRegistryHelper
{
    <T extends IForgeRegistryEntry<T>> Optional<T> findInRegistryUsingPredicate(@Nonnull IForgeRegistry<T> registry, @Nonnull Predicate<T> predicate);

    Optional<IMaterial> findMaterialUsingPredicate(@Nonnull Predicate<IMaterial> predicate);

    Optional<ICoreArmorMaterial> findCoreMaterialUsingPredicate(@Nonnull Predicate<ICoreArmorMaterial> predicate);

    Optional<IAddonArmorMaterial> findAddonMaterialUsingPredicate(@Nonnull Predicate<IAddonArmorMaterial> predicate);

    Optional<IAnvilMaterial> findAnvilMaterialUsingPredicate(@Nonnull Predicate<IAnvilMaterial> predicate);
}
