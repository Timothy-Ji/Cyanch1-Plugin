package com.gmail.cyanthundermc.Cyanch1;

import com.gmail.cyanthundermc.Cyanch1.command.PluginCommands;
import com.gmail.cyanthundermc.Cyanch1.event.EventHandlerPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import com.gmail.cyanthundermc.Cyanch1.sqlite.SQLite;
import com.pablo67340.SQLiteLib.Main.SQLiteLib;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Random;

public class CyanchPlugin extends JavaPlugin {

    public static CyanchPlugin INSTANCE;

    private final Random random = new Random();

    public SQLiteLib sqlLib;

    @Override
    public void onEnable() {
        INSTANCE = this;
        getLogger().info("Enabled.");

        getServer().getPluginManager().registerEvents(new EventHandlerPlayer(), this);
        sqlLib = SQLiteLib.hookSQLiteLib();

        getServer().createWorld(new WorldCreator("creative"));
        getServer().createWorld(new WorldCreator("creative_void"));

        SQLite.Init();
        PluginCommands.Init();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabled");
    }

    public Random getRandom() {
        return random;
    }

    public CyanchPlayer getCyanchPlayer(Player player) {
        return CyanchPlayers.getCyanchPlayer(player);
    }
}
