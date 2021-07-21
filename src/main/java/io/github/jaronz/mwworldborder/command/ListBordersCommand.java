package io.github.jaronz.mwworldborder.command;

import cn.nukkit.command.CommandSender;
import cn.nukkit.utils.TextFormat;
import io.github.jaronz.mwworldborder.Util;

import java.util.Map;

public class ListBordersCommand extends BaseCommand {
    public ListBordersCommand(){
        super(
            "listborders",
            "List all world borders",
            new String[]{"listworldborders"},
            "list"
        );
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!super.execute(commandSender, commandName, commandArgs)) return false;

        Map<String, Map<String, Double>> borders = Util.getBorders();

        if(borders.size() == 0){
            commandSender.sendMessage("No borders have been set!");
            return false;
        }

        String separator = TextFormat.BLACK + "---------------" + TextFormat.RESET + "\n";
        Object[] worldNames = borders.keySet().toArray();
        String bordersString = TextFormat.AQUA + "Borders" + "\n" + separator;

        for(int i = 0; i < borders.size(); i++){
            String worldName = worldNames[i].toString();
            Map<String, Double> border = borders.get(worldName);
            bordersString += "World: " + worldName + "\nDistance X: " + border.get("x") + "\nDistance Z: " + border.get("z") + "\n" + separator;
        }

        commandSender.sendMessage(bordersString);

        return true;
    }
}
