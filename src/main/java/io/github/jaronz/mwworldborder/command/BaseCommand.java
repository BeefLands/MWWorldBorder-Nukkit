package io.github.jaronz.mwworldborder.command;

import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.data.CommandParameter;
import cn.nukkit.utils.TextFormat;
import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.HashMap;

public abstract class BaseCommand extends Command {
    public BaseCommand(
            @NotNull String name,
            String description,
            String[] aliases,
            String permission
    ){
        super(name, description, "/" + name, aliases);
        this.setCommandParameters(new HashMap<String, CommandParameter[]>(){{ put("default", new CommandParameter[]{}); }});
        this.setPermission("mwworldborder.command." + permission);
        this.setPermissionMessage(TextFormat.RED + "You do not have permission to use this command!");
    }

    public BaseCommand(
        @NotNull String name,
        String description,
        String[] aliases,
        String permission,
        CommandParameter[] commandParameters
    ){
        this(name, description, aliases, permission);
        String usageMessage = "/" + name;
        for(CommandParameter commandParameter : commandParameters){
            boolean optional = commandParameter.optional;
            usageMessage += " " + (optional ? "[" : "<") + commandParameter.name + ": " + commandParameter.type.name().toLowerCase() + (optional ? "]" : ">");
        }
        this.setUsage(usageMessage);
        this.setCommandParameters(new HashMap<String, CommandParameter[]>(){{ put("default", commandParameters); }});
    }

    public void sendErrorMessage(CommandSender target, String message){
        target.sendMessage(TextFormat.RED + message);
    }

    public void sendArgsErrorMessage(CommandSender target, String message){
        this.sendErrorMessage(target, message + TextFormat.RESET + "\nUsage: " + this.getUsage());
    }

    @Override
    public boolean execute(CommandSender commandSender, String commandName, String[] commandArgs) {
        if(!this.testPermission(commandSender)) return false;

        CommandParameter[] commandParameters = this.commandParameters.get("default");

        if(commandArgs.length < Arrays.stream(commandParameters).filter(parameter -> !parameter.optional).toArray().length) {
            this.sendArgsErrorMessage(commandSender, "Not enough arguments!");
            return false;
        }

        if(commandArgs.length > commandParameters.length){
            this.sendArgsErrorMessage(commandSender, "Too many arguments!");
            return false;
        }

        return true;
    }
}
