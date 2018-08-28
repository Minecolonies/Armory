package com.ldtteam.armory.common.tileentity.guimanagers;

import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.crafting.blacksmiths.recipe.VanillaAnvilRecipe;
import com.ldtteam.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.ldtteam.smithscore.client.events.gui.GuiInputEvent;
import com.ldtteam.smithscore.client.gui.components.core.IGUIComponent;
import com.ldtteam.smithscore.client.gui.management.TileStorageBasedGUIManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

import javax.annotation.Nonnull;

/**
 * Created by Marc on 14.02.2016.
 */
public class TileEntityBlackSmithsAnvilGuiManager extends TileStorageBasedGUIManager {

    TileEntityBlackSmithsAnvil anvil;

    public TileEntityBlackSmithsAnvilGuiManager(TileEntityBlackSmithsAnvil anvil) {
        this.anvil = anvil;
    }

    @Override
    public void onInput(GuiInputEvent.InputTypes types, String componentId, String input) {
        if (types != GuiInputEvent.InputTypes.TEXTCHANGED) return;

        (anvil.getState()).setItemName(input);
    }

    @Override
    public String getLabelContents(@Nonnull IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.TEXTBOX))
            return (anvil.getState()).getItemName();

        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.EXPERIENCELABEL)) {
            if (anvil.getCurrentRecipe() == null) return "";
            if (!(anvil.getCurrentRecipe() instanceof VanillaAnvilRecipe)) return "";

            return String.valueOf(((VanillaAnvilRecipe) anvil.getCurrentRecipe()).getRequiredLevelsPerPlayer());
        }

        if (component.getID().contains(".material"))
        {
            return anvil.getState().getMaterial().getTextFormatting() + I18n.translateToLocal(anvil.getState().getMaterial().getTranslationKey()) + TextFormatting.RESET;
        }

        if (component.getID().contains(".maxDurability"))
        {
            return anvil.getState().getMaterial().getDurability().toString();
        }

        if (component.getID().contains(".currentDurability"))
        {
            return ((Integer) anvil.getState().getRemainingUses()).toString();
        }

        return "UNKNOWN";
    }

    @Override
    public float getProgressBarValue(@Nonnull IGUIComponent component) {
        if (component.getID().equals(References.InternalNames.GUIComponents.Anvil.EXTENDEDCRAFTING + ".Progress")) {
            if (anvil.getCurrentRecipe() == null) {
                return 0F;
            }

            return ((anvil.getState()).getCraftingprogress() / (float) anvil.getCurrentRecipe().getProgress());
        }

        return 0f;
    }


}
