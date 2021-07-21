package io.github.jaronz.mwworldborder.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import io.github.jaronz.mwworldborder.Util;

import java.util.Map;

public class GetBorderCommand extends BaseCommand {
    public GetBorderCommand(){
        super(
            "getborder",
            "Get the x and y distance of the border of a world",
            new String[]{"getworldborder"},
            "get",
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

        Level world = commandArgs.length == 1 ?
                commandSender.getServer().getLevelByName(commandArgs[0]) : ((Player) commandSender).getLevel();

        if(world == null){
            this.sendErrorMessage(commandSender, "Unknown world!");
            return false;
        }

        Map<String, Double> border = Util.getBorder(world);
        String worldName = world.getName();

        if(border == null){
            commandSender.sendMessage("World " + worldName + " does not have a border");
            return false;
        }

        commandSender.sendMessage("World: " + worldName + "\nDistance X: " + border.get("x") + "\nDistance Z: " + border.get("z"));
        return true;
    }
}
