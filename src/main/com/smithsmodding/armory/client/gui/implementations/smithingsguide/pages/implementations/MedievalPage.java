package com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.implementations;

import com.smithsmodding.armory.api.util.references.ModArmor;
import com.smithsmodding.armory.api.util.references.ModItems;
import com.smithsmodding.armory.api.util.references.ModMaterials;
import com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.core.AbstractPage;
import com.smithsmodding.armory.common.factories.ArmorFactory;
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
import java.util.List;

import static com.smithsmodding.armory.api.util.client.TranslationKeys.Gui.Guide.Introduction.*;
import static com.smithsmodding.armory.api.util.client.TranslationKeys.Gui.Guide.Medieval.*;

public class MedievalPage extends AbstractPage
{
    private static ItemStack LOGO;

    static {
        LOGO = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, new ArrayList<>(), ModMaterials.Armor.Core.IRON.getBaseDurabilityForArmor(ModArmor.Medieval.HELMET), ModMaterials.Armor.Core.IRON);
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


    }
}
