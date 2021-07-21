package io.github.jaronz.mwworldborder;

import cn.nukkit.Server;
import cn.nukkit.command.CommandMap;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.plugin.PluginLogger;
import cn.nukkit.utils.TextFormat;
import io.github.jaronz.mwworldborder.command.*;

import java.util.Arrays;

public class MWWorldBorder extends PluginBase {
    private static final int configVersion = 1;

    private PluginLogger logger;
    public static MWWorldBorder instance;

    @Override
    public void onLoad() {
        logger = this.getLogger();
        logger.info(TextFormat.WHITE + this.getName() + " has been loaded!");
    }

    @Override
    public void onEnable() {
        logger.info(TextFormat.DARK_GREEN + this.getName() + " has been enabled!");
        instance = this;

        saveDefaultConfig();

        if(getConfig().getInt("version") < configVersion) {
            logger.error("The config file of " + this.getName() + " is outdated, please delete the old config.yml file.");
            this.setEnabled(false);
            return;
        }

        Server server = this.getServer();
        CommandMap commandMap = server.getCommandMap();

        commandMap.registerAll("mwworldborder", Arrays.asList(
            new SetBorderCommand(),
            new GetBorderCommand(),
            new RemoveBorderCommand(),
            new ListBordersCommand(),
            new ClearBordersCommand()
        ));

        server.getPluginManager().registerEvents(new EventListener(), this);
    }

    @Override
    public void onDisable() {
        logger.info(TextFormat.DARK_RED + this.getName() + " has been disabled!");
    }
}
