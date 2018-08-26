package com.smithsmodding.armory.common.item.armor;

import com.google.common.collect.Lists;
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
import net.minecraft.client.util.ITooltipFlag;
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
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static com.smithsmodding.armory.api.util.client.TranslationKeys.Items.MultiArmor.Armor.TK_BROKEN;
import static com.smithsmodding.armory.api.util.client.TranslationKeys.Items.MultiArmor.Armor.TK_DURABILTIY;

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

        if (componentArmorCapability.isBroken())
        {
            return new ArmorProperties(0, 0, 0);
        }

        final float armorDefence = ArmorHelper.getModifyableCapabilityValue(armor, ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY).floatValue();
        final float armorThoughness = ArmorHelper.getModifyableCapabilityValue(armor, ModCapabilities.MOD_ARMOR_THOUGHNESS_CAPABILITY).floatValue();
        final float armorRatio = ArmorHelper.getModifyableCapabilityValue(armor, ModCapabilities.MOD_ARMOR_ABSORPTION_RATIO_CAPABILITY).floatValue();

        final ArmorProperties properties = new ArmorProperties(Integer.MAX_VALUE, armorRatio, componentArmorCapability.getCurrentDurability());
        properties.Armor = armorDefence;
        properties.Toughness = armorThoughness;

        return properties;
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
        if (!armor.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        IMultiComponentArmorCapability componentArmorCapability = armor.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        if (componentArmorCapability.isBroken())
        {
            return 0;
        }

        return ArmorHelper.getModifyableCapabilityValue(armor, ModCapabilities.MOD_ARMOR_DEFENCE_CAPABILITY).intValue();
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
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        IMultiComponentArmorCapability componentArmorCapability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        if (componentArmorCapability.isBroken())
        {
            return;
        }

        componentArmorCapability.decreaseCurrentDurability(damage);
    }

    @Override
    @SideOnly(Side.CLIENT)
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
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(final ItemStack stack, @Nullable final World worldIn, final List<String> tooltip, final ITooltipFlag flagIn)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        final IMultiComponentArmorCapability capability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
        if (capability.isBroken())
        {
            tooltip.add(TextFormatting.RED + I18n.translateToLocal(TK_BROKEN) + TextFormatting.RESET);
            tooltip.add("");
        }


        tooltip.add(String.format(TextFormatting.AQUA + "%s %d/%d" + TextFormatting.RESET,
          I18n.translateToLocal(TK_DURABILTIY),
          capability.getCurrentDurability(),
          ArmorHelper.getModifyableCapabilityValue(stack, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).intValue()));

        super.addInformation(stack, worldIn, tooltip, flagIn);
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

    /**
     * returns a list of items with the same ID, but different meta (eg: dye returns 16 items)
     */
    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems)
    {
        if (tab != getCreativeTab())
        {
            return;
        }

        IMultiComponentArmor armorType = ArmorHelper.getArmorForItem(this);

        for (ICoreArmorMaterial coreArmorMaterial : IArmoryAPI.Holder.getInstance().getRegistryManager().getCoreMaterialRegistry())
        {
            subItems.add(IArmoryAPI.Holder.getInstance().getHelpers().getFactories().getMLAFactory().buildNewMLAArmor(armorType, coreArmorMaterial, Lists.newArrayList()));
        }
    }

    /**
     * Returns the font renderer used to render tooltips and overlays for this item.
     * Returning null will use the standard font renderer.
     *
     * @param stack The current item stack
     * @return A instance of FontRenderer or null to use default
     */
    @Nullable
    @Override
    @SideOnly(Side.CLIENT)
    //Using full imports in this method to prevent ClassNotFound sidedness issues.
    public net.minecraft.client.gui.FontRenderer getFontRenderer(final ItemStack stack)
    {
        return com.smithsmodding.smithscore.client.proxy.CoreClientProxy.getMultiColoredFontRenderer();
    }

    /**
     * Determines if the durability bar should be rendered for this item.
     * Defaults to vanilla stack.isDamaged behavior.
     * But modders can use this for any data they wish.
     *
     * @param stack The current Item Stack
     * @return True if it should render the 'durability' bar.
     */
    @Override
    public boolean showDurabilityBar(final ItemStack stack)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        final IMultiComponentArmorCapability capability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        final int maxDurability = ArmorHelper.getModifyableCapabilityValue(stack, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).intValue();
        final int currentDurability = capability.getCurrentDurability();

        return maxDurability != currentDurability;
    }

    /**
     * Queries the percentage of the 'Durability' bar that should be drawn.
     *
     * @param stack The current ItemStack
     * @return 0.0 for 100% (no damage / full bar), 1.0 for 0% (fully damaged / empty bar)
     */
    @Override
    public double getDurabilityForDisplay(final ItemStack stack)
    {
        if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
        {
            throw new IllegalArgumentException("Armor is not an instance of multicomponent armor.");
        }

        final IMultiComponentArmorCapability capability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);

        final float maxDurability = ArmorHelper.getModifyableCapabilityValue(stack, ModCapabilities.MOD_ARMOR_DURABILITY_CAPABILITY).floatValue();
        final int currentDurability = capability.getCurrentDurability();

        return 1f - (currentDurability / maxDurability);
    }
}
