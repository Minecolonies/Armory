package com.ldtteam.armory.common.command;

import com.ldtteam.armory.api.util.client.TranslationKeys;
import com.ldtteam.armory.api.util.references.References;
import com.ldtteam.armory.common.config.ArmoryConfig;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentTranslation;

import javax.annotation.Nonnull;

/**
 * Created by marcf on 9/28/2015.
 */
public class CommandEnableTempDecay extends CommandBase {
    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Nonnull
    @Override
    public String getName() {
        return References.InternalNames.Commands.ENABLEDECAY;
    }

    @Nonnull
    @Override
    public String getUsage(ICommandSender pSender) {
        return TranslationKeys.Messages.Commands.TK_TEMPDECAYUSAGE;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length != 1)
            throw new WrongUsageException(TranslationKeys.Messages.Commands.TK_TEMPDECAYUSAGE);

        boolean enabled = ArmoryConfig.enableTemperatureDecay;

        if (!args[0].equalsIgnoreCase("query"))
        {
            enabled = parseBoolean(args[0]);
        }

        ArmoryConfig.enableTemperatureDecay = enabled;

        String resultMessage = enabled ? TranslationKeys.Messages.Commands.Successfull.TK_TEMPDECAY_ENABLE : TranslationKeys.Messages.Commands.Successfull.TK_TEMPDECAY_DISABLE;

        sender.sendMessage(new TextComponentTranslation(resultMessage));
    }

}
