package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.command.CommandExecutor;

import java.util.ArrayList;
import java.util.List;

public class PluginCommands {
    private static CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    public static void Init() {
        AddCommand("showhand", new CommandShowHand());
        AddCommand("hiderain", new CommandHideRain(), ArrayToList(new String[] {
                "clearrain",
                "clearweather"
        }));
        AddCommand("position", new CommandPosition(), ArrayToList(new String[] {
                "pos"
        }));
        AddCommand("map", new CommandMap());

        AddCommand("serverworld", new CommandServerWorld());
        AddCommand("creative", new CommandCreative());
    }

    private static void AddCommand(String name, CommandExecutor executor, List<String> aliases) {
        plugin.getCommand(name).setAliases(aliases);
        AddCommand(name, executor);
    }

    private static void AddCommand(String name, CommandExecutor executor) {
        plugin.getCommand(name).setExecutor(executor);
    }

    private static List<String> ArrayToList(String[] array) {
        ArrayList<String> list = new ArrayList<>();
        for (String s : array) {
            list.add(s);
        }
        return list;
    }
}
