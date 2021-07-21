package io.github.jaronz.mwworldborder;

import cn.nukkit.level.Level;
import cn.nukkit.utils.Config;

import java.util.HashMap;
import java.util.Map;

public class Util {
    public static Map<String, Map<String, Double>> getBorders(){
        return (Map<String, Map<String, Double>>) MWWorldBorder.instance.getConfig().get("borders");
    }

    private static void saveBorders(Map<String, Map<String, Double>> borders){
        Config config = MWWorldBorder.instance.getConfig();
        config.set("borders", borders);
        config.save();
    }

    public static Map<String, Double> getBorder(Level world){
        return getBorders().getOrDefault(world.getName(), null);
    }

    public static void setBorder(Level world, double distanceX, double distanceZ){
        Map<String, Map<String, Double>> borders = getBorders();
        borders.put(world.getName(), new HashMap<String, Double>(){{
            put("x", distanceX);
            put("z", distanceZ);
        }});
        saveBorders(borders);
    }

    public static boolean removeBorder(String worldName){
        Map<String, Map<String, Double>> borders = getBorders();
        if(!borders.containsKey(worldName)) return false;
        borders.remove(worldName);
        saveBorders(borders);
        return true;
    }

    public static void clearBorders(){
        saveBorders(new HashMap());
    }

    public static double roundToHalf(double value){
        return Math.round(value * 2) / 2.0;
    }
}
