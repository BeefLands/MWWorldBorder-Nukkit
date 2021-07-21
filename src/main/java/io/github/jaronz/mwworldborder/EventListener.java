package io.github.jaronz.mwworldborder;

import cn.nukkit.Player;
import cn.nukkit.entity.Entity;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.player.PlayerCommandPreprocessEvent;
import cn.nukkit.event.player.PlayerMoveEvent;
import cn.nukkit.event.player.PlayerTeleportEvent;
import cn.nukkit.event.vehicle.VehicleMoveEvent;
import cn.nukkit.level.Level;
import cn.nukkit.level.Position;
import cn.nukkit.utils.Config;
import cn.nukkit.utils.TextFormat;

import java.util.Map;

public class EventListener implements Listener {
    private boolean isOutsideBorder(Position position){
        Level world = position.getLevel();
        Map<String, Double> border = Util.getBorder(world);

        if(border == null) return false;

        Position spawn = world.getSpawnLocation();
        double distanceX = border.get("x"), distanceZ = border.get("z"),
            positionX = position.getX(), positionZ = position.getZ(),
            spawnX = spawn.getX(), spawnZ = spawn.getZ();

        return positionX < Util.roundToHalf(spawnX - distanceX) ||
            positionX >= Util.roundToHalf(spawnX + distanceX) ||
            positionZ < Util.roundToHalf(spawnZ - distanceZ) ||
            positionZ >= Util.roundToHalf(spawnZ + distanceZ);
    }

    @EventHandler(ignoreCancelled = true)
    public void onMove(PlayerMoveEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("mwworldborder.ignore")) return;
        if(isOutsideBorder(event.getTo())){
            event.setCancelled();
            Config config = MWWorldBorder.instance.getConfig();
            player.sendMessage(config.getString("message"));
            player.teleport(config.getBoolean("teleportToSpawn") ? player.getSpawn() : event.getFrom(),
                PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        if(player.hasPermission("mwworldborder.ignore")) return;
        if(isOutsideBorder(event.getTo())){
            event.setCancelled();
            Config config = MWWorldBorder.instance.getConfig();
            player.sendMessage(config.getString("messageTp"));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onVehicleMove(VehicleMoveEvent event){
        Config config = MWWorldBorder.instance.getConfig();
        if(!config.getBoolean("checkVehicleMovement")) return;
        if(isOutsideBorder(event.getTo())){
            Entity vehicle = event.getVehicle();
            vehicle.teleport(event.getFrom(), PlayerTeleportEvent.TeleportCause.PLUGIN);
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event){
        Config config = MWWorldBorder.instance.getConfig();
        Player player = event.getPlayer();
        if(config.getBoolean("blocksBreakable") || player.hasPermission("mwworldborder.ignore")) return;
        if(isOutsideBorder(event.getBlock().getLocation())) {
            event.setCancelled();
            event.getPlayer().sendMessage(config.getString("messageBlockBreak"));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent event){
        Config config = MWWorldBorder.instance.getConfig();
        Player player = event.getPlayer();
        if(config.getBoolean("blocksPlaceable") || player.hasPermission("mwworldborder.ignore")) return;
        if(isOutsideBorder(event.getBlock().getLocation())) {
            event.setCancelled();
            event.getPlayer().sendMessage(config.getString("messageBlockPlace"));
        }
    }

    @EventHandler(ignoreCancelled = true)
    public void onCommandPreprocess(PlayerCommandPreprocessEvent event){
        String message = event.getMessage();
        if(message.equalsIgnoreCase("/reload") || message.toLowerCase().startsWith("/reload ")){
            MWWorldBorder.instance.getLogger().warning(TextFormat.YELLOW + "Please do not use /reload, it can break the config of this plugin!");
        }
    }
}
