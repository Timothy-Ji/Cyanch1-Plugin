package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHideRain extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command!");
        } else {
            Player player = (Player) commandSender;

            player.setPlayerWeather(WeatherType.CLEAR);
        }

        return true;
    }
}
