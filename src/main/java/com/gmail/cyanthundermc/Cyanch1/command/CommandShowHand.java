package com.gmail.cyanthundermc.Cyanch1.command;

import com.gmail.cyanthundermc.Cyanch1.player.CyanchPlayer;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandShowHand extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        BooleanPlayer bp = PlayerOnly(commandSender);
        if (bp.getBoolean()) {
            CyanchPlayer player = bp.getCyanchPlayer();
            if (player.bukkit().getItemInHand().getType() == null || player.bukkit().getItemInHand().getAmount() == 0) {
                player.bukkit().sendMessage(ChatColor.RED + "Your main hand is empty!");
            } else {
                ChatColor color = plugin.getServer().getScoreboardManager().getMainScoreboard().getPlayerTeam(player.bukkit()).getColor();
                ItemStack tooltipItem = player.bukkit().getItemInHand();
                sendItemTooltipMessage(player.bukkit(), player.createChatFormat(ChatColor.GREEN + "Hover over me to see my " + ChatColor.AQUA + ChatColor.BOLD + (tooltipItem.getItemMeta().getDisplayName() != null ? tooltipItem.getItemMeta().getDisplayName() : tooltipItem.getType().toString().toLowerCase()) + ChatColor.GREEN + "!"
                        + (tooltipItem.getAmount() > 1 ? ChatColor.GREEN + " I have " + ChatColor.YELLOW + ChatColor.BOLD + tooltipItem.getAmount() + ChatColor.GREEN + "!" : "")), tooltipItem);
            }
        }
        return true;
    }


    private String convertItemStackToJson(ItemStack itemStack) {
        // First we convert the item stack into an NMS itemstack
        net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        net.minecraft.server.v1_12_R1.NBTTagCompound compound = new NBTTagCompound();
        compound = nmsItemStack.save(compound);

        return compound.toString();
    }

    private void sendItemTooltipMessage(Player player, String message, ItemStack item) {
        String itemJson = convertItemStackToJson(item);

        // Prepare a BaseComponent array with the itemJson as a text component
        BaseComponent[] hoverEventComponents = new BaseComponent[]{
                new TextComponent(itemJson) // The only element of the hover events basecomponents is the item json
        };

        // Create the hover event
        HoverEvent event = new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents);

        /* And now we create the text component (this is the actual text that the player sees)
         * and set it's hover event to the item event */
        TextComponent component = new TextComponent(message);
        component.setHoverEvent(event);

        // Finally, send the message to the player
        for (Player player1 : plugin.getServer().getOnlinePlayers()) {
            player1.spigot().sendMessage(component);
        }
    }
}
