package com.gmail.cyanthundermc.Cyanch1.player;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorlds;
import com.gmail.cyanthundermc.Cyanch1.sqlite.SQLite;
import com.gmail.cyanthundermc.Cyanch1.util.PlayerSerialization;
import com.gmail.cyanthundermc.Cyanch1.util.SerializedPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class CyanchPlayer {
    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    private Player player;
    private UUID uuid;

    //As Cache:
    private Map<String, SerializedPlayer> serverWorldPlayerData;

    public CyanchPlayer(Player player) {
        this.player = player;

        this.uuid = player.getUniqueId();
        this.serverWorldPlayerData = new HashMap<>();

        this.player.setPlayerListName(getWorldServerPrefix() + " " + getColoredName());
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
        return "" + getServerWorld().getColor() + ChatColor.BOLD + ("" + getServerWorld().name().charAt(0)).toUpperCase();
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
        String exists = (String)plugin.sqlLib.getDatabase(SQLite.player_database_name).queryValue("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = '" + getUniqueId().toString() + "'", "UUID");

        SerializedPlayer old;
        if (exists == null && !serverWorldPlayerData.containsKey(serverWorld.name())) {
            old = PlayerSerialization.getSerializedPlayerFromPlayer(player);

            player.getInventory().clear();
            player.setGameMode(serverWorld.getDefaultGameMode());
            player.setExp(0);
            player.setSaturation(5);
            player.setFoodLevel(20);
            player.setHealth(20);
            player.teleport(plugin.getServer().getWorld(serverWorld.getWorldName()).getSpawnLocation());
        } else {
            if (serverWorldPlayerData.containsKey(serverWorld.name())) {
                old = PlayerSerialization.applySerializedPlayerToPlayer(player, serverWorldPlayerData.get(serverWorld.name()));
            } else {
                //get from database.
                Map<String, List<Object>> results = plugin.sqlLib.getDatabase(SQLite.player_database_name).queryMultipleRows("SELECT * FROM player_" + serverWorld.getDb_table_name() + " WHERE UUID = '" + getUniqueId() + "';",
                        "INVENTORY_CONTENTS",
                        "LOCATION",
                        "EXPERIENCE",
                        "HEALTH",
                        "FOOD",
                        "SATURATION",
                        "FLYING",
                        "GLIDING",
                        "GAMEMODE"
                        );

                SerializedPlayer serializedPlayer = new SerializedPlayer(
                        (String) results.get("INVENTORY_CONTENTS").get(0),
                        (String) results.get("LOCATION").get(1),
                        Float.parseFloat(String.valueOf(results.get("EXPERIENCE").get(2))),
                        (double) results.get("HEALTH").get(3),
                        (int) results.get("FOOD").get(4),
                        Float.parseFloat(String.valueOf(results.get("SATURATION").get(5))),
                        Boolean.parseBoolean(String.valueOf(results.get("FLYING").get(6))),
                        Boolean.parseBoolean(String.valueOf(results.get("GLIDING").get(7))),
                        (String) results.get("GAMEMODE").get(8)
                );


                old = PlayerSerialization.applySerializedPlayerToPlayer(player, serializedPlayer);
            }
        }

        //set to cache & database.
        serverWorldPlayerData.put(sourceServerWorld.name(), old);

        plugin.sqlLib.getDatabase(SQLite.player_database_name).executeStatement(
                "INSERT OR REPLACE INTO player_" + sourceServerWorld.getDb_table_name() + "(UUID, INVENTORY_CONTENTS, LOCATION, EXPERIENCE, HEALTH, FOOD, SATURATION, FLYING, GLIDING, GAMEMODE)" +
                " VALUES(" +
                        "'" + getUniqueId() + "'," +
                        "'" + old.getInventory() + "'," +
                        "'" + old.getLocation() + "'," +
                        old.getExperience() + "," +
                        old.getHealth() + "," +
                        old.getFood() + "," +
                        old.getSaturation() + "," +
                        "'" + old.isFlying() + "'," +
                        "'" + old.isGliding() + "'," +
                        "'" + old.getGameMode() + "');"
        );

        //welcome!
        serverWorld.AnnounceToPlayer(player, "Welcome!");

        //update display stuff
        player.setPlayerListName(getWorldServerPrefix() + " " + getColoredName());
    }
}
