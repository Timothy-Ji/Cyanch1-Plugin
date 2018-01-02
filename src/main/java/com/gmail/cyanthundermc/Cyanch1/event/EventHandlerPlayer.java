package com.gmail.cyanthundermc.Cyanch1.event;

import com.gmail.cyanthundermc.Cyanch1.CyanchPlugin;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayers;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorld;
import net.minecraft.server.v1_12_R1.InventoryEnderChest;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

public class EventHandlerPlayer implements Listener {

    private CyanchPlugin plugin = CyanchPlugin.INSTANCE;
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getPlayer());
        if (!player.bukkit().hasPlayedBefore()) {
            plugin.getServer().getScoreboardManager().getMainScoreboard().getTeam("Teamless").addPlayer(player.bukkit());
        }

        event.setJoinMessage(
                ChatColor.DARK_GRAY + "| " + ChatColor.GREEN + ChatColor.BOLD + "Join " + ChatColor.GRAY + "> " +
                player.getColoredName());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getPlayer());

        event.setQuitMessage(
                ChatColor.DARK_GRAY + "| " + ChatColor.RED + ChatColor.BOLD + "Quit " + ChatColor.GRAY + "> " +
                player.getColoredName());
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getPlayer());
        event.setMessage(ChatColor.translateAlternateColorCodes('&', event.getMessage()));
        event.setFormat(player.createChatFormat("%2$s"));
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getEntity());

        if (player.getServerWorld() == ServerWorld.SURVIVAL) {
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

            plugin.getServer().broadcastMessage(ChatColor.GRAY + deathSpecialMessage.replaceAll("#player", "" + ChatColor.RESET + player.getColoredName() + ChatColor.RESET + ChatColor.GRAY) + ChatColor.RESET + ChatColor.GRAY + " for a total of " + ChatColor.BOLD + ChatColor.RED + (player.bukkit().getStatistic(Statistic.DEATHS) + 1) + ChatColor.RESET + ChatColor.GRAY + " deaths.");
            player.bukkit().sendMessage(ChatColor.GRAY + "Psst! X:" + ChatColor.YELLOW + player.bukkit().getLocation().getBlockX() + ChatColor.GRAY + " Y:" + ChatColor.YELLOW + player.bukkit().getLocation().getBlockY() + ChatColor.GRAY + " Z:" + ChatColor.YELLOW + player.bukkit().getLocation().getBlockZ());
        }
    }

    @EventHandler
    public void OnDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            CyanchPlayer player = CyanchPlayers.getCyanchPlayer((Player) event.getEntity());

            //Prevent Death in Non Survival World!
            if (!player.isInSurvivalWorld()) {
                if (player.bukkit().getHealth() - event.getFinalDamage() <= 0) {
                    event.setCancelled(true);
                    player.getServerWorld().AnnounceToLocal(player.getColoredName() + ChatColor.GRAY + " died.");

                    World world = player.bukkit().getWorld();

                    //Make sure spawn has somewhere to land on!
                    player.bukkit().teleport(player.bukkit().getWorld().getSpawnLocation());
                    player.bukkit().setFlying(true);
                    player.bukkit().setHealth(20);
                }
            }

        }
    }

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getPlayer());

        //Only allow use of bed in survival.
        if (!player.isInSurvivalWorld()) {
            event.setCancelled(true);
            player.getServerWorld().AnnounceToPlayer(player.bukkit(), "You are not allowed to do that in this world!");
            return;
        }

        plugin.getServer().broadcastMessage(player.getColoredName() + ChatColor.YELLOW + " is in bed.");
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer(event.getPlayer());

        plugin.getServer().broadcastMessage(player.getColoredName() + ChatColor.YELLOW + " is no longer in bed.");
    }

    @EventHandler
    public void onPortal(PlayerPortalEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer((Player) event.getPlayer());
        //Only allow portals to work in survival
        if (!player.isInSurvivalWorld()) {
            event.setCancelled(true);
            player.getServerWorld().AnnounceToPlayer(player.bukkit(), "You are not allowed to do that in this world!");
        }
    }

    @EventHandler
    public void onInventoryOpen(InventoryOpenEvent event) {
        CyanchPlayer player = CyanchPlayers.getCyanchPlayer((Player) event.getPlayer());
        if (!player.isInSurvivalWorld()) {
            if (event.getInventory() instanceof InventoryEnderChest || event.getInventory().equals(player.bukkit().getEnderChest())) {
                event.setCancelled(true);
                player.getServerWorld().AnnounceToPlayer(player.bukkit(), "You are not allowed to do that in this world!");
            }
        }
    }
}
