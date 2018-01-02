package com.gmail.cyanthundermc.Cyanch1.util;

import com.gmail.cyanthundermc.Cyanch1.lib.BukkitSerialization;
import com.gmail.cyanthundermc.Cyanch1.serverworld.ServerWorlds;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;

public class PlayerSerialization {
    public static SerializedPlayer getSerializedPlayerFromPlayer(Player player) {
        String[] serializedInventory = BukkitSerialization.playerInventoryToBase64(player.getInventory());

        String serialized_inventory = serializedInventory[0];
        String serialized_armor = serializedInventory[1];
        String serialized_offhand = BukkitSerialization.itemStackArrayToBase64(new ItemStack[]{
                player.getInventory().getItemInOffHand()
        });
        String serialized_location = LocationSerialization.getStringFromLocation(player.getLocation());
        float serialized_experience = player.getExp();
        double serialized_health = player.getHealth();
        int serialized_food = player.getFoodLevel();
        float serialized_saturation = player.getSaturation();
        boolean serialized_isFlying = player.isFlying();
        boolean serialized_isGliding = player.isGliding();
        String serialized_gameMode = GameModeSerialization.getStringFromGameMode(player.getGameMode());

        return new SerializedPlayer(
                serialized_inventory,
                serialized_armor,
                serialized_offhand,
                serialized_location,
                serialized_experience,
                serialized_health,
                serialized_food,
                serialized_saturation,
                serialized_isFlying,
                serialized_isGliding,
                serialized_gameMode
        );
    }

    /**
     * Applys a SerializedPlayer's data to the Player.
     *
     * @param player           Player being modified
     * @param serializedPlayer What the player is being modified with
     * @return Creates a SerializedPlayer off the previous data.
     */
    public static SerializedPlayer applySerializedPlayerToPlayer(Player player, SerializedPlayer serializedPlayer) {
        SerializedPlayer toReturn = getSerializedPlayerFromPlayer(player);

        try {
            ItemStack[] inv = BukkitSerialization.itemStackArrayFromBase64(serializedPlayer.getInventory());
            player.getInventory().setContents(inv);
            ItemStack[] armor = BukkitSerialization.itemStackArrayFromBase64(serializedPlayer.getArmor());
            player.getInventory().setArmorContents(armor);
            ItemStack offhand = BukkitSerialization.itemStackArrayFromBase64(serializedPlayer.getOffhand())[0];
            if (offhand != null)
                player.getInventory().setItemInOffHand(BukkitSerialization.itemStackArrayFromBase64(serializedPlayer.getOffhand())[0]);
            Location loc = LocationSerialization.getLocationFromString(serializedPlayer.getLocation());
            player.teleport(loc, PlayerTeleportEvent.TeleportCause.PLUGIN);
            player.setExp(serializedPlayer.getExperience());
            player.setHealth(serializedPlayer.getHealth());
            player.setFoodLevel(serializedPlayer.getFood());
            player.setSaturation(serializedPlayer.getSaturation());
            player.setFlying(serializedPlayer.isFlying());
            player.setGliding(serializedPlayer.isGliding());
            GameMode gameMode = GameModeSerialization.getGameModeFromString(serializedPlayer.getGameMode());
            if (gameMode != null) {
                player.setGameMode(gameMode);
            } else {
                player.setGameMode(ServerWorlds.getServerWorld(loc.getWorld()).getDefaultGameMode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return toReturn;
    }
}