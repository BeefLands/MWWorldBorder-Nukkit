package io.github.jaronz.mwworldborder.command;

import cn.nukkit.Player;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParamType;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.level.Level;
import io.github.jaronz.mwworldborder.Util;

public class SetBorderCommand extends BaseCommand {
    public SetBorderCommand(){
        super(
            "setborder",
            "Set a new border for a world",
            new String[]{"setworldborder"},
            "set",
            new CommandParameter[]{
                CommandParameter.newType("distanceX", CommandParamType.FLOAT),
                CommandParameter.newType("distanceY", CommandParamType.FLOAT),
                CommandParameter.newType("world", true, CommandParamType.STRING)
            }
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!super.execute(commandSender, commandName, commandArgs)) return false;

        if(!commandSender.isPlayer() && commandArgs.length < 3) {
            this.sendErrorMessage(commandSender, "When using this command in the console, please specify a world!");
            return false;
        }

        double distanceX;
        double distanceZ;
        try {
            distanceX = Util.roundToHalf(Double.parseDouble(commandArgs[0]));
            distanceZ = Util.roundToHalf(Double.parseDouble(commandArgs[1]));
        } catch (Exception exception) {
            this.sendArgsErrorMessage(commandSender, "Invalid arguments!");
            return false;
        }

        if(distanceX < 1 || distanceZ < 1) {
            this.sendErrorMessage(commandSender, "distanceX and distanceZ can not be lower than 1!");
            return false;
        }

        Level world = commandArgs.length == 3 ?
            commandSender.getServer().getLevelByName(commandArgs[2]) : ((Player) commandSender).getLevel();

        if(world == null){
            this.sendErrorMessage(commandSender, "Unknown world!");
            return false;
        }

        Util.setBorder(world, distanceX, distanceZ);
        commandSender.sendMessage("Border has been set for world " + world.getName());

        return true;
    }
}
