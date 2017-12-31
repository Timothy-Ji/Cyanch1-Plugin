package com.gmail.cyanthundermc.Cyanch1.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPosition extends CyanchCommand {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        BooleanPlayer bp = PlayerOnly(commandSender);
        if (bp.getBoolean()) {
            Player player = bp.getPlayer();
            player.chat("I am at X,Y,Z: " + ChatColor.YELLOW + player.getLocation().getBlockX() + ", " + player.getLocation().getBlockY() + ", " + player.getLocation().getBlockZ() + ChatColor.WHITE + " in " + ChatColor.YELLOW + player.getLocation().getWorld().getName());
        }
        return true;
    }
}
