package com.ldtteam.armory.api.util.references;

import com.ldtteam.armory.api.IArmoryAPI;
import com.ldtteam.armory.api.common.capability.*;
import com.ldtteam.armory.api.common.capability.armor.IArmorAbsorptionRatioCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorDefenceCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorDurabilityCapability;
import com.ldtteam.armory.api.common.capability.armor.IArmorToughnessCapability;
import com.ldtteam.armory.api.common.fluid.IMoltenMetalAcceptor;
import com.ldtteam.armory.api.common.fluid.IMoltenMetalProvider;
import com.ldtteam.smithscore.util.common.capabilities.NullFactory;
import com.ldtteam.smithscore.util.common.capabilities.NullStorage;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;


/**
 * Author Orion (Created on: 25.07.2016)
 */
public class ModCapabilities {

    @CapabilityInject(IMoltenMetalAcceptor.class)
    public static Capability<IMoltenMetalAcceptor> MOD_MOLTENMETAL_ACCEPTOR_CAPABILITY;

    @CapabilityInject(IMoltenMetalProvider.class)
    public static Capability<IMoltenMetalProvider> MOD_MOLTENMETAL_PROVIDER_CAPABILITY;

    @CapabilityInject(IMultiComponentArmorCapability.class)
    public static Capability<IMultiComponentArmorCapability> MOD_MULTICOMPONENTARMOR_CAPABILITY;

    @CapabilityInject(IMaterializedStackCapability.class)
    public static Capability<IMaterializedStackCapability> MOD_MATERIALIZEDSSTACK_CAPABIITY;

    @CapabilityInject(IHeatedObjectCapability.class)
    public static Capability<IHeatedObjectCapability> MOD_HEATEDOBJECT_CAPABILITY;

    @CapabilityInject(IHeatableObjectCapability.class)
    public static Capability<IHeatableObjectCapability> MOD_HEATABLEOBJECT_CAPABILITY;

    @CapabilityInject(IArmorComponentStackCapability.class)
    public static Capability<IArmorComponentStackCapability> MOD_ARMORCOMPONENT_CAPABILITY;

    @CapabilityInject(IArmorDefenceCapability.class)
    public static Capability<IArmorDefenceCapability> MOD_ARMOR_DEFENCE_CAPABILITY;

    @CapabilityInject(IArmorToughnessCapability.class)
    public static Capability<IArmorToughnessCapability> MOD_ARMOR_THOUGHNESS_CAPABILITY;

    @CapabilityInject(IArmorDurabilityCapability.class)
    public static Capability<IArmorDurabilityCapability> MOD_ARMOR_DURABILITY_CAPABILITY;

    @CapabilityInject(IArmorAbsorptionRatioCapability.class)
    public static Capability<IArmorAbsorptionRatioCapability> MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY;

    static {
        CapabilityManager.INSTANCE.register(IMoltenMetalAcceptor.class, new NullStorage<>(), new NullFactory<>());
        CapabilityManager.INSTANCE.register(IMoltenMetalProvider.class, new NullStorage<>(), new NullFactory<>());

        CapabilityManager.INSTANCE.register(IMultiComponentArmorCapability.class, new IMultiComponentArmorCapability.Storage(), IMultiComponentArmorCapability.Impl::new);

        CapabilityManager.INSTANCE.register(IMaterializedStackCapability.class, new IMaterializedStackCapability.Storage(), IMaterializedStackCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IHeatableObjectCapability.class, new IHeatableObjectCapability.Storage(), IHeatableObjectCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IHeatedObjectCapability.class, new IHeatedObjectCapability.Storage(), IHeatedObjectCapability.Impl::new);

        CapabilityManager.INSTANCE.register(IArmorComponentStackCapability.class, new Capability.IStorage<IArmorComponentStackCapability>() {

            @Override
            public NBTBase writeNBT(Capability<IArmorComponentStackCapability> capability, IArmorComponentStackCapability instance, EnumFacing side) {
                NBTTagCompound compound = new NBTTagCompound();

                compound.setString(References.NBTTagCompoundData.Item.ItemComponent.EXTENSION, instance.getExtension().getRegistryName().toString());

                return compound;
            }

            @Override
            public void readNBT(Capability<IArmorComponentStackCapability> capability, IArmorComponentStackCapability instance, EnumFacing side, NBTBase nbt) {
                NBTTagCompound compound = (NBTTagCompound) nbt;

                instance.setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(compound.getString(References.NBTTagCompoundData.Item.ItemComponent.EXTENSION))));
            }
        }, IArmorComponentStackCapability.Impl::new);

        CapabilityManager.INSTANCE.register(IArmorDefenceCapability.class, new IArmorDefenceCapability.Storage(), IArmorDefenceCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IArmorToughnessCapability.class, new IArmorToughnessCapability.Storage(), IArmorToughnessCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IArmorDurabilityCapability.class, new IArmorDurabilityCapability.Storage(), IArmorDurabilityCapability.Impl::new);
        CapabilityManager.INSTANCE.register(IArmorAbsorptionRatioCapability.class, new IArmorAbsorptionRatioCapability.Storage(), IArmorAbsorptionRatioCapability.Impl::new);
    }

}
