package com.gmail.cyanthundermc.Cyanch1.sqllite;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;

public class SQLLite {

    private static CyanchPlugin plugin = CyanchPlugin.INSTANCE;
    public static String player_database_name = "CyanchS1_PlayerDB";
    public static String player_db_table_creative_data_name = "creative_server_data";
    public static String player_db_table_survival_data_name = "survival_server_data";

    public static void Init() {
        //Player DB
        plugin.sqlLib.initializeDatabase(player_database_name,
                "CREATE TABLE IF NOT EXISTS " + player_db_table_creative_data_name + "(" +
                        "UUID TEXT NOT NULL," +
                        "HAS_DATA BOOLEAN" +
                        "INVENTORY_CONTENTS TEXT," +
                        "ARMOR_CONTENTS TEXT," +
                        "GAMEMODE TEXT," +
                        "LOCATION TEXT," +
                        "EXPERIENCE FLOAT" +
                        ");" +
                        "CREATE TABLE IF NOT EXISTS " + player_db_table_survival_data_name + "(" +
                        "UUID TEXT NOT NULL," +
                        "INVENTORY_CONTENTS TEXT," +
                        "ARMOR_CONTENTS TEXT," +
                        "LOCATION TEXT," +
                        "EXPERIENCE FLOAT" +
                        ")"
        );
    }
}