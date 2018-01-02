package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import org.bukkit.WeatherType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHideRain extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        BooleanPlayer bp = PlayerOnly(commandSender);
        if (bp.getBoolean()) {
            CyanchPlayer player = CyanchPlayers.getCyanchPlayer((Player) commandSender);

            player.bukkit().setPlayerWeather(WeatherType.CLEAR);
        }

        return true;
    }
}
