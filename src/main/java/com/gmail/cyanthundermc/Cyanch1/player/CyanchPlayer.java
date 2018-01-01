package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorlds;
import com.gmail.cyanthundermc.Cyanch1.sqllite.SQLLite;
import com.gmail.cyanthundermc.Cyanch1.util.LocationSerialization;
import com.gmail.cyanthundermc.Cyanch1.util.PlayerSerialization;
import com.gmail.cyanthundermc.Cyanch1.util.SerializedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CyanchPlayer {
    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    private Player player;
    private UUID uuid;

    //As Cache:
    private Map<ServerWorld, SerializedPlayer> serverWorldPlayerData = new HashMap<>();

    public CyanchPlayer(Player player) {
        this.player = player;

        this.uuid = player.getUniqueId();

        player.setPlayerListName(getWorldServerPrefix() + " " + getColoredName());
    }

    public Player bukkit() {
        return player;
    }

    public UUID getUniqueId() {
        return uuid;
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

    public boolean isInServerWorld(ServerWorld serverWorld) {
        return getServerWorld() == serverWorld;
    }

    public boolean isInSurvivalWorld() {
        return getServerWorld() == ServerWorld.SURVIVAL;
    }

    public boolean isInCreativeWorld() {
        return getServerWorld() == ServerWorld.CREATIVE;
    }

    public void TransferToServerWorld(ServerWorld serverWorld) {
        ServerWorld sourceServerWorld = getServerWorld();

        //Check if Player has already been in serverWorld.
        boolean exists = (int) plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT EXISTS(SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId().toString(), "UUID") == 0 ? false : true;

        SerializedPlayer old;
        if (!exists && !serverWorldPlayerData.containsKey(serverWorld)) {
            //Do not get from database!
            SerializedPlayer empty = new SerializedPlayer(
                    null,
                    null,
                    null,
                    LocationSerialization.getStringFromLocation(plugin.getServer().getWorld(getServerWorld().getWorldName()).getSpawnLocation()),
                    0,
                    null
            );

            old = PlayerSerialization.applySerializedPlayerToPlayer(player, empty);
        } else {
            if (serverWorldPlayerData.containsKey(serverWorld)) {
                old = PlayerSerialization.applySerializedPlayerToPlayer(player, serverWorldPlayerData.get(serverWorld));
            } else {
                //get from database.
                SerializedPlayer serializedPlayer = new SerializedPlayer(
                        (String)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "INVENTORY_CONTENTS"),
                        (String)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "ARMOR_CONTENTS"),
                        (String)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "OFF_HAND"),
                        (String)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "LOCATION"),
                        (float)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "EXPERIENCE"),
                        (String)plugin.sqlLib.getDatabase(SQLLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = " + getUniqueId(), "GAMEMODE")
                );

                old = PlayerSerialization.applySerializedPlayerToPlayer(player, serializedPlayer);
            }
        }

        //set to cache & database.
        serverWorldPlayerData.put(sourceServerWorld, old);
        serverWorld.AnnounceToPlayer(player, "Welcome!");
        player.setPlayerListName(getWorldServerPrefix() + " " + getColoredName());
    }
}
