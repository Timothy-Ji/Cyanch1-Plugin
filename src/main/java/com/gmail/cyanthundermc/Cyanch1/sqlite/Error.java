package com.gmail.cyanthundermc.Cyanch1.sqlite;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;

import java.util.logging.Level;

public class Error {
    public static void execute(CyanchPlugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Couldn't execute MySQL statement: ", ex);
    }
    public static void close(CyanchPlugin plugin, Exception ex){
        plugin.getLogger().log(Level.SEVERE, "Failed to close MySQL connection: ", ex);
    }
}