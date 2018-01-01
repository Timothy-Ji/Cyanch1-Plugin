package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class CyanchCommand implements CommandExecutor {
    protected CyanchPlugin plugin = CyanchPlugin.INSTANCE;

    protected boolean IsPlayer(CommandSender sender) {
        return sender instanceof Player;
    }

    protected BooleanPlayer PlayerOnly(CommandSender sender) {
        if (IsPlayer(sender)) {
            return new BooleanPlayer(true, (Player) sender);
        } else {
            sender.sendMessage("Only players can use this command!");
            return new BooleanPlayer(false);
        }
    }

    protected class BooleanPlayer {
        private boolean aBoolean;
        private Player player;

        public BooleanPlayer(boolean aBoolean) {
            this.aBoolean = aBoolean;
        }

        public BooleanPlayer(boolean aBoolean, Player player) {
            this.aBoolean = aBoolean;
            this.player = player;
        }

        public boolean getBoolean() {
            return aBoolean;
        }

        public Player getPlayer() {
            if (aBoolean == false)
                return null;
            return player;
        }

        public CyanchPlayer GetCyanchPlayer() {
            if (aBoolean == false)
                return null;
            return CyanchPlayers.getCyanchPlayer(player);
        }
    }
}
