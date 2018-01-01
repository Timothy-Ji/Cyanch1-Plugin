package com.gmail.cyanthundermc.Cyanch1.util;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerialization {
    public static String getStringFromLocation(Location loc) {
        if (loc == null) {
            return "";
        } else {
            return loc.getWorld().getName() + ";" + loc.getX() + ";" + loc.getY() + ";" + loc.getZ() + ";" + loc.getYaw() + ";" + loc.getPitch();
        }
    }

    public static Location getLocationFromString(String str) {
        if (str == null || str.trim() == "") {
            return null;
        }
        String[] locParts = str.split(";");
        if (locParts.length != 6)
            return null;

        World world = CyanchPlugin.INSTANCE.getServer().getWorld(locParts[0]);
        double x = Double.parseDouble(locParts[1]);
        double y = Double.parseDouble(locParts[2]);
        double z = Double.parseDouble(locParts[3]);
        float yaw = Float.parseFloat(locParts[4]);
        float pitch = Float.parseFloat(locParts[5]);
        return new Location(world, x, y, z, yaw, pitch);
    }
}
