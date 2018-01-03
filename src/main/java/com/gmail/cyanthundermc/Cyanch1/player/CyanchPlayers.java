package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CyanchPlayers {
    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    private static final Map<UUID, CyanchPlayer> players = new HashMap<>();

    public static CyanchPlayer getCyanchPlayer(final Player player) {
        UUID uuid = player.getUniqueId();
        if (!players.containsKey(uuid))
            players.put(uuid, new CyanchPlayer(player));
        return players.get(uuid);
    }
}
