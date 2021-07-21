package io.github.jaronz.mwworldborder.command;

import cn.nukkit.command.CommandSender;
import io.github.jaronz.mwworldborder.Util;

public class ClearBordersCommand extends BaseCommand {
    public ClearBordersCommand(){
        super(
            "clearborders",
            "Remove all borders",
            new String[]{"clearworldborders"},
            "clear"
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!super.execute(commandSender, commandName, commandArgs)) return false;

        Util.clearBorders();
        commandSender.sendMessage("All borders have been removed");
        return true;
    }
}
