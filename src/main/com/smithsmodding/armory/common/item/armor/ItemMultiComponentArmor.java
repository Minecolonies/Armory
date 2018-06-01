package com.smithsmodding.armory.common.item.armor;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.client.render.provider.model.IModelProvider;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.material.armor.ICoreArmorMaterial;
import com.smithsmodding.armory.api.common.material.core.IMaterial;
import com.smithsmodding.armory.api.util.common.armor.ArmorHelper;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModCreativeTabs;
import com.smithsmodding.armory.api.util.references.References;
import com.smithsmodding.armory.client.model.item.baked.BakedMultiLayeredArmorItemModel;
import com.smithsmodding.smithscore.common.capability.SmithsCoreCapabilityDispatcher;
import com.smithsmodding.smithscore.util.CoreReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * Class that represents MultiComponentArmor
 */
public class ItemMultiComponentArmor extends Item implements ISpecialArmor, IModelProvider
{


    public ItemMultiComponentArmor(ResourceLocation internalName, String translationKey) {
        this.setRegistryName(internalName);
        this.setUnlocalizedName(translationKey);
        this.setMaxStackSize(1);
        this.setCreativeTab(ModCreativeTabs.ARMOR);
    }

    /**
     * Retrieves the modifiers to be used when calculating armor damage.
     * <p>
     * Armor will higher priority will have damage applied to them before
     * lower priority ones. If there are multiple pieces of armor with the
     * same priority, damage will be distributed between them based on there
     * absorption ratio.
     *
     * @param player The entity wearing the armor.
     * @param armor  The ItemStack of the armor item itself.
     * @param source The source of the damage, which can be used to alter armor
     *               properties based on the type or source of damage.
     * @param damage The total damage being applied to the entity
     * @param slot   The armor slot the item is in.
     * @return A ArmorProperties instance holding information about how the armor effects damage.
     */
    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        if (!armor.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        IMultiComponentArmorCapability componentArmorCapability = armor.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        IMultiComponentArmor componentArmor = componentArmorCapability.getArmorType();

        float armorDefence = componentArmor.getCapability(ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY, null).getValue();
        float armorThoughness = componentArmor.getCapability(ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY, null).getValue();

        ICoreArmorMaterial coreArmorMaterial = componentArmorCapability.getMaterial();
        armorDefence *= coreArmorMaterial.
                          armorThoughness *= componentArmor




        return new ArmorProperties(Integer.MAX_VALUE, 1, Integer.MAX_VALUE);
    }

    /**
     * Get the displayed effective armor.
     *
     * @param player The player wearing the armor.
     * @param armor  The ItemStack of the armor item itself.
     * @param slot   The armor slot the item is in.
     * @return The number of armor points for display, 2 per shield.
     */
    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        return 0;
    }

    /**
     * Applies damage to the ItemStack. The mod is responsible for reducing the
     * item durability and stack size. If the stack is depleted it will be cleaned
     * up automatically.
     *
     * @param entity The entity wearing the armor
     * @param stack  The ItemStack of the armor item itself.
     * @param source The source of the damage, which can be used to alter armor
     *               properties based on the type or source of damage.
     * @param damage The amount of damage being applied to the armor
     * @param slot   The armor slot the item is in.
     */
    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {

    }

    @Override
    public IBakedModel getRenderingModel(
      final @NotNull EntityLivingBase entityLiving, final @NotNull ItemStack itemStack, final @NotNull EntityEquipmentSlot armorSlot)
    {
        final IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(itemStack);
        if (model instanceof BakedMultiLayeredArmorItemModel)
        {
            return model;
        }

        return null;
    }

    @Override
    public boolean getHasSubtypes() {
        return true;
    }

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     *
     * @param tab
     * @param subItems
     */
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        IMultiComponentArmor armorType = ArmorHelper.getArmorForItem(this);

        for(ICoreArmorMaterial coreArmorMaterial : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry()) {
            subItems.add(IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getMLAFactory().buildNewMLAArmor(armorType, new ArrayList<>(), coreArmorMaterial.getBaseDurabilityForArmor(armorType), coreArmorMaterial));
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            return "Stack has No Data!";
        }

        if (stack.hasTagCompound())
        {
            if (stack.getTagCompound().hasKey(References.NBTTagCompoundData.CustomName))
            {
                return TextFormatting.ITALIC + stack.getTagCompound().getString(References.NBTTagCompoundData.CustomName) + TextFormatting.RESET;
            }
        }

        IMultiComponentArmorCapability capability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        IMultiComponentArmor armorType = capability.getArmorType();
        IMaterial material = capability.getMaterial();

        return material.getTextFormatting() + I18n.translateToLocal(material.getTranslationKey()) + TextFormatting.RESET + " "
                 + I18n.translateToLocal(armorType.getTranslationKey());
    }

    /**
     * Called from ItemStack.setItem, will hold extra data for the life of this ItemStack.
     * Can be retrieved from stack.getCapabilities()
     * The NBT can be null if this is not called from readNBT or if the item the stack is
     * changing FROM is different then this item, or the previous item had no capabilities.
     * <p>
     * This is called BEFORE the stacks item is set so you can use stack.getItem() to see the OLD item.
     * Remember that getItem CAN return null.
     *
     * @param stack The ItemStack
     * @param nbt   NBT of this item serialized, or null.
     * @return A holder instance associated with this ItemStack where you can hold capabilities for the life of this item.
     */
    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        if (stack.getItem() == null)
            return null;

        SmithsCoreCapabilityDispatcher internalParentDispatcher = new SmithsCoreCapabilityDispatcher();
        internalParentDispatcher.registerNewInstance(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY);

        if (nbt != null)
        {
            NBTTagCompound parentCompound =
              nbt.getCompoundTag(new ResourceLocation(CoreReferences.General.MOD_ID.toLowerCase(), CoreReferences.CapabilityManager.DEFAULT).toString());
            internalParentDispatcher.deserializeNBT(parentCompound);
        }

        return internalParentDispatcher;
    }

    /**
     * Determines if the specific ItemStack can be placed in the specified armor slot.
     *
     * @param stack     The ItemStack
     * @param armorType Armor slot ID: 0: Helmet, 1: Chest, 2: Legs, 3: Boots
     * @param entity    The entity trying to equip the armor
     * @return True if the given ItemStack can be inserted in the slot
     */
    @Override
    public boolean isValidArmor(final ItemStack stack, final EntityEquipmentSlot armorType, final Entity entity)
    {
        if (stack.isEmpty())
        {
            return false;
        }

        @Nullable final IMultiComponentArmor armor  = ArmorHelper.getArmorForItemName(stack.getItem().getRegistryName());

        if (armor == null)
        {
            return false;
        }

        return armor.getEquipmentSlot() == armorType;
    }

}
