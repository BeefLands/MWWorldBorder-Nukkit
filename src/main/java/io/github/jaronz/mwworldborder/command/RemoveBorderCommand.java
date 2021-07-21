package io.github.jaronz.mwworldborder.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import io.github.jaronz.mwworldborder.Util;

public class RemoveBorderCommand extends BaseCommand {
    public RemoveBorderCommand(){
        super(
            "removeborder",
            "Remove the border of a world",
            new String[]{
                "removeworldborder",
                "rmborder",
                "rmworldborder"
            },
            "remove",
            new CommandParameter[]{
                    CommandParameter.newType("world", true, CommandParamType.STRING)
            }
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!super.execute(commandSender, commandName, commandArgs)) return false;

        if(!commandSender.isPlayer() && commandArgs.length < 1){
            this.sendErrorMessage(commandSender, "When using this command in the console, please specify a world!");
            return false;
        }

        String worldName = commandArgs.length == 1 ? commandArgs[0] : ((Player) commandSender).getLevel().getName();

        if(!Util.removeBorder(worldName)){
            commandSender.sendMessage("Unknown world or world has no border!");
            return false;
        }

        commandSender.sendMessage("The border of world " + worldName + " has been removed");
        return true;
    }
}
