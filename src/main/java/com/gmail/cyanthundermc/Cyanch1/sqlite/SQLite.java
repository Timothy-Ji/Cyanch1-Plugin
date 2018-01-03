package com.gmail.cyanthundermc.Cyanch1.sqlite;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;

public class SQLite {

    private static CyanchPlugin plugin = CyanchPlugin.INSTANCE;
    public static String player_database_name = "CyanchS1_PlayerDB";

    public static void Init() {
        //Player DB
        plugin.sqlLib.initializeDatabase(
                player_database_name,
                ""
        );
        for (ServerWorld serverWorld : ServerWorld.values()) {
            String statement =
                    "CREATE TABLE IF NOT EXISTS player_" + serverWorld.getDb_table_name() + "(" +
                            "UUID TEXT PRIMARY KEY NOT NULL," +
                            "INVENTORY_CONTENTS TEXT," +
                            "LOCATION TEXT," +
                            "EXPERIENCE FLOAT," +
                            "HEALTH DOUBLE," +
                            "FOOD INT," +
                            "SATURATION FLOAT," +
                            "FLYING BOOLEAN" +
                            "GLIDING BOOLEAN" +
                            "GAMEMODE TEXT" +
                            ")"
                    ;
            plugin.sqlLib.getDatabase(player_database_name).executeStatement(statement);
        }
    }
}