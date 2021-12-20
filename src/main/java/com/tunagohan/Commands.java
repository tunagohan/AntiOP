package com.tunagohan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Commands implements CommandExecutor {

    AntiOP m = AntiOP.instance;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        // Use /antiop send
        boolean isCommandAntiOp = cmd.getName().equalsIgnoreCase("antiop");
        // Check player have a permission antiop.use
        boolean hasPermissionByUse = sender.hasPermission("antiop.use");

        if (isCommandAntiOp) {

            // Permission Denied
            if (!hasPermissionByUse) {
                sender.sendMessage(antiopConsoleMsg("No Permissions"));
                return true;
            }

            // Arg Missing
            if (args.length == 0) {
                sender.sendMessage(antiopConsoleMsg("&eCommands: /antiop reload &8Reload Plugin"));
                return true;
            }

            // Reload
            if (args[0].equalsIgnoreCase("reload")) {
                m.config.reload();
                sender.sendMessage(antiopConsoleMsg("Plugin reloaded"));
                Bukkit.getPluginManager().disablePlugin(m);
                Bukkit.getPluginManager().enablePlugin(m);
                return true;
            }
        }
        return true;
    }

    public String antiopConsoleMsg(String s) {
        String msg = "&c[&4AntiOP&c] &e" + s;
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
