package com.gmail.cyanthundermc.Cyanch1.sqllite;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;

public class SQLLite {

    private static CyanchPlugin plugin = CyanchPlugin.INSTANCE;
    public static String player_database_name = "CyanchS1_PlayerDB";

    public static void Init() {
        //Player DB
        String createStatement = "";
        for (ServerWorld serverWorld : ServerWorld.values()) {
            createStatement +=
                    "CREATE TABLE IF NOT EXISTS player_"  + serverWorld.getDb_table_name() + "(" +
                    "UUID TEXT NOT NULL," +
                    "INVENTORY_CONTENTS TEXT," +
                    "ARMOR_CONTENTS TEXT," +
                    "OFF_HAND TEXT," +
                    "LOCATION TEXT," +
                    "EXPERIENCE FLOAT," +
                    "GAMEMODE TEXT" +
                    ");"
            ;
    }
        plugin.sqlLib.initializeDatabase(player_database_name,
                createStatement
        );
    }
}