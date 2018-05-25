package com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.implementations;

import com.google.common.collect.Lists;
import com.smithsmodding.armory.api.IArmoryAPI;
import com.smithsmodding.armory.api.common.armor.IMultiComponentArmorExtensionInformation;
import com.smithsmodding.armory.api.util.client.Colors;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.client.gui.implementations.smithingsguide.pages.core.AbstractPage;
import com.smithsmodding.armory.common.factories.ArmorFactory;
import com.smithsmodding.armory.common.factories.HeatedItemFactory;
import com.smithsmodding.smithscore.client.font.MultiColoredFontRenderer;
import com.smithsmodding.smithscore.client.gui.components.implementations.ComponentBorder;
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
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.smithsmodding.armory.api.util.client.TranslationKeys.Gui.Guide.Introduction.*;
import static com.smithsmodding.armory.api.util.references.References.InternalNames.Upgrades.Helmet.UN_LEFT;
import static com.smithsmodding.armory.api.util.references.References.InternalNames.Upgrades.Helmet.UN_RIGHT;
import static com.smithsmodding.armory.api.util.references.References.InternalNames.Upgrades.Helmet.UN_TOP;

public class IntroductionPage extends AbstractPage
{
    private static ItemStack LEFT_HEADER_STACK;

    static {
        ArrayList<IMultiComponentArmorExtensionInformation> components = new ArrayList<>();
        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
                         .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_TOP.getResourcePath() + "-" + ModMaterials.Armor.Addon.OBSIDIAN.getRegistryName().getResourcePath()))));

        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
                         .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_RIGHT.getResourcePath() + "-" + ModMaterials.Armor.Addon.GOLD.getRegistryName().getResourcePath()))));

        components.add(new IMultiComponentArmorExtensionInformation.Impl().setCount(1)
                         .setExtension(IArmoryAPI.Holder.getInstance().getRegistryManager().getMultiComponentArmorExtensionRegistry().getValue(new ResourceLocation(References.General.MOD_ID, UN_LEFT.getResourcePath() + "-" + ModMaterials.Armor.Addon.GOLD.getRegistryName().getResourcePath()))));

        LEFT_HEADER_STACK = ArmorFactory.getInstance().buildNewMLAArmor(ModArmor.Medieval.HELMET, components, ModMaterials.Armor.Core.IRON.getBaseDurabilityForArmor(ModArmor.Medieval.HELMET), ModMaterials.Armor.Core.IRON);
    }

    public IntroductionPage(
      @Nonnull final IGUIBasedTabHost root
      )
    {
        super("introduction", root, new ItemStack(ModItems.IT_GUIDE), I18n.format(TK_INTRODUCTION));
    }

    @Override
    public void registerPageContents(@Nonnull final ComponentContentArea host)
    {
        final int pageSizeWidth = getSize().getWidth() - 14;

        host.registerNewComponent(new ComponentItemStackDisplay(host.getID() + ".header.itemstack.left", host, new CoreComponentState(), new Coordinate2D(16,0), LEFT_HEADER_STACK)
        {
            @Nullable
            @Override
            public ArrayList<String> getToolTipContent()
            {
                return Lists.newArrayList();
            }
        });
        host.registerNewComponent(new ComponentItemStackDisplay(host.getID() + ".header.itemstack.right", host, new CoreComponentState(), new Coordinate2D(pageSizeWidth - 32,0), HeatedItemFactory.getInstance().generateHeatedItemFromMaterial(ModMaterials.Armor.Core.HARDENED_IRON, ModHeatableObjects.ITEMSTACK, ModHeatedObjectTypes.INGOT, 350f))
        {
            @Nullable
            @Override
            public ArrayList<String> getToolTipContent()
            {
                return Lists.newArrayList();
            }
        });

        final String headerText = I18n.format(TK_INTRODUCTION);
        final int maximumHeaderWidth = pageSizeWidth - 48;
        final int horizontalOffset = 16 + (maximumHeaderWidth - CoreClientProxy.getMultiColoredFontRenderer().getStringWidth(headerText)) / 2;

        host.registerNewComponent(new ComponentLabel(host.getID() + ".header.text", host, new CoreComponentState(), new Coordinate2D(horizontalOffset, (16-CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT) / 2), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), headerText));

        final String sectionOneText = I18n.format(TK_SECTION_ONE);
        final String sectionTwoText = I18n.format(TK_SECTION_TWO);
        final String sectionThreeText = I18n.format(TK_SECTION_THREE);

        final List<String> splittedSectionOneText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionOneText, maximumHeaderWidth);
        final int sectionOneHeight = (splittedSectionOneText.size() + 1) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT);

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.one", host, new CoreComponentState(), new Coordinate2D(16, 24), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), sectionOneText, maximumHeaderWidth));

        final List<String> splittedSectionTwoText = CoreClientProxy.getMultiColoredFontRenderer().listFormattedStringToWidth(sectionTwoText, maximumHeaderWidth);
        final int sectionTwoHeight = (splittedSectionTwoText.size() + 1) * (CoreClientProxy.getMultiColoredFontRenderer().FONT_HEIGHT);

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.two", host, new CoreComponentState(), new Coordinate2D(16, 24 + sectionOneHeight), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), sectionTwoText, maximumHeaderWidth));

        host.registerNewComponent(new ComponentLabel(host.getID() + ".section.three", host, new CoreComponentState(), new Coordinate2D(16, 24 + sectionTwoHeight + sectionOneHeight), new MinecraftColor(MinecraftColor.WHITE), CoreClientProxy.getMultiColoredFontRenderer(), sectionThreeText, maximumHeaderWidth));
    }
}
