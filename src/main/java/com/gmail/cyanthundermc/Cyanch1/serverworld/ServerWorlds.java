package com.gmail.cyanthundermc.Cyanch1.serverworld;

import org.bukkit.World;

public class ServerWorlds {
    public static ServerWorld getServerWorld(World world) {
        for (ServerWorld serverWorld : ServerWorld.values()) {
            if (world.getName().startsWith(serverWorld.getWorldName())) {
                return serverWorld;
            }
        }
        return null;
    }
}
