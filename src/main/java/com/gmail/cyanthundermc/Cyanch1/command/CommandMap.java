package com.gmail.cyanthundermc.Cyanch1.command;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandMap extends CyanchCommand {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        TextComponent message = new TextComponent(ChatColor.YELLOW + "Click me to go to Dynmap!" );
        message.setClickEvent( new ClickEvent( ClickEvent.Action.OPEN_URL, "http://cyanthundermc.noip.me:8123/" ) );
        message.setHoverEvent( new HoverEvent( HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("Goto the DYNMAP website!").create() ) );
        commandSender.spigot().sendMessage( message );
        return true;
    }
}
