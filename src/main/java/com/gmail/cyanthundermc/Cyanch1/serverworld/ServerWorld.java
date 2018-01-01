package com.gmail.cyanthundermc.Cyanch1.serverworld;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public enum ServerWorld {
    CREATIVE("creative", ChatColor.YELLOW, "" + ChatColor.YELLOW + ChatColor.BOLD + "Creative " + ChatColor.GRAY + "> " + ChatColor.RESET, "creative_sw_data"),
    SURVIVAL("world", ChatColor.GREEN, "" + ChatColor.GREEN + ChatColor.BOLD + "Survival " +ChatColor.GREEN + " > " + ChatColor.RESET, "survival_sw_data");

    private String worldName;
    private ChatColor color;
    private String announcementFormat;
    private String db_table_name;

    ServerWorld(String worldName, ChatColor color, String announcementFormat, String database_table_name) {
        this.worldName = worldName;
        this.announcementFormat = announcementFormat;
    }

    public ChatColor getColor() {
        return color;
    }

    public String getWorldName() {
        return worldName;
    }

    public String getAnnouncementFormat() {
        return announcementFormat;
    }

    public void AnnounceToPlayer(Player player, String message) {
        player.sendMessage(getAnnouncementFormat() + message);
    }

    public void AnnounceToAll(String message) {
        CyanchPlugin.INSTANCE.getServer().broadcastMessage(getAnnouncementFormat() + message);
    }

    public void AnnounceToLocal(String message) {
        for (Player player : CyanchPlugin.INSTANCE.getServer().getOnlinePlayers()) {
            CyanchPlayer cyanchPlayer = CyanchPlayers.getCyanchPlayer(player);
            if (cyanchPlayer.getServerWorld() == this) {
                AnnounceToPlayer(player, message);
            }
        }
        CyanchPlugin.INSTANCE.getServer().getLogger().info("[Local Announcement - " + this.name() + "] " + message);
    }

    public String getDb_table_name() {
        return db_table_name;
    }
}

