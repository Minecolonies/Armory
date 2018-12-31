package com.ldtteam.armory.common.material;

import com.ldtteam.armory.api.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.AnvilRecipe;
import com.ldtteam.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.ldtteam.armory.api.common.material.anvil.IAnvilMaterial;
import com.ldtteam.armory.api.util.references.ModBlocks;
import com.ldtteam.armory.api.util.references.ModHeatableObjects;
import com.ldtteam.armory.api.util.references.ModHeatedObjectTypes;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.smithscore.client.textures.ITextureController;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Marc on 22.02.2016.
 */
public class MedievalAnvilMaterial extends IForgeRegistryEntry.Impl<IAnvilMaterial> implements IAnvilMaterial {
    private final String translationKey;
    private final String textFormatting;
    private final String oreDictionaryIdentifier;
    
    private final Float meltingPoint;
    private final Float vaporizingPoint;
    private final Integer meltingTime;
    private final Integer vaporizingTime;
    private final Float heatCoefficient;

    private final Integer durability;
    
    private final String textureOverrideIdentifier;

    private ITextureController renderInfo;
    private Fluid fluid;

    public MedievalAnvilMaterial(String translationKey, String textFormatting, String oreDictionaryIdentifier, Float meltingPoint, Float vaporizingPoint, Integer meltingTime, Integer vaporizingTime, Float heatCoefficient, Integer durability) {
        this.translationKey = translationKey;
        this.textFormatting = textFormatting;
        this.oreDictionaryIdentifier = oreDictionaryIdentifier;
        this.meltingPoint = meltingPoint;
        this.vaporizingPoint = vaporizingPoint;
        this.meltingTime = meltingTime;
        this.vaporizingTime = vaporizingTime;
        this.heatCoefficient = heatCoefficient;
        this.durability = durability;

        this.textureOverrideIdentifier = oreDictionaryIdentifier;
    }


    /**
     * Method to get the TranslationKey.
     *
     * @return The translation key of the Material.
     * medieval.
     */
    @Nonnull
    @Override
    public String getTranslationKey() {
        return translationKey;
    }

    /**
     * Method to get the markup.
     *
     * @return The markup. Default is TextFormatting.Reset
     */
    @Nonnull
    @Override
    public String getTextFormatting() {
        return textFormatting;
    }


    /**
     * Method to get the Identifier inside the OreDictionary for a Material.
     *
     * @return The String that identifies this material in the OreDictionary. IE: TK_ANVIL_IRON, TK_ANVIL_STONE etc.
     */
    @Nullable
    @Override
    public String getOreDictionaryIdentifier() {
        return oreDictionaryIdentifier;
    }

    /**
     * Getter for the Melting Point of this material.
     *
     * @return The melting point of this material.
     */
    @Nonnull
    @Override
    public Float getMeltingPoint() {
        return meltingPoint;
    }

    /**
     * Getter for the VaporizingPoint of this material.
     *
     * @return The vaporizing point og this material.
     */
    @Nonnull
    @Override
    public Float getVaporizingPoint() {
        return vaporizingPoint;
    }

    /**
     * Getter for the melting time of this Material
     *
     * @return The melting time of this material
     */
    @Nonnull
    @Override
    public Integer getMeltingTime() {
        return meltingTime;
    }

    /**
     * Getter for the vaporizing time of this Material
     *
     * @return The vaporizing time of this material
     */
    @Nonnull
    @Override
    public Integer getVaporizingTime() {
        return vaporizingTime;
    }

    /**
     * Method to get the getDurability of the Anvil when it is made out of this Material
     *
     * @return A value bigger then 0, Integer.MaxValue means unbreakable.
     */
    @Nonnull
    @Override
    public Integer getDurability() {
        return durability;
    }

    /**
     * Method used to get the RenderInfo used to change the Texture of the Model if need be.
     *
     * @return The RenderInfo used to modify the Texture of the model.
     * medieval.
     */
    @Nonnull
    @Override
    public ITextureController getRenderInfo() {
        return renderInfo;
    }

    /**
     * Setter for the RenderInfo used to change the Texture of the Model if need be.
     *
     * @param info The RenderInfo used to modify the Texture of the Model.
     * @return The instance this method was called on.
     * medieval.
     */
    @Nonnull
    @Override
    public IAnvilMaterial setRenderInfo(@Nonnull ITextureController info) {
        this.renderInfo = info;
        return this;
    }

    /**
     * Method to get the Override for the texture.
     * Has to be supplied so that resourcepack makers can override the behaviour if they fell the need to do it.
     *
     * @return The override identifier for overloading the programmatic behaviour of the RenderInfo.
     *
     */
    @Nonnull
    @Override
    public String getTextureOverrideIdentifier() {
        return textureOverrideIdentifier;
    }

    @Nonnull
    @Override
    public Float getHeatCoefficient() {
        return heatCoefficient;
    }

    /**
     * Method to get the fluid if this material is molten.
     *
     * @return The fluid that represents this material.
     */
    @Nonnull
    @Override
    public Fluid getFluidForMaterial() {
        return fluid;
    }

    /**
     * Setter for the fluid that represents this material.
     *
     * @param fluid The new molten material.
     *
     * @return The instance this was called upon.
     */
    @Nonnull
    @Override
    public IAnvilMaterial setFluidForMaterial(Fluid fluid) {
        this.fluid = fluid;
        return this;
    }

    /**
     * The recipe that is used to craft an Anvil with this as a Main material.
     *
     * @return The recipe for the anvil with this as a main Material.
     */
    @Nullable
    @Override
    public IAnvilRecipe getRecipeForAnvil() {
        ItemStack stack = new ItemStack(Item.getItemFromBlock(ModBlocks.BL_ANVIL));

        NBTTagCompound compound = new NBTTagCompound();
        compound.setString(References.NBTTagCompoundData.TE.Anvil.MATERIAL, getRegistryName().toString());

        stack.setTagCompound(compound);
        return new AnvilRecipe()
                .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.PLATE, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.BLOCK, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(ModHeatedObjectTypes.INGOT, ModHeatableObjects.ITEMSTACK, this, getMeltingPoint() * 0.5F * 0.85F, getMeltingPoint() * 0.5F * 0.95F)))
                .setProgress(25).setResult(stack).setHammerUsage(30);
    }

    /**
     * Determines if this object has support for the capability in question on the specific side.
     * The return value of this MIGHT change during runtime if this object gains or looses support
     * for a capability.
     * <p>
     * Example:
     * A Pipe getting a cover placed on one side causing it loose the Inventory attachment function for that side.
     * <p>
     * This is a light weight version of getCapability, intended for metadata uses.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return True if this object supports the capability.
     */
    @Override
    public boolean hasCapability(@Nonnull final Capability<?> capability, @Nullable final EnumFacing facing)
    {
        return false;
    }

    /**
     * Retrieves the handler for the capability requested on the specific side.
     * The return value CAN be null if the object does not support the capability.
     * The return value CAN be the same for multiple faces.
     *
     * @param capability The capability to check
     * @param facing     The Side to check from:
     *                   CAN BE NULL. Null is defined to represent 'internal' or 'self'
     * @return The requested capability. Returns null when {@link #hasCapability(Capability, EnumFacing)} would return false.
     */
    @Nullable
    @Override
    public <T> T getCapability(@Nonnull final Capability<T> capability, @Nullable final EnumFacing facing)
    {
        return null;
    }

    @Override
    public String toString()
    {
        return "MedievalAnvilMaterial{" +
                 "translationKey='" + translationKey + '\'' +
                 ", textFormatting='" + textFormatting + '\'' +
                 ", oreDictionaryIdentifier='" + oreDictionaryIdentifier + '\'' +
                 ", meltingPoint=" + meltingPoint +
                 ", vaporizingPoint=" + vaporizingPoint +
                 ", meltingTime=" + meltingTime +
                 ", vaporizingTime=" + vaporizingTime +
                 ", heatCoefficient=" + heatCoefficient +
                 ", durability=" + durability +
                 ", textureOverrideIdentifier='" + textureOverrideIdentifier + '\'' +
                 ", renderInfo=" + renderInfo +
                 ", fluid=" + fluid +
                 '}';
    }
}
