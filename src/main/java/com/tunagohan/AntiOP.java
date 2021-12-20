package com.tunagohan;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.Objects;

public class AntiOP extends JavaPlugin implements Listener, CommandExecutor {

    public Config config = new Config(this, "config.yml", "config.yml");
    public static AntiOP instance;
    public static ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();

    @SuppressWarnings("unused")
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(this, this);
        Objects.requireNonNull(getCommand("antiop")).setExecutor(new Commands());

        int scheduleSyncRepeatingTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this,
                new Runnable() {
                    @SuppressWarnings("unlikely-arg-type")
                    public void run() {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            // Config allowUsers UserName Type
                            boolean isNotAllowOpUserByName = !config.getStringList("allowUsers").contains(p.getName());
                            // Config allowUsers UserName Type
                            boolean isNotAllowOpUserByUUID = !config.getStringList("allowUsers").contains(p.getUniqueId());
                            // User OP State
                            boolean isOpByPlayerState = p.isOp();
                            // Not allowed, but it is OP.
                            boolean isNotAllowedHaveOp = (isNotAllowOpUserByName && isNotAllowOpUserByUUID && isOpByPlayerState);

                            if (isNotAllowedHaveOp) {
                                p.setOp(false);
                                console.sendMessage(ChatColor.RED + p.getName() + "is Not Allow OP User");
                                console.sendMessage(ChatColor.AQUA + "Please Check config allowUsers list");
                            }
                        }
                    }
                }, 20L, 20L);
    }

    @SuppressWarnings("unlikely-arg-type")
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = (Player) event.getPlayer();
        // Config allowUsers UserName Type
        boolean isNotAllowOpUserByName = !config.getStringList("allowUsers").contains(p.getName());
        // Config allowUsers UserName Type
        boolean isNotAllowOpUserByUUID = !config.getStringList("allowUsers").contains(p.getUniqueId());
        // User OP State
        boolean isOpByPlayerState = p.isOp();
        // Not allowed, but it is OP.
        boolean isNotAllowedHaveOp = (isNotAllowOpUserByName && isNotAllowOpUserByUUID && isOpByPlayerState);

        if (isNotAllowedHaveOp) {
            p.setOp(false);
            console.sendMessage(ChatColor.RED + p.getName() + "is Not Allow OP User");
            console.sendMessage(ChatColor.AQUA + "Please Check config allowUsers list");
        }
    }
}
