package com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.implementations;

import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmor;
import com.smithsmodding.armory.api.common.capability.IMultiComponentArmorCapability;
import com.smithsmodding.armory.api.common.crafting.blacksmiths.recipe.IAnvilRecipe;
import com.smithsmodding.armory.api.util.client.Colors;
import com.smithsmodding.armory.api.util.references.ModArmor;
import com.smithsmodding.armory.api.util.references.ModCapabilities;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.client.gui.components.ComponentReadOnly5X5CraftingGrid;
import com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.core.AbstractPage;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import com.smithsmodding.armory.compatibility.recipes.anvil.BlackSmithsAnvilRecipeWrapper;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentContentArea;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentItemStackDisplay;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentLabel;
import com.smithsmodding.smithscore.client.gui.hosts.IGUIBasedTabHost;
import com.smithsmodding.smithscore.client.gui.state.CoreComponentState;
import com.smithsmodding.smithscore.client.proxy.CoreClientProxy;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import com.smithsmodding.smithscore.util.common.positioning.Coordinate2D;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.smithsmodding.armory.api.util.client.TranslationKeys.Gui.Guide.Medieval.*;

public class MedievalPage extends AbstractPage
{
    private static ItemStack LOGO;

    static {
        LOGO = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, ModMaterials.Armor.Core.IRON, new ArrayList<>());
    }

    public MedievalPage(
      @Nonnull final IGUIBasedTabHost root)
    {
        super("medieval", root, LOGO, I18n.format(TK_MEDIEVAL));
    }

    @Override
    public void registerPageContents(@Nonnull final ComponentContentArea host)
    {
        final int pageSizeWidth = getSize().getWidth() - 14;

        host.registerNewComponent(new ComponentItemStackDisplay(host.getID() + ".header.left", host, new CoreComponentState(), new Coordinate2D(16,0), LOGO));

        final String headerText = I18n.format(TK_MEDIEVAL);
        final int maximumHeaderWidth = pageSizeWidth - 48;
        final int horizontalOffset = 16 + (maximumHeaderWidth - CoreClientProxy.getMultiColoredFontRenderer().getStringWidth(headerText)) / 2;

        host.registerNewComponent(new ComponentLabel(host.getID() + ".header.text", host, new CoreComponentState(), new Coordinate2D(horizontalOffset, (16-CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT) / 2), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), headerText));

        final String sectionOneTitleText = I18n.format(TK_SECTION_ONE_TITLE);
        final String sectionOneContentText = I18n.format(TK_SECTION_ONE_CONTENT);
        final String sectionTwoTitleText = I18n.format(TK_SECTION_TWO_TITLE);
        final String sectionTwoContentPartOneText = I18n.format(TK_SECTION_TWO_CONTENT_PART_ONE);

        final List<String> splittedSectionOneTitleText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionOneTitleText, maximumHeaderWidth);
        final int sectionOneTitleHeight = (int) ((splittedSectionOneTitleText.size() + 0.5D) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT));

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.one.title", host, new CoreComponentState(), new Coordinate2D(16, 24), new MinecraftColor(MinecraftColor.GRAY), CoreClientProxy.getMultiColoredFontRenderer(), sectionOneTitleText, maximumHeaderWidth));

        final List<String> splittedSectionOneContentText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionOneContentText, maximumHeaderWidth);
        final int sectionOneContentHeight = (int) ((splittedSectionOneContentText.size() + 0.5D) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT));

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.one.content", host, new CoreComponentState(), new Coordinate2D(16, 24 + sectionOneTitleHeight), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), sectionOneContentText, maximumHeaderWidth));

        final List<String> splittedSectionTwoTitleText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionTwoTitleText, maximumHeaderWidth);
        final int sectionTwoTitleHeight = (int) ((splittedSectionTwoTitleText.size() + 0.5D) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT));

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.two.title", host, new CoreComponentState(), new Coordinate2D(16, 24 + sectionOneTitleHeight + sectionOneContentHeight), new MinecraftColor(MinecraftColor.GRAY), CoreClientProxy.getMultiColoredFontRenderer(), sectionTwoTitleText, maximumHeaderWidth));

        final List<String> splittedSectionTwoContentText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionTwoContentPartOneText, maximumHeaderWidth);
        final int sectionTwoContentHeight = (int) ((splittedSectionTwoContentText.size() + 0.5D) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT));

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.two.content.part.one", host, new CoreComponentState(), new Coordinate2D(16, 24 + sectionOneTitleHeight + sectionOneContentHeight + sectionTwoTitleHeight), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), sectionTwoContentPartOneText, maximumHeaderWidth));

        int totalHeight =
          24 + sectionOneTitleHeight + sectionOneContentHeight + sectionTwoTitleHeight + sectionTwoContentHeight + CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT;
        final Map<IMultiComponentArmor, List<ItemStack>> recipeExamples = new HashMap<>();

        for (IAnvilRecipe anvilRecipe :
          IArmoryAPI.Holder.getInstance().getRegistryManager().getAnvilRecipeRegistry().getValues())
        {
            final BlackSmithsAnvilRecipeWrapper wrapper = new BlackSmithsAnvilRecipeWrapper(anvilRecipe);
            final ItemStack stack = wrapper.getOutputs().isEmpty() ? null : wrapper.getOutputs().get(0);

            if (stack == null)
            {
                continue;
            }

            if (!stack.hasCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null))
            {
                continue;
            }

            IMultiComponentArmorCapability multiComponentArmorCapability = stack.getCapability(ModCapabilities.MOD_MULTICOMPONENTARMOR_CAPABILITY, null);
            if (!multiComponentArmorCapability.getInstalledExtensions().isEmpty())
            {
                continue;
            }

            final List<ItemStack> stacks = new ArrayList<>();
            stacks.addAll(wrapper.getInputs());
            stacks.add(stack);
            recipeExamples.putIfAbsent(multiComponentArmorCapability.getArmorType(), stacks);
        }

        int indexCounter = 0;
        for (IMultiComponentArmor armor : recipeExamples.keySet())
        {
            final ComponentLabel label = new ComponentLabel(host.getID() + ".section.two.content.part.two." + indexCounter + ".title",
              host,
              new CoreComponentState(),
              new Coordinate2D(16, totalHeight),
              new MinecraftColor(MinecraftColor.WHITE),
              CoreClientProxy.getMultiColoredFontRenderer(),
              I18n.format(armor.getTranslationKey()),
              maximumHeaderWidth);

            totalHeight += label.getSize().getHeigth() + CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT / 2;
            final ComponentReadOnly5X5CraftingGrid grid = new ComponentReadOnly5X5CraftingGrid(host.getID() + ".section.two.content.part.two." + indexCounter + ".recipe",
              host,
              new CoreComponentState(),
              new Coordinate2D(16, totalHeight),
              recipeExamples.get(armor),
              new MinecraftColor(Colors.Guide.PAPYRUS.brighter()));

            indexCounter++;
            totalHeight += grid.getSize().getHeigth() + CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT;

            host.registerNewComponent(label);
            host.registerNewComponent(grid);
        }
    }
}
