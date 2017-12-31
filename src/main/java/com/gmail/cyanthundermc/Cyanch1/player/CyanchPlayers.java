package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class CyanchPlayers {
    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    private static final Map<Player, CyanchPlayer> players = new HashMap<>();

    public static CyanchPlayer getCyanchPlayer(final Player player) {
        return players.getOrDefault(player, players.put(player, new CyanchPlayer(player)));
    }
}
