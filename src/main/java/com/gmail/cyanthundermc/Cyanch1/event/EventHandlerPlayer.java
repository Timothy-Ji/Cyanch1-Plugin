package com.gmail.cyanthundermc.Cyanch1.event;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

public class EventHandlerPlayer implements Listener {

    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam("Teamless").addPlayer(player);
        }

        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();

        event.setJoinMessage(ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + ChatColor.BOLD + "Join " + ChatColor.RESET + ChatColor.GRAY + "> " +
                color + player.getDisplayName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();

        event.setQuitMessage(
                ChatColor.DARK_GRAY + "| " + ChatColor.RED + ChatColor.BOLD + "Quit " + ChatColor.RESET + ChatColor.GRAY + "> " +
                color + player.getDisplayName());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat(plugin.getCyanchPlayer(player).CreateChatFormat("%2$s"));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();
        String[] possibleMessages = new String[]{
                "#player seems to have died",
                "It seems #player failed his epic mission",
                "Another day, and a death for #player",
                "Look out! Oh, #player died",
                "OUCH! #player is dead",
                "Good luck next time #player, You have died",
                "Too late! You've died #player",
                "#player, cursed to die, has finally done so",
                "Haha! #player died",
                "#player, #player, #player, why must you fail me",
                "Congrats! On Dying #player",
                "Hey look! #player's items! #player has died"
        };

        String deathSpecialMessage = possibleMessages[plugin.getRandom().nextInt(possibleMessages.length)];

        plugin.getServer().broadcastMessage(ChatColor.GRAY + deathSpecialMessage.replaceAll("#player", "" + ChatColor.RESET + color + player.getDisplayName() + ChatColor.RESET + ChatColor.GRAY) + ChatColor.RESET + ChatColor.GRAY + " for a total of " + ChatColor.BOLD + ChatColor.RED + (player.getStatistic(Statistic.DEATHS) + 1)+ ChatColor.RESET + ChatColor.GRAY + " deaths.");
        player.sendMessage("Psst! X:" + player.getLocation().getBlockX() + " Y:" + player.getLocation().getBlockY() + " Z:" + player.getLocation().getBlockZ());
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();

        plugin.getServer().broadcastMessage(color + player.getDisplayName() + ChatColor.YELLOW + " is in bed.");
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Player player = event.getPlayer();
        ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player).getColor();

        plugin.getServer().broadcastMessage(color + player.getDisplayName() + ChatColor.YELLOW + " is no longer in bed.");
    }
}
