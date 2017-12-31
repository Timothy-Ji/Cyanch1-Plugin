package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CyanchPlayer {
    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    public CyanchPlayer(Player player) {
        this.player = player;
    }

    private Player player;

    public Player bukkit() {
        return player;
    }

    public String getWorldServerPrefix() {
        return "" + ChatColor.GREEN + ChatColor.BOLD + "S";
    }

    public String CreateChatFormat(String message) {
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();
        return ChatColor.DARK_GRAY + "[" + getWorldServerPrefix() + ChatColor.DARK_GRAY + "] " +
                color + player.getDisplayName() + ChatColor.GRAY + " > " +
                ChatColor.RESET + message;
    }
}
