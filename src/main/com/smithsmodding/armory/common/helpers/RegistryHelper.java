package com.smithsmodding.armory.common.helpers;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.material.anvil.IAnvilMaterial;
import com.smithsmodding.armory.api.common.material.armor.IAddonArmorMaterial;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.common.material.core.RegistryMaterialWrapper;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.function.Predicate;

public class RegistryHelper implements com.smithsmodding.armory.api.common.helpers.IRegistryHelper
{
    private static RegistryHelper ourInstance = new RegistryHelper();

    public static RegistryHelper getInstance()
    {
        return ourInstance;
    }

    private RegistryHelper()
    {
    }

    /**
     * Default search method used to search and find in a registry.
     *
     * @param registry The registry to look in.
     * @param predicate The predicate to search with.
     * @param <T> The type you are looking for.
     * @return An optional containing the searched value if found.
     */
    @Override
    public <T extends IForgeRegistryEntry<T>> Optional<T> findInRegistryUsingPredicate(@Nonnull final IForgeRegistry<T> registry, @Nonnull final Predicate<T> predicate)
    {
        return registry.getValues().stream().filter(predicate).findFirst();
    }

    /**
     * Finds a {@link IMaterial} by searching through the entire material list (core, addon, anvil, etc) that matches the predicate and returns the first found.
     * @param predicate The predicate used to search with.
     * @return An optional containing the searched material if found.
     */
    @Override
    public Optional<IMaterial> findMaterialUsingPredicate(@Nonnull final Predicate<IMaterial> predicate)
    {
        return findInRegistryUsingPredicate(IArmoryAPI.Holder.getInstance().getRegistryManager().getCombinedMaterialRegistry(),
          (RegistryMaterialWrapper m) -> predicate.test(m.getWrapped()))
                 .map(RegistryMaterialWrapper::getWrapped);
    }

    /**
     * Finds a {@link IMaterial} by searching through the entire material list (core, addon, anvil, etc) that matches the predicate and returns the first found.
     * @param predicate The predicate used to search with.
     * @return An optional containing the searched material if found.
     */
    @Override
    public Optional<ICoreArmorMaterial> findCoreMaterialUsingPredicate(@Nonnull final Predicate<ICoreArmorMaterial> predicate)
    {
        return findInRegistryUsingPredicate(IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry(),
          predicate);
    }

    /**
     * Finds a {@link IMaterial} by searching through the entire material list (core, addon, anvil, etc) that matches the predicate and returns the first found.
     * @param predicate The predicate used to search with.
     * @return An optional containing the searched material if found.
     */
    @Override
    public Optional<IAddonArmorMaterial> findAddonMaterialUsingPredicate(@Nonnull final Predicate<IAddonArmorMaterial> predicate)
    {
        return findInRegistryUsingPredicate(IArmoryAPI.Holder.getInstance().getRegistryManager().getAddonArmorMaterialRegistry(),
          predicate);
    }

    /**
     * Finds a {@link IMaterial} by searching through the entire material list (core, addon, anvil, etc) that matches the predicate and returns the first found.
     * @param predicate The predicate used to search with.
     * @return An optional containing the searched material if found.
     */
    @Override
    public Optional<IAnvilMaterial> findAnvilMaterialUsingPredicate(@Nonnull final Predicate<IAnvilMaterial> predicate)
    {
        return findInRegistryUsingPredicate(IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilMaterialRegistry(),
          predicate);
    }
}
