package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorlds;
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

    public String getColoredName() {
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();
        return color + player.getDisplayName();
    }

    public String getWorldServerPrefix() {
        return "" + getServerWorld().getColor() + ("" + getServerWorld().name().charAt(0)).toUpperCase();
    }

    public String createChatFormat(String message) {
        return ChatColor.DARK_GRAY + "[" + getWorldServerPrefix() + ChatColor.DARK_GRAY + "] " +
                getColoredName() + ChatColor.GRAY + " > " +
                ChatColor.RESET + message;
    }

    public ServerWorld getServerWorld() {
        return ServerWorlds.getServerWorld(player.getWorld());
    }

    public boolean isInSurvivalWorld() {
        return ServerWorlds.getServerWorld(player.getWorld()) == ServerWorld.SURVIVAL;
    }

    public boolean isInCreativeWorld() {
        return ServerWorlds.getServerWorld(player.getWorld()) == ServerWorld.CREATIVE;
    }
}
