package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandServerWorld extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        BooleanPlayer bp = PlayerOnly(commandSender);
        if (bp.getBoolean()) {
            CyanchPlayer player = bp.getCyanchPlayer();
            if (strings.length < 1) {
                return false;
            }

            for (ServerWorld world : ServerWorld.values()) {
                if (strings[0].equalsIgnoreCase(world.name())) {
                    if (player.getServerWorld() == world) {
                        world.AnnounceToPlayer(player.bukkit(), "Your already here!");
                        return true;
                    }
                    else {
                        player.TransferToServerWorld(world);
                        return true;
                    }
                }
            }
            return false;
        }
        return true;
    }
}
