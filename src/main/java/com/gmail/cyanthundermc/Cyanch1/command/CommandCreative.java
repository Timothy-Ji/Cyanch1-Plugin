package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import com.gmail.cyanthundermc.Cyanch1.util.GameModeSerialization;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreative extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        BooleanPlayer bp = PlayerOnly(commandSender);
        if (bp.getBoolean()) {
            CyanchPlayer player = bp.getCyanchPlayer();
            if (!player.isInCreativeWorld()) {
                ServerWorld.CREATIVE.AnnounceToPlayer(player.bukkit(), "You are not in the creative world!");
                return true;
            }

            if (strings.length == 2) {
                //Change between creative worlds.
                if (strings[0].equalsIgnoreCase("world")) {
                    if (strings[1].equalsIgnoreCase("void")) {
                        player.bukkit().teleport(plugin.getServer().getWorld(ServerWorld.CREATIVE.getWorldName() + "_void").getSpawnLocation());
                    } else if (strings[1].equalsIgnoreCase("flat")) {
                        player.bukkit().teleport(plugin.getServer().getWorld(ServerWorld.CREATIVE.getWorldName()).getSpawnLocation());
                    }
                }

                //Change GameMode
                if (strings[0].equalsIgnoreCase("gamemode")) {
                    GameMode gameMode = GameModeSerialization.getGameModeFromString(strings[1]);
                    if (gameMode == null) {
                        String str = "gamemode [";
                        for (GameMode mode : GameMode.values()) {
                            if (str.equalsIgnoreCase("gamemode ["))
                                str += mode.name();
                            else
                                str += "/" + mode.name();
                        }
                        str += "]";
                        player.bukkit().sendMessage(str);
                    } else {
                        ServerWorld.CREATIVE.AnnounceToLocal(ChatColor.BLUE + "Gamemode " + ChatColor.GRAY + "> " + player.getColoredName() + ChatColor.YELLOW + " is now in " + gameMode.name().toLowerCase() + " mode.");
                        player.bukkit().setGameMode(gameMode);
                    }
                    return true;
                }

                //Teleport
                if (strings[0].equalsIgnoreCase("tp")) {
                    Player tpTarget = plugin.getServer().getPlayerExact(strings[1]);
                    if (tpTarget == null) {
                        player.bukkit().sendMessage("Player '" + tpTarget + "' could not be found.");
                    } else if (!CyanchPlayers.getCyanchPlayer(tpTarget).isInCreativeWorld()) {
                        player.bukkit().sendMessage("Player '" + tpTarget + "' is in a different server world!!");
                    } else if (player.bukkit() == tpTarget) {
                        player.bukkit().sendMessage("You cannot teleport to yourself!");
                    } else {
                        ServerWorld.CREATIVE.AnnounceToLocal(ChatColor.GOLD + "Teleport " + ChatColor.GRAY + "> " + player.getColoredName() + ChatColor.YELLOW + CyanchPlayers.getCyanchPlayer(tpTarget).getColoredName());
                        player.bukkit().teleport(tpTarget);
                    }
                }
            }

            if (strings.length == 4) {
                //Teleport by Coords
                if (strings[0].equalsIgnoreCase("tp")) {
                    try {
                        float x = Float.parseFloat(strings[1]);
                        float y = Float.parseFloat(strings[2]);
                        float z = Float.parseFloat(strings[3]);

                        ServerWorld.CREATIVE.AnnounceToLocal(ChatColor.GOLD + "Teleport " + ChatColor.GRAY + "> " + player.getColoredName() + ChatColor.YELLOW + " -> " + x + ", " + y + ", " + z);
                        player.bukkit().teleport(new Location(player.bukkit().getWorld(), x, y, z));
                    } catch (Exception e) {
                        player.bukkit().sendMessage("Error.");
                    }
                }
            }
        }
        return false;
    }
}
